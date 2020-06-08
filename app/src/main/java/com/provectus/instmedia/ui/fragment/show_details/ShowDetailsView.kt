package com.provectus.instmedia.ui.fragment.show_details

import androidx.viewpager2.widget.ViewPager2
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.provectus.instmedia.entity.MediaFile

@StateStrategyType(AddToEndSingleStrategy::class)
interface ShowDetailsView : MvpView {
    fun setData(mediaFile: MediaFile)
    fun setupAdapter(photoAdapter: PhotoAdapter)
    fun setUpPhotoScrollCallback(photoScrollCallback: ViewPager2.OnPageChangeCallback)
    fun setPhotoNumber(position: Int)
    fun showPhotoCounter(totalAmount: Int)
}