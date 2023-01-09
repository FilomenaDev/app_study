package com.ao.co.tobe.appbuap.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Blob


@Entity(tableName = "settings")
data class Settings(
    @PrimaryKey(autoGenerate = true)
    var id: Int =0 ,
    @ColumnInfo(name = "IDTerminal")
    var idTerminal: Int?,
    @ColumnInfo(name = "IDEstabelecimento")
    var idEstabelecimento: Int?,
    @ColumnInfo(name = "Estabelecimento")
    var estabelecimento: String?,
    @ColumnInfo(name = "LastUpdate")
    var lastUpdate: String?,
){
}

@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    var id: Int =0 ,
    @ColumnInfo(name = "username")
    var username: String ="",
    @ColumnInfo(name = "password")
    var password: String = "",
    @ColumnInfo(name = "userID")
    var userId : Int = 0,
    @ColumnInfo(name = "idterminal")
    var idterminal: String = "",
    @ColumnInfo(name = "description")
    var description: String = "",
    @ColumnInfo(name = "profile")
    var profile: Int = 0,
    @ColumnInfo(name = "userPerfilDesc")
    var profileDescription: String = "",
    @ColumnInfo(name = "estabelecimento")
    var estabelecimento: String = "",
    @ColumnInfo(name = "IdEstabelecimento")
    var IdEstabelecimento: String = "",
    @ColumnInfo(name = "LastUpdate")
    var lastUpdate: String = "",
){
}

@Entity(tableName = "place")
data class Place(
    @PrimaryKey(autoGenerate = true)
    var id: Int =0 ,
    @ColumnInfo(name = "IDEstabelecimento")
    var idEstabelecimento: Int?,
    @ColumnInfo(name = "IDLocal")
    var idLocal: Int?,
    @ColumnInfo(name = "LastUpdate")
    var lastUpdate: String?,
    @ColumnInfo(name = "Local")
    var local: String?,
    @ColumnInfo(name = "Status")
    var Status: Int?,
){
}


@Entity(tableName = "movimento")
data class Movimentos(
    @PrimaryKey(autoGenerate = true)
    var id: Int =0 ,
    @ColumnInfo(name = "idOrdem")
    var id_orden: String?,
    @ColumnInfo(name = "rupe")
    var numero_rupe: String?,
    @ColumnInfo(name = "valor")
    var valor: String?,
    @ColumnInfo(name = "pago")
    var pago: String?,
    @ColumnInfo(name = "troco")
    var troco: String?,
    @ColumnInfo(name = "forma")
    var forma: String?,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB,name = "image")
    var image: ByteArray?,
    @ColumnInfo(name = "SaldoAnterior")
    var saldoAnterior: String?,
    @ColumnInfo(name = "SaldoApos")
    var saldoApos: String?,
    @ColumnInfo(name = "IdLocal")
    var IdLocal: String?,
    @ColumnInfo(name = "Description")
    var description: String?,
    @ColumnInfo(name = "IdUser")
    var IdUser: String?,
    @ColumnInfo(name = "isOnline")
    var isOnline: String?,
    @ColumnInfo(name = "LastUpdateIntegration")
    var lastUpdateIntegration: String?,
    @ColumnInfo(name = "LastUpdate")
    var lastUpdate: String?,

){
}