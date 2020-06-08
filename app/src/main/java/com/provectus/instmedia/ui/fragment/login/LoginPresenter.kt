package com.provectus.instmedia.ui.fragment.login

import android.content.Context
import android.content.SharedPreferences
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.provectus.instmedia.Constants.ACCESS_TOKEN
import com.provectus.instmedia.entity.response_body.LongTermAccessTokenResponseBody
import com.provectus.instmedia.repository.AuthenticationRepository
import com.provectus.instmedia.util.hasInternetConnection
import com.provectus.instmedia.util.update_token.UpdateTokenManager
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

@InjectViewState
class LoginPresenter(
        private val context: Context,
        private val updateTokenManager: UpdateTokenManager,
        private val authenticationRepository: AuthenticationRepository,
        private val sharedPreferences: SharedPreferences
) : MvpPresenter<LoginView>() {

    private var compositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        checkInternetConnection(context)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    fun checkInternetConnection(context: Context) {
        if (context.hasInternetConnection()) viewState.openWebView() else viewState.showNoInternetDialog()
    }

    fun getAccessToken(accessCode: String) {
        val disposable = authenticationRepository.getShortTermAccessToken(accessCode)
                .subscribe(
                        { getLongTermAccessToken(it.shortTermAccessToken) },
                        { Timber.e(it, "Failed to get short term access token") }
                )

        compositeDisposable.add(disposable)
    }

    private fun getLongTermAccessToken(shortTermAccessToken: String) {
        val disposable = authenticationRepository.getLongTermAccessToken(shortTermAccessToken)
                .subscribe(
                        { doOnLongTermAccessTokenReceived(it) },
                        { Timber.e(it, "Failed to get long term access token") }
                )

        compositeDisposable.add(disposable)
    }

    private fun doOnLongTermAccessTokenReceived(longTermAccessTokenResponseBody: LongTermAccessTokenResponseBody) {
        saveToken(longTermAccessTokenResponseBody.longTermAccessToken)
        scheduleTokenUpdating(longTermAccessTokenResponseBody.expiresIn)
        viewState.navigateToMediaList()
    }

    private fun scheduleTokenUpdating(expiresIn: Long) {
        updateTokenManager.init(expiresIn)
    }

    private fun saveToken(longTermAccessToken: String) {
        sharedPreferences.edit().putString(ACCESS_TOKEN, longTermAccessToken).apply()
    }

}