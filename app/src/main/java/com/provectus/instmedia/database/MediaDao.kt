package com.provectus.instmedia.database

import androidx.room.*
import com.provectus.instmedia.entity.MediaFile
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface MediaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(obj: MediaFile): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: List<MediaFile>)

    @Query("SELECT * FROM media")
    fun subscribe(): Flowable<List<MediaFile>>

    @Query("SELECT * FROM media WHERE id == :mediaId")
    fun subscribeById(mediaId: Long): Flowable<MediaFile>

    @Query("DELETE FROM media")
    fun deleteMediaFiles(): Completable

    @Query("DELETE FROM media WHERE id == :mediaId")
    fun deleteItem(mediaId: Long): Completable

    @Transaction
    fun deleteAndInsertList(mediaFiles: List<MediaFile>) {
        deleteMediaFiles()
        insert(mediaFiles)
    }

    @Transaction
    fun deleteAndInsertItem(mediaFile: MediaFile) {
        deleteItem(mediaFile.id)
        insertItem(mediaFile)
    }

}