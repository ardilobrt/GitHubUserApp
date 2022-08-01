package id.co.ardilobrt.githubuser.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.co.ardilobrt.githubuser.ui.setting.SettingPreferences
import java.lang.IllegalArgumentException

class MainViewModelFactory(private val pref: SettingPreferences) :
    ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                return MainViewModel(pref) as T
            }
            throw IllegalArgumentException("unknown ViewModel class: ${modelClass.name}")
        }

        companion object {
            @Volatile
            private var instance: MainViewModelFactory? = null
            fun getInstance(pref: SettingPreferences): MainViewModelFactory =
                instance ?: synchronized(this) {
                    instance ?: MainViewModelFactory(pref)
                }.also { instance = it }
        }
}