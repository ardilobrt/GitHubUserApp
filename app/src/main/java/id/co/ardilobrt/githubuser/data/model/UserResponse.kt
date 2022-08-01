package id.co.ardilobrt.githubuser.data.model

import com.google.gson.annotations.SerializedName

data class UserResponse(

    @field:SerializedName("total_count")
    val totalUser: Int,

    @field:SerializedName("items")
    val items: ArrayList<UsersItem>
)
