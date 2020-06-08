package com.provectus.instmedia.network.service

import com.provectus.instmedia.Constants.BASE_URL_AUTH
import com.provectus.instmedia.entity.response_body.LongTermAccessTokenResponseBody
import com.provectus.instmedia.entity.response_body.ShortTermAccessTokenResponseBody
import com.provectus.instmedia.entity.response_body.UserInfoResponseBody
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*

interface AuthenticationService {

    @FormUrlEncoded
    @POST
    fun getShortTermAccessToken(
            @Field("code") code: String,
            @Field("client_id") clientId: Long,
            @Field("client_secret") clientSecretId: String,
            @Field("grant_type") grantType: String,
            @Field("redirect_uri") redirectUri: String,
            @Url url: String = "${BASE_URL_AUTH}oauth/access_token"
    ): Single<ShortTermAccessTokenResponseBody>

    @GET("/access_token")
    fun getLongTermAccessToken(
            @Query("access_token") shortTermAccessToken: String,
            @Query("client_secret") clientSecretId: String,
            @Query("grant_type") grantType: String
    ): Single<LongTermAccessTokenResponseBody>

    @GET("/access_token")
    fun updateLongTermAccessToken(
            @Query("access_token") shortTermAccessToken: String,
            @Query("client_secret") clientSecretId: String,
            @Query("grant_type") grantType: String
    ): Flowable<LongTermAccessTokenResponseBody>

    @GET("/me")
    fun getUserInfo(
            @Query("access_token") accessToken: String,
            @Query("fields") fields: String
    ): Observable<UserInfoResponseBody>

}