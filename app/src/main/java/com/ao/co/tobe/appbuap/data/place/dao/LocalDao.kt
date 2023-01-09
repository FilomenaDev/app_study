package com.ao.co.tobe.appbuap.data.place.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ao.co.tobe.appbuap.models.Place


import kotlinx.coroutines.flow.Flow

@Dao
interface LocalDao {
    @Query("SELECT * from place ORDER BY Local ASC")
    fun getPlaces(): Flow<List<Place>>

    @Query("SELECT * from place WHERE id = :id")
    fun getPlace(id: Long): Place

    @Query("SELECT MAX(id) FROM place")
    fun findLastPlaceId (): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg place: Place)

    @Update()
    fun update(place: Place)

    @Query("DELETE FROM place")
    fun delete()
}