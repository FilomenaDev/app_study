package com.ao.co.tobe.appbuap.data.place.dao

import androidx.room.*
import com.ao.co.tobe.appbuap.models.Usuario
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * from usuarios")
    fun getUsuarios(): Flow<List<Usuario>>

    @Query("SELECT * from usuarios WHERE username = :username AND password = :password")
    fun getUsuario(username: String,password: String): Usuario

    @Query("SELECT MAX(id) FROM usuarios")
    fun findLastUsuarioId (): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg usuario: Usuario)

    @Query("UPDATE usuarios SET username = :usuario,password = :password,userID= :userId, idterminal= :idTerminal," +
            " description = :description, profile = :profile,userPerfilDesc = :profileDescription,estabelecimento= :estabelecimento,IdEstabelecimento= :IdEstabelecimento,LastUpdate = :lastData  where id = 1")
    fun update(usuario: String,password:String,userId: Int,idTerminal:String
               ,description :String,profile:Int,
               profileDescription:String,estabelecimento:String,
               IdEstabelecimento:String, lastData:String)

    @Query("DELETE FROM usuarios")
    fun delete()
}