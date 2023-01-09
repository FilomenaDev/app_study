package com.ao.co.tobe.appbuap.data.place.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ao.co.tobe.appbuap.models.Movimentos

import kotlinx.coroutines.flow.Flow

@Dao
interface DaoMovimento {

    @Query("SELECT * from movimento WHERE forma = :forma")
    fun getMovimentos(forma:String): Flow<List<Movimentos>>

    @Query("SELECT * from movimento WHERE id = :id")
     fun getMovimento(id: Int): Movimentos

    @Query("SELECT MAX(id) FROM movimento WHERE forma = :forma")
    fun findLastMovimentoId (forma:String): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg movimentos: Movimentos)

    @Query("UPDATE movimento SET isOnline = :isOnline,LastUpdateIntegration = :integration,LastUpdate= :lastData WHERE idOrdem = :nRecibo")
     fun update(nRecibo: String,isOnline:String,
               integration:String,lastData:String)

    @Query("DELETE FROM movimento")
    suspend fun delete()
}