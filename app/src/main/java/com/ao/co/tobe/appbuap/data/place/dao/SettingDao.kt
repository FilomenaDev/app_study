package com.ao.co.tobe.appbuap.data.place.dao

import androidx.room.*
import com.ao.co.tobe.appbuap.models.Settings
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingDao {
    @Query("SELECT * from settings")
    fun getSettings(): Flow<List<Settings>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg settings: Settings)

    @Query("SELECT * from settings where IDTerminal = :terminal")
    fun getTerminal(terminal: Int):Settings
    @Update()
    fun update(settings: Settings)

    @Query("DELETE FROM settings")
    fun delete()
}