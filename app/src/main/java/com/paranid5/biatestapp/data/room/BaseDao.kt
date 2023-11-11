package com.paranid5.biatestapp.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

@Dao
interface BaseDao<T : Entity> {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg entities: T): List<Long>

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(vararg entities: T)

    @Delete
    suspend fun delete(vararg entities: T)
}