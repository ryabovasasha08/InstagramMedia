package com.provectus.instmedia.database

import androidx.room.*
import com.provectus.instmedia.entity.Child
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface ChildrenDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: List<Child>)

    @Query("DELETE FROM children WHERE parentId == :parentId")
    fun delete(parentId: Long): Completable

    @Query("SELECT * FROM children WHERE parentId == :parentId")
    fun subscribe(parentId: Long): Flowable<List<Child>>

    @Transaction
    fun deleteAndInsertList(children: List<Child>, parentId: Long) {
        delete(parentId)
        insert(children)
    }

}