package com.provectus.instmedia.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "media")
data class MediaFile(
        @PrimaryKey
        @ColumnInfo(name = "id")
        val id: Long,

        @ColumnInfo(name = "caption")
        val caption: String?,

        @ColumnInfo(name = "media_type")
        val type: String,

        @ColumnInfo(name = "media_url")
        val url: String,

        @ColumnInfo(name = "timestamp")
        val time: String,

        @ColumnInfo(name = "username")
        val creator: String,

        @ColumnInfo(name = "isCarousel")
        val isCarousel: Boolean
) : Serializable