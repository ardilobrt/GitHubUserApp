package id.co.ardilobrt.githubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import id.co.ardilobrt.githubuser.data.local.entity.FavoriteUser

@Dao
interface FavoriteUserDao {

    @Query("SELECT * FROM favorite_user")
    fun getFavoriteUser(): LiveData<List<FavoriteUser>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addFavorite(favoriteUser: FavoriteUser)

    @Query("DELETE FROM favorite_user WHERE id = :id")
    fun deleteFavorite(id: Int): Int

    @Query("SELECT count(*) FROM favorite_user WHERE id = :id")
    fun checkFavorite(id: Int): Int
}