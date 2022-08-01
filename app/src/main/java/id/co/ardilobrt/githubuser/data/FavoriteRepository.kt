package id.co.ardilobrt.githubuser.data

import androidx.lifecycle.LiveData
import id.co.ardilobrt.githubuser.data.local.entity.FavoriteUser
import id.co.ardilobrt.githubuser.data.local.room.FavoriteUserDao
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class FavoriteRepository private constructor(
    private val favoriteUserDao: FavoriteUserDao,
    private val appExecutors: Executor = Executors.newSingleThreadExecutor()
) {

    fun setFavorite(user: FavoriteUser, favoriteState: Boolean) {
        appExecutors.execute {
            user.isFavorite = favoriteState
            favoriteUserDao.addFavorite(user)
        }
    }

    fun checkFavorite(id: Int) = favoriteUserDao.checkFavorite(id)

    fun deleteFavorite(id: Int) {
        appExecutors.execute {
            favoriteUserDao.deleteFavorite(id)
        }
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUser>> {
        return favoriteUserDao.getFavoriteUser()
    }

    companion object {
        @Volatile
        private var INSTANCE: FavoriteRepository? = null
        fun getInstance(favoriteUserDao: FavoriteUserDao): FavoriteRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: FavoriteRepository(favoriteUserDao)
            }.also { INSTANCE = it }
    }
}