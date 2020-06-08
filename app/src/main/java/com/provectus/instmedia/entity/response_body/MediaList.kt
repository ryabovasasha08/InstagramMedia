package com.provectus.instmedia.entity.response_body

import com.google.gson.annotations.SerializedName

data class MediaList(
        @SerializedName("data") val data: List<MediaFileResponseBody>
)