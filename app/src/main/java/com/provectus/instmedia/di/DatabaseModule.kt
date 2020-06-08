package com.provectus.instmedia.di

import android.content.Context
import androidx.room.Room
import com.provectus.instmedia.Constants.DB_TABLE_NAME
import com.provectus.instmedia.database.ChildrenDao
import com.provectus.instmedia.database.MediaDao
import com.provectus.instmedia.database.InstMediaDb
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single { provideDatabase(androidContext()) }
    single { provideMediaDao(get()) }
    single { provideChildrenDao(get()) }
}

fun provideDatabase(context: Context): InstMediaDb {
    return Room.databaseBuilder(context, InstMediaDb::class.java, DB_TABLE_NAME).build()
}

fun provideMediaDao(database: InstMediaDb): MediaDao = database.mediaDao()

fun provideChildrenDao(database: InstMediaDb): ChildrenDao = database.childrenDao()