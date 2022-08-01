package id.co.ardilobrt.githubuser.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ThemeViewModelFactory(private val pref: SettingPreferences) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThemeViewModel::class.java)) {
            return ThemeViewModel(pref) as T
        }
        throw IllegalArgumentException("unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var instance: ThemeViewModelFactory? = null
        fun getInstance(pref: SettingPreferences): ThemeViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ThemeViewModelFactory(pref)
            }.also { instance = it }
    }
}