package id.co.ardilobrt.githubuser.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "favorite_user")
data class FavoriteUser(
    @field:ColumnInfo(name = "id")
    @field:PrimaryKey
    val id: Int,

    @field:ColumnInfo(name = "login")
    val login: String,

    @field:ColumnInfo(name = "avatarUrl")
    val avatarUrl: String,

    @field:ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean
) : Parcelable
