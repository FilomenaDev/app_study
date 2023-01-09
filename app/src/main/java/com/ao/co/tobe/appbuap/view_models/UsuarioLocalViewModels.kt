package com.ao.co.tobe.appbuap.view_models

import android.util.Log
import androidx.lifecycle.*
import com.ao.co.tobe.appbuap.data.place.dao.UserDao
import com.ao.co.tobe.appbuap.models.Usuario
import kotlinx.coroutines.launch

class UsuarioLocalViewModels (private val userDao: UserDao): ViewModel() {
    val allMovimento: LiveData<List<Usuario>> = userDao.getUsuarios().asLiveData()

    var maxId : Int = userDao.findLastUsuarioId()

    fun getUser(username: String,password: String):Usuario{
        return userDao.getUsuario(username,password)
    }

    private fun insertUsuario(usuario: Usuario) {
        viewModelScope.launch {
            userDao.insert(usuario)
        }
    }

    private fun updateUsuario(username: String,password:String,userId: Int,idTerminal:String
                              ,description :String,profile:Int,
                              profileDescription:String,estabelecimento:String,
                              IdEstabelecimento:String, lastData:String) {
        viewModelScope.launch {
            userDao.update(username, password, userId, idTerminal, description, profile,
                profileDescription, estabelecimento, IdEstabelecimento, lastData)
        }
    }

    private fun deleteUsuario(){
        viewModelScope.launch {
            userDao.delete()
        }
    }


    fun updateUser(username: String,password:String,userId: Int,idTerminal:String
                   ,description :String,profile:Int,
                   profileDescription:String,estabelecimento:String,
                   IdEstabelecimento:String, lastData:String){

        val usuario = newUsuarioEntry(username, password, userId, idTerminal,
            description, profile, profileDescription, estabelecimento,
            IdEstabelecimento, lastData)

        if (maxId.equals(0)){
            Log.d("Usuario", maxId.toString())
            insertUsuario(usuario)
        }else{
            Log.d("Usuario", maxId.toString())
            updateUsuario(username, password, userId, idTerminal, description, profile,
                profileDescription, estabelecimento, IdEstabelecimento, lastData)
        }
    }

    fun deletePlaces(){
        deleteUsuario()
    }


    private fun newUsuarioEntry(usuario: String,password:String,userId: Int,idTerminal:String
                                     ,description :String,profile:Int,
                                     profileDescription:String,estabelecimento:String,
                                     IdEstabelecimento:String, lastData:String,): Usuario {
        return Usuario(username = usuario , password = password , userId = userId, idterminal = idTerminal,
            description = description, profile = profile , profileDescription = profileDescription,
            estabelecimento = estabelecimento, IdEstabelecimento = IdEstabelecimento, lastUpdate = lastData
        )
    }
}

class UsuarioLocalViewModelsFactory(private val usuarioDao: UserDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsuarioLocalViewModels::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UsuarioLocalViewModels(usuarioDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}