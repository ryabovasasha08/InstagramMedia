package com.provectus.instmedia.ui.fragment.media_list

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.provectus.instmedia.entity.MediaFile
import com.provectus.instmedia.repository.AuthenticationRepository
import com.provectus.instmedia.repository.MediaRepository
import com.provectus.instmedia.util.toDateTime
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

@InjectViewState
class MediaListPresenter(
        private val authenticationRepository: AuthenticationRepository,
        private val mediaRepository: MediaRepository
) : MvpPresenter<MediaListView>() {

    private val mediaListAdapter = MediaListAdapter()
    private var currentPosition: Int = 0

    private var userInfoDisposable: Disposable? = null
    private var mediaListDisposable: Disposable? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        setUpRecyclerView()
        subscribeOnUserInfo()
        subscribeOnMediaList()
    }

    override fun attachView(view: MediaListView?) {
        super.attachView(view)
        viewState.setCurrentRecyclerViewPosition(currentPosition)
    }

    override fun onDestroy() {
        super.onDestroy()

        userInfoDisposable?.dispose()
        mediaListDisposable?.dispose()
    }

    private fun setUpRecyclerView() {
        mediaListAdapter.mediaListCallback = mediaListCallback
        viewState.setUpMediaRecyclerView(mediaListAdapter)
    }

    private fun subscribeOnMediaList() {
        mediaListDisposable?.dispose()

        mediaListDisposable = mediaRepository.getMediaList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { it.sortedByDescending { mediaFile -> mediaFile.time.toDateTime() } }
                .subscribe(
                        { mediaListAdapter.setData(it) },
                        { Timber.e("Error getting user info") }
                )
    }

    private fun subscribeOnUserInfo() {
        userInfoDisposable?.dispose()

        userInfoDisposable = authenticationRepository.getUserInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { viewState.setUserName(it.name) },
                        { Timber.e("Error getting user info") }
                )
    }

    private val mediaListCallback = object : MediaListCallback {

        override fun onMediaClick(mediaFile: MediaFile, position: Int) {
            currentPosition = position
            viewState.navigateToShowDetails(mediaFile)
        }

    }

}
