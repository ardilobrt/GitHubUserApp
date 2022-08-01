package id.co.ardilobrt.githubuser.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.co.ardilobrt.githubuser.api.ApiConfig
import id.co.ardilobrt.githubuser.data.FavoriteRepository
import id.co.ardilobrt.githubuser.data.local.entity.FavoriteUser
import id.co.ardilobrt.githubuser.data.model.DetailUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(private val favoriteRepository: FavoriteRepository) : ViewModel() {

    private val _user = MutableLiveData<DetailUserResponse>()
    val user: LiveData<DetailUserResponse> = _user

    fun getDetailUser(username: String) {
        val client = ApiConfig.getApiService().getUserDetail(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                if (response.isSuccessful) {
                    _user.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun saveFavoriteUser(user: FavoriteUser) = favoriteRepository.setFavorite(user, true)

    fun checkFavorite(id: Int) = favoriteRepository.checkFavorite(id)

    fun deleteFavorite(id: Int) = favoriteRepository.deleteFavorite(id)

    companion object {
        private const val TAG = "DetailUserActivity"
    }
}