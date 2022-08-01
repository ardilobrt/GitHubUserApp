package id.co.ardilobrt.githubuser.di

import android.content.Context
import id.co.ardilobrt.githubuser.data.FavoriteRepository
import id.co.ardilobrt.githubuser.data.local.room.UserDatabase

object Injection {

    fun provideRepository(context: Context): FavoriteRepository {
        val database = UserDatabase.getInstance(context)
        val dao = database.userDao()
        return FavoriteRepository.getInstance(dao)
    }
}