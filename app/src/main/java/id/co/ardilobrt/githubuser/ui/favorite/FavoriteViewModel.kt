package id.co.ardilobrt.githubuser.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.co.ardilobrt.githubuser.data.FavoriteRepository
import id.co.ardilobrt.githubuser.data.local.entity.FavoriteUser

class FavoriteViewModel(private val repository: FavoriteRepository) : ViewModel() {

    val text = MutableLiveData<String>().apply {
        value = "Please Set Favorite User"
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUser>> = repository.getFavoriteUser()
}