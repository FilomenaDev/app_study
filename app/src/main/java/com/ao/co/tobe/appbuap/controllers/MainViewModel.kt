package com.ao.co.tobe.appbuap.controllers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.nexgo.oaf.apiv3.DeviceEngine
import com.nexgo.oaf.apiv3.device.reader.CardReader
import com.nexgo.oaf.apiv3.device.reader.CardSlotTypeEnum

var slotTypes: HashSet<CardSlotTypeEnum> = HashSet()
var deviceEng: DeviceEngine? = null
var cardReader: CardReader? = null
var type: String? = null
class MainViewModel : ViewModel() {
    val cardHolderName = MutableLiveData("")
    val payOrdenView = MutableLiveData("")
    val payRupeView = MutableLiveData("")
    val payValorView = MutableLiveData("")
    val payTypeView = MutableLiveData("")
    val isCash = MutableLiveData("")
    val isPrint = MutableLiveData(false)
    val isScanner = MutableLiveData("")
    val token = MutableLiveData("")
    val productPrice = MutableLiveData(0.00)

}
