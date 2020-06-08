package com.provectus.instmedia.ui.fragment.media_list

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.provectus.instmedia.entity.MediaFile

@StateStrategyType(AddToEndSingleStrategy::class)
interface MediaListView : MvpView {
    fun setUpMediaRecyclerView(mediaListAdapter: MediaListAdapter)
    fun setCurrentRecyclerViewPosition(position: Int)
    fun setUserName(userName: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun navigateToShowDetails(mediaFile: MediaFile)
}