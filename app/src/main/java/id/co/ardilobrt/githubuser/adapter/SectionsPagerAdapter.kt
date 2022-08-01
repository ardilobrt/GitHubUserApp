package id.co.ardilobrt.githubuser.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.co.ardilobrt.githubuser.utils.MyUtils
import id.co.ardilobrt.githubuser.data.model.UsersItem
import id.co.ardilobrt.githubuser.ui.detail.follow.FollowersFragment
import id.co.ardilobrt.githubuser.ui.detail.follow.FollowingFragment

class SectionsPagerAdapter(activity: AppCompatActivity, data: UsersItem) : FragmentStateAdapter(activity) {

    private val fragmentBundle = data

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }
        fragment?.arguments = Bundle().apply {
            putInt(MyUtils.ARG_SECTION_NUMBER, position + 1)
            putString(MyUtils.EXTRA_USER, fragmentBundle.login)
        }
        return fragment as Fragment
    }
}