package com.provectus.instmedia.ui.fragment.login

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface LoginView : MvpView {
    fun openWebView()
    fun showNoInternetDialog()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun navigateToMediaList()
}