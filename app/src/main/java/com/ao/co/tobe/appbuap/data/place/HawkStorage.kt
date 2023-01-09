package com.ao.co.tobe.solucaotpe.Data.local


import com.orhanobut.hawk.Hawk

object DatabaseKeys {
    const val id: String = "idKey"
    const val token: String = "tokenKey"

}


class HawkStorage {

    fun clearData() {
        Hawk.deleteAll()
    }

    fun putData(key: String, value: Any) {
        Hawk.put(key, value)
    }


    fun getData(key: String): Any? = if (checkData(DatabaseKeys.id)) Hawk.get(key, Any()) else Any()



    fun checkData(key: String): Boolean = Hawk.contains(key)

}