package com.ao.co.tobe.appbuap.models

data class MovimentosModel(
    val DataMov: String,
    val NumeroCartao: String,
    val DescricaoMov: String,
    val LastUpdate: String,
    val NumeroRecibo: String,
    val Saldo: Double,
    val Valor: Double
)