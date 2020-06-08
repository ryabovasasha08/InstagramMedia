package com.provectus.instmedia.entity.response_body

import com.google.gson.annotations.SerializedName

data class UserInfoResponseBody (
        @SerializedName("id") val id: Long,
        @SerializedName("username") val name: String
)