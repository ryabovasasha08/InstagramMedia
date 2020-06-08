package com.provectus.instmedia.repository

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.provectus.instmedia.Constants.ACCESS_TOKEN
import com.provectus.instmedia.database.ChildrenDao
import com.provectus.instmedia.database.MediaDao
import com.provectus.instmedia.entity.Child
import com.provectus.instmedia.entity.MediaFile
import com.provectus.instmedia.entity.RequestParams
import com.provectus.instmedia.entity.response_body.MediaFileResponseBody
import com.provectus.instmedia.entity.response_body.MediaList
import com.provectus.instmedia.network.service.MediaService
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MediaRepository(
        private val mediaService: MediaService,
        private val mediaDao: MediaDao,
        private val childrenDao: ChildrenDao,
        private val sharedPreferences: SharedPreferences
) {

    fun getMediaList(): Flowable<List<MediaFile>> {
        updateUserMediaList()
        return mediaDao.subscribe()
    }

    fun getMediaFile(id: Long, isCarousel: Boolean): Flowable<MediaFile> {
        updateUserMedia(id, isCarousel)
        return mediaDao.subscribeById(id)
    }

    fun getChildrenUrlList(parentId: Long, parentUrl: String): Flowable<List<String>> {
        return childrenDao.subscribe(parentId).map { getSortedUrlChildList(it, parentUrl) }
    }

    @SuppressLint("CheckResult")
    private fun updateUserMediaList() {
        val accessToken = sharedPreferences.getString(ACCESS_TOKEN, "") ?: ""

        mediaService.getMediaList(
                accessToken,
                getBasicMediaFileParams().joinToString(",")
        )
                .subscribeOn(Schedulers.io())
                .flatMapCompletable {
                    Completable.fromAction {
                        cacheMediaList(it)
                    }
                }
                .subscribe(
                        { Unit },
                        { Timber.e(it, "Failed to update user media list from network") }
                )
    }

    @SuppressLint("CheckResult")
    private fun updateUserMedia(id: Long, isCarousel: Boolean) {
        val accessToken = sharedPreferences.getString(ACCESS_TOKEN, "") ?: ""

        mediaService.getMediaFile(
                id,
                accessToken,
                getMediaDetailsParams(isCarousel)
        )
                .subscribeOn(Schedulers.io())
                .flatMapCompletable {
                    Completable.fromAction {
                        cacheMediaData(it)
                    }
                }
                .subscribe(
                        { Unit },
                        { Timber.e(it, "Failed to update user media from network") }
                )
    }

    private fun cacheMediaList(mediaList: MediaList) {
        mediaList.data
                .map { it.toMediaFile() }
                .toList()
                .apply { mediaDao.deleteAndInsertList(this) }
    }

    private fun cacheMediaData(mediaFileResponseBody: MediaFileResponseBody) {
        cacheMedia(mediaFileResponseBody)
        if (mediaFileResponseBody.isCarousel) updateChildrenUrlList(mediaFileResponseBody.childrenIds!!, mediaFileResponseBody.id)
    }

    @SuppressLint("CheckResult")
    private fun updateChildrenUrlList(childrenIdList: List<Long>, parentId: Long) {
        val childrenUrlFlowables = getChildrenUrlFlowables(childrenIdList)

        Flowable.zip(childrenUrlFlowables) { it.map { child -> child as MediaFileResponseBody } }
                .subscribeOn(Schedulers.io())
                .flatMapCompletable {
                    Completable.fromAction {
                        cacheChildren(it, parentId)
                    }
                }
                .subscribe(
                        { Unit },
                        { Timber.e(it, "Failed to get children url list from network") }
                )

    }

    private fun getChildrenUrlFlowables(childrenIdList: List<Long>): List<Flowable<MediaFileResponseBody>> {
        val accessToken = sharedPreferences.getString(ACCESS_TOKEN, "") ?: ""
        val flowableList = mutableListOf<Flowable<MediaFileResponseBody>>()

        childrenIdList.forEach {
            flowableList.add(
                    mediaService
                            .getMediaFile(
                                    it,
                                    accessToken,
                                    getChildParams().joinToString(",")
                            )
                            .subscribeOn(Schedulers.io())
            )
        }

        return flowableList
    }

    private fun cacheChildren(childList: List<MediaFileResponseBody>, parentId: Long) {
        childList
                .map { child -> Child(id = child.id, url = child.url, parentId = parentId) }
                .apply { childrenDao.deleteAndInsertList(this, parentId) }
    }

    private fun cacheMedia(mediaFileResponseBody: MediaFileResponseBody) {
        mediaDao.deleteAndInsertItem(mediaFileResponseBody.toMediaFile())
    }

    private fun getSortedUrlChildList(childList: List<Child>, parentUrl: String): List<String> {
        val sortedChildList = mutableListOf<Child>()
        sortedChildList.addAll(childList)

        childList.forEachIndexed { index, child ->
            if (child.url == parentUrl) {
                sortedChildList.apply {
                    removeAt(index)
                    add(0, child)
                }
            }
        }

        return sortedChildList.map { it.url }
    }

    private fun getMediaDetailsParams(isCarousel: Boolean): String {
        val params = getBasicMediaFileParams()
        if (isCarousel) params.add(RequestParams.CHILDREN.title)

        return params.joinToString(",")
    }

    private fun getBasicMediaFileParams(): MutableList<String> = mutableListOf(
            RequestParams.MEDIA_TYPE.title,
            RequestParams.MEDIA_URL.title,
            RequestParams.ID.title,
            RequestParams.CAPTION.title,
            RequestParams.TIMESTAMP.title,
            RequestParams.USERNAME.title
    )

    private fun getChildParams(): MutableList<String> = mutableListOf(
            RequestParams.ID.title,
            RequestParams.MEDIA_TYPE.title,
            RequestParams.MEDIA_URL.title
    )

}