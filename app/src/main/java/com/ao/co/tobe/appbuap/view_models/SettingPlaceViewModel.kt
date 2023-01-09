package com.ao.co.tobe.appbuap.view_models

import androidx.lifecycle.*
import com.ao.co.tobe.appbuap.data.place.dao.SettingDao
import com.ao.co.tobe.appbuap.models.Settings

import kotlinx.coroutines.launch

class SettingPlaceViewModel (private val settingsDao: SettingDao): ViewModel() {
    val allSettings: LiveData<List<Settings>> = settingsDao.getSettings().asLiveData()


    private fun insertSttings(settings: Settings) {
        viewModelScope.launch {
            settingsDao.insert(settings)
        }
    }

    private fun lastSeting(terminal: Int): Settings {
        return  settingsDao.getTerminal(terminal)
    }
    private fun updateSttings(settings: Settings) {
        viewModelScope.launch {
            settingsDao.update(settings)
        }
    }

    fun addSetting(terminal: Int, IDestabelecimento: Int, estabelecimento: String, lastData: String) {
        val setting = newSettingsEntry(terminal, IDestabelecimento, estabelecimento, lastData)
        val lastSettings = lastSeting(terminal)
        if (lastSettings == null)
         insertSttings(setting)
        else
         updateSttings(setting)
    }


    private fun deleteSetting() {
        viewModelScope.launch {
            settingsDao.delete()
        }
    }


    fun deleteSettinhPlaces() {
        deleteSetting()
    }


    private fun newSettingsEntry(terminal: Int, IDestabelecimento: Int, estabelecimento: String, lastData: String): Settings {
        return Settings(
            idTerminal = terminal,
            idEstabelecimento = IDestabelecimento,
            estabelecimento = estabelecimento,
            lastUpdate = lastData,

        )
    }


    class SettingPlaceViewModelFactory(private val settingsDao: SettingDao) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SettingPlaceViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SettingPlaceViewModel(settingsDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}