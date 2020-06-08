package com.provectus.instmedia.ui.fragment.show_details

import androidx.viewpager2.widget.ViewPager2
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.provectus.instmedia.entity.MediaFile
import com.provectus.instmedia.repository.MediaRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

@InjectViewState
class ShowDetailsPresenter(
        private val mediaFile: MediaFile,
        private val mediaRepository: MediaRepository
) : MvpPresenter<ShowDetailsView>() {

    private var mediaDetailsDisposable: Disposable? = null
    private var carouselUrlsDisposable: Disposable? = null

    private val photoAdapter = PhotoAdapter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        viewState.apply {
            setupAdapter(photoAdapter)
            setUpPhotoScrollCallback(photoScrollCallback)
        }

        subscribeOnMediaDetails()
    }

    override fun onDestroy() {
        super.onDestroy()

        mediaDetailsDisposable?.dispose()
        carouselUrlsDisposable?.dispose()
    }

    private fun subscribeOnMediaDetails() {
        mediaDetailsDisposable?.dispose()

        mediaDetailsDisposable = mediaRepository.getMediaFile(mediaFile.id, mediaFile.isCarousel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { setData(it) },
                        { Timber.e("Error getting media details") }
                )
    }

    private fun setData(mediaFile: MediaFile) {
        loadPhotos(mediaFile)

        viewState.apply {
            setData(mediaFile)
        }
    }

    private fun loadPhotos(mediaFile: MediaFile) {
        if (mediaFile.isCarousel) {
            subscribeOnCarouselUrls(mediaFile.id, mediaFile.url)
        } else {
            photoAdapter.setData(listOf(mediaFile.url))
        }
    }

    private fun subscribeOnCarouselUrls(parentId: Long, parentUrl: String) {
        carouselUrlsDisposable?.dispose()

        carouselUrlsDisposable = mediaRepository.getChildrenUrlList(parentId, parentUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { setPhotos(it) },
                        { Timber.e("Error getting carousel urls") }
                )
    }

    private fun setPhotos(photos: List<String>) {
        photoAdapter.setData(photos)
        viewState.showPhotoCounter(photos.size)
    }

    private val photoScrollCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            viewState.setPhotoNumber(position.plus(1))
        }
    }

}