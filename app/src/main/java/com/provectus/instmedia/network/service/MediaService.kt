package com.provectus.instmedia.network.service

import com.provectus.instmedia.entity.response_body.MediaFileResponseBody
import com.provectus.instmedia.entity.response_body.MediaList
import io.reactivex.Flowable
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MediaService {

    @GET("/me/media")
    fun getMediaList(
            @Query("access_token") accessToken: String,
            @Query("fields") fields: String
    ): Observable<MediaList>

    @GET("/{mediaId}")
    fun getMediaFile(
            @Path("mediaId") mediaId: Long,
            @Query("access_token") accessToken: String,
            @Query("fields") fields: String
    ): Flowable<MediaFileResponseBody>

}