package com.provectus.instmedia.util.update_token

import android.content.SharedPreferences
import com.provectus.instmedia.Constants.ACCESS_TOKEN
import com.provectus.instmedia.Constants.CLIENT_SECRET_ID
import com.provectus.instmedia.Constants.GRANT_TYPE_EXCHANGE
import com.provectus.instmedia.network.service.AuthenticationService
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit

class UpdateTokenManager(
        private val authenticationService: AuthenticationService,
        private val sharedPreferences: SharedPreferences) {

    private var disposable: Disposable? = null

    fun init(expiresIn: Long) {
        subscribeOnMidnightUpdate(expiresIn)
    }

    private fun subscribeOnMidnightUpdate(expiresIn: Long) {
        Completable.complete()
                .andThen(Flowable.interval(expiresIn, TimeUnit.SECONDS))
                .doOnNext { updateLongTermAccessToken() }
                .subscribe()
    }

    private fun updateLongTermAccessToken() {
        val accessToken = sharedPreferences.getString(ACCESS_TOKEN, "") ?: ""
        disposable?.dispose()

        disposable = authenticationService.updateLongTermAccessToken(
                accessToken,
                CLIENT_SECRET_ID,
                GRANT_TYPE_EXCHANGE
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { saveNewLongTermAccessToken(it.longTermAccessToken) },
                        { Timber.e(it, "Error updating access token") }
                )
    }

    private fun saveNewLongTermAccessToken(accessToken: String) {
        sharedPreferences.edit().putString(ACCESS_TOKEN, accessToken).apply()
    }

}