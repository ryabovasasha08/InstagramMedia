package com.provectus.instmedia.ui.fragment.show_details

import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.provectus.instmedia.R
import com.provectus.instmedia.entity.MediaFile
import com.provectus.instmedia.ui.base.BaseFragment
import com.provectus.instmedia.util.toCorrectDateFormat
import kotlinx.android.synthetic.main.frag_show_details.*
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf

class ShowDetailsFragment : BaseFragment(), ShowDetailsView {

    @InjectPresenter
    lateinit var showDetailsPresenter: ShowDetailsPresenter

    @ProvidePresenter
    fun provideShowDetailsPresenter() = get<ShowDetailsPresenter> { parametersOf(mediaFile) }

    override fun getLayoutResId(): Int = R.layout.frag_show_details

    private val mediaFile: MediaFile
        get() = arguments!!.getSerializable(MEDIA_ID) as MediaFile

    override fun setData(mediaFile: MediaFile) {
        captionTextView.text = mediaFile.caption
        dateTextView.text = mediaFile.time.toCorrectDateFormat()
    }

    override fun setupAdapter(photoAdapter: PhotoAdapter) {
        photoViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        photoViewPager.adapter = photoAdapter
    }

    override fun setUpPhotoScrollCallback(photoScrollCallback: ViewPager2.OnPageChangeCallback) {
        photoViewPager.registerOnPageChangeCallback(photoScrollCallback)
    }

    override fun setPhotoNumber(position: Int) {
        photoCounterTextView.currentPosition = position
    }

    override fun showPhotoCounter(totalAmount: Int) {
        photoCounterContainer.visibility = View.VISIBLE
        photoCounterTextView.photoTotalAmount = totalAmount
    }

    companion object {
        fun newInstance(mediaFile: MediaFile): ShowDetailsFragment {
            val args = Bundle()
            args.putSerializable(MEDIA_ID, mediaFile)

            val fragment = ShowDetailsFragment()
            fragment.arguments = args

            return fragment
        }

        private const val MEDIA_ID = "ARG_MEDIA_ID"
    }

}
