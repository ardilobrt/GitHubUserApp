package id.co.ardilobrt.githubuser.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailUserResponse(

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("followers")
    val followers: Int,

    @field:SerializedName("following")
    val following: Int,

    @field:SerializedName("public_repos")
    val publicRepos: Int,

    @field:SerializedName("location")
    val location: String?,

    @field:SerializedName("company")
    val company: String?,

    @field:SerializedName("followers_url")
    val followersUrl: String,

    @field:SerializedName("following_url")
    val followingUrl: String,
) : Parcelable