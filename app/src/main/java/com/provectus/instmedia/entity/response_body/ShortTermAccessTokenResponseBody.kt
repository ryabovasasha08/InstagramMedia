package com.provectus.instmedia.entity.response_body

import com.google.gson.annotations.SerializedName

data class ShortTermAccessTokenResponseBody (
        @SerializedName("access_token") val shortTermAccessToken: String,
        @SerializedName("user_id") val userId: Long
)