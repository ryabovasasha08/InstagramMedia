package com.provectus.instmedia.entity.response_body

import com.google.gson.annotations.SerializedName

data class LongTermAccessTokenResponseBody (
        @SerializedName("access_token") val longTermAccessToken: String,
        @SerializedName("token_type") val tokenType: String,
        @SerializedName("expires_in") val expiresIn: Long
)