package com.provectus.instmedia.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "children")
data class Child(
        @PrimaryKey
        @ColumnInfo(name = "id")
        val id: Long,

        @ColumnInfo(name = "url")
        val url: String,

        @ColumnInfo(name = "parentId")
        val parentId: Long
) : Serializable