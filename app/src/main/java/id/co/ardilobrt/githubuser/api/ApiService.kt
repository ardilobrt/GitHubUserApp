package id.co.ardilobrt.githubuser.api

import id.co.ardilobrt.githubuser.BuildConfig
import id.co.ardilobrt.githubuser.data.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users") // https://api.github.com/search/users?q={username}
    @Headers(BuildConfig.GithubApi)
    fun getUserByQuery(
        @Query("q") query: String,
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers(BuildConfig.GithubApi)
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers(BuildConfig.GithubApi)
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<UsersItem>>

    @GET("users/{username}/following")
    @Headers(BuildConfig.GithubApi)
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<UsersItem>>
}