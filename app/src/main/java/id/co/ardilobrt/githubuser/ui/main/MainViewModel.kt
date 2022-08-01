package id.co.ardilobrt.githubuser.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import id.co.ardilobrt.githubuser.api.ApiConfig
import id.co.ardilobrt.githubuser.data.model.UserResponse
import id.co.ardilobrt.githubuser.data.model.UsersItem
import id.co.ardilobrt.githubuser.ui.setting.SettingPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val pref: SettingPreferences) : ViewModel() {

    private val _listUser = MutableLiveData<ArrayList<UsersItem>>()
    val listUser: LiveData<ArrayList<UsersItem>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getThemeSettings(): LiveData<Boolean> = pref.getThemeSetting().asLiveData()

    fun setSearchUser(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserByQuery(query)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listUser.value = responseBody.items
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}