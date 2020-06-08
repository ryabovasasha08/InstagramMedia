package com.provectus.instmedia.entity.response_body

import com.google.gson.annotations.SerializedName

data class ChildrenList(
        @SerializedName("data") val data: List<ChildrenId>
)