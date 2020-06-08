package com.provectus.instmedia.entity.response_body

import com.google.gson.annotations.SerializedName
import com.provectus.instmedia.entity.MediaFile
import java.io.Serializable

data class MediaFileResponseBody(
        @SerializedName("id") val id: Long,
        @SerializedName("caption") val caption: String?,
        @SerializedName("children") val children: ChildrenList?,
        @SerializedName("media_type") val type: String,
        @SerializedName("media_url") val url: String,
        @SerializedName("timestamp") val time: String,
        @SerializedName("username") val creator: String
) : Serializable {

    val isCarousel: Boolean
        get() = type == CAROUSEL_TYPE

    val childrenIds: List<Long>?
        get() = children?.let {
            it.data.map { children -> children.id }
        }

    fun toMediaFile(): MediaFile {
        return MediaFile(
                id = id,
                caption = caption,
                type = type,
                url = url,
                time = time,
                creator = creator,
                isCarousel = isCarousel
        )
    }

    companion object {
        const val CAROUSEL_TYPE = "CAROUSEL_ALBUM"
    }

}