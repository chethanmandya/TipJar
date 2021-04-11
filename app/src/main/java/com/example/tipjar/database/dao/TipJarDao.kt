package com.example.tipjar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tipjar.database.entity.TipHistory
import com.example.tipjar.testing.OpenForTesting

@Dao
@OpenForTesting
interface TipJarDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tipHistory: TipHistory)

    @Query("SELECT * FROM TipHistory")
    fun getAllTheTips(): LiveData<List<TipHistory>>

    @Query("SELECT * FROM TipHistory WHERE `timeStamp` = :timeStamp")
    fun getSavedTip(timeStamp: Long): LiveData<TipHistory>

    @Delete
    suspend fun delete(item: TipHistory)
}
