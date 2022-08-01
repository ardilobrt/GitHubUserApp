package id.co.ardilobrt.githubuser.ui.detail.follow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.co.ardilobrt.githubuser.api.ApiConfig
import id.co.ardilobrt.githubuser.data.model.UsersItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {

    private var _listFollow = MutableLiveData<ArrayList<UsersItem>>()
    val listFollow: LiveData<ArrayList<UsersItem>> = _listFollow

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getFollower(username: String) {
        val clientFollower = ApiConfig.getApiService().getFollowers(username)
        setClient(clientFollower)
    }

    fun getFollowing(username: String) {
        val clientFollowing = ApiConfig.getApiService().getFollowing(username)
        setClient(clientFollowing)
    }

    private fun setClient(client: Call<ArrayList<UsersItem>>) {
        _isLoading.value = true
        client.enqueue(object : Callback<ArrayList<UsersItem>> {
            override fun onResponse(
                call: Call<ArrayList<UsersItem>>,
                response: Response<ArrayList<UsersItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listFollow.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<UsersItem>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "FollowersFragment"
    }
}