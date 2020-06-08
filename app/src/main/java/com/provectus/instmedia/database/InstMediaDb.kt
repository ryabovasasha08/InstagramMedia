package com.provectus.instmedia.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.provectus.instmedia.Constants.DB_VERSION
import com.provectus.instmedia.entity.Child
import com.provectus.instmedia.entity.MediaFile

@Database(entities = [MediaFile::class, Child::class], version = DB_VERSION)
abstract class InstMediaDb : RoomDatabase() {
    abstract fun mediaDao(): MediaDao
    abstract fun childrenDao(): ChildrenDao
}