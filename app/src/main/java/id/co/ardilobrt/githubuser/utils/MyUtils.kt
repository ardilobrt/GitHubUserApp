package id.co.ardilobrt.githubuser.utils

import android.content.Context
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.bumptech.glide.Glide
import id.co.ardilobrt.githubuser.R

object MyUtils {
    // To avoid human error (typo)
    const val EXTRA_USER = "extra_user"
    const val EXTRA_ID = "extra_id"

    val TAB_TITLES = intArrayOf(R.string.tab_1, R.string.tab_2)

    const val ARG_SECTION_NUMBER = "section_number"

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    // Avoid duplicate coding
    fun ImageView.loadImage(url: String?) {
        Glide.with(this.context)
            .load(url)
            .circleCrop()
            .into(this)
    }

    fun ProgressBar.showLoading(isLoading: Boolean) {
        this.visibility = if (isLoading) ProgressBar.VISIBLE else ProgressBar.GONE
    }
}