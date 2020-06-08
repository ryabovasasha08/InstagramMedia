package com.provectus.instmedia.repository

import android.content.SharedPreferences
import com.provectus.instmedia.Constants.ACCESS_TOKEN
import com.provectus.instmedia.Constants.CLIENT_ID
import com.provectus.instmedia.Constants.CLIENT_SECRET_ID
import com.provectus.instmedia.Constants.GRANT_TYPE_AUTH
import com.provectus.instmedia.Constants.GRANT_TYPE_EXCHANGE
import com.provectus.instmedia.Constants.REDIRECT_URL
import com.provectus.instmedia.entity.RequestParams
import com.provectus.instmedia.entity.response_body.LongTermAccessTokenResponseBody
import com.provectus.instmedia.entity.response_body.ShortTermAccessTokenResponseBody
import com.provectus.instmedia.entity.response_body.UserInfoResponseBody
import com.provectus.instmedia.network.service.AuthenticationService
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AuthenticationRepository(
        private val authenticationService: AuthenticationService,
        private val sharedPreferences: SharedPreferences
) {

    fun getShortTermAccessToken(accessCode: String): Single<ShortTermAccessTokenResponseBody> {
        return authenticationService.getShortTermAccessToken(
                accessCode,
                CLIENT_ID,
                CLIENT_SECRET_ID,
                GRANT_TYPE_AUTH,
                REDIRECT_URL
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getLongTermAccessToken(shortTermAccessToken: String): Single<LongTermAccessTokenResponseBody> {
        return authenticationService.getLongTermAccessToken(
                shortTermAccessToken,
                CLIENT_SECRET_ID,
                GRANT_TYPE_EXCHANGE
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getUserInfo(): Observable<UserInfoResponseBody> {
        val accessToken = sharedPreferences.getString(ACCESS_TOKEN, "") ?: ""

        return authenticationService.getUserInfo(
                accessToken,
                getUserInfoParams()
        )
    }

    private fun getUserInfoParams(): String {
        return listOf(
                RequestParams.ID.title,
                RequestParams.USERNAME.title
        ).joinToString(",")
    }

}