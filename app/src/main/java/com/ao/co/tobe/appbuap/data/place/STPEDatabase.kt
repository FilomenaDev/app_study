package com.ao.co.tobe.appbuap.data.place

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ao.co.tobe.appbuap.data.place.dao.DaoMovimento
import com.ao.co.tobe.appbuap.data.place.dao.LocalDao
import com.ao.co.tobe.appbuap.data.place.dao.SettingDao
import com.ao.co.tobe.appbuap.data.place.dao.UserDao
import com.ao.co.tobe.appbuap.models.*


@Database(entities = [Place::class,Movimentos::class,Usuario::class,Settings::class], version = 1, exportSchema = false)

abstract class STPEDatabase: RoomDatabase() {


    abstract fun localDao(): LocalDao
    abstract fun movimentoDao(): DaoMovimento
    abstract fun usuarioDao(): UserDao
    abstract fun settingDao(): SettingDao


    companion object{
        @Volatile
        private var INSTANCE: STPEDatabase? = null

        fun getDatabase(context: Context): STPEDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    STPEDatabase::class.java,
                    "BUAP"
                ).fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}