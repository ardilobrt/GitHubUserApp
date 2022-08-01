package id.co.ardilobrt.githubuser.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import id.co.ardilobrt.githubuser.R
import id.co.ardilobrt.githubuser.adapter.UserAdapter
import id.co.ardilobrt.githubuser.data.local.entity.FavoriteUser
import id.co.ardilobrt.githubuser.data.model.UsersItem
import id.co.ardilobrt.githubuser.databinding.ActivityFavoriteBinding
import id.co.ardilobrt.githubuser.ui.detail.DetailUserActivity
import id.co.ardilobrt.githubuser.utils.MyUtils

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.favorite)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        observerValue()
    }

    private fun observerValue() {
        val viewModel: FavoriteViewModel by viewModels {
            FavoriteViewModelFactory.getInstance(this)
        }

        viewModel.text.observe(this) {
            binding.includeEmpty.tvEmpty.text = it
        }

        viewModel.getFavoriteUser().observe(this) {
            if (it != null){
                binding.includeEmpty.tvEmpty.visibility =
                    if (it.isEmpty()) View.VISIBLE else View.GONE
                showUser(it)
            }
        }
    }

    private fun showUser(list: List<FavoriteUser>) {
        val linearLayoutManager = LinearLayoutManager(this)
        val itemDecoration = DividerItemDecoration(this, linearLayoutManager.orientation)
        val userAdapter = UserAdapter()

        val arrayList = mapList(list)
        userAdapter.setList(arrayList)

        binding.rvUsers.apply {
            layoutManager = linearLayoutManager
            addItemDecoration(itemDecoration)
            adapter = userAdapter
        }

        userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UsersItem) {
                Intent(this@FavoriteActivity, DetailUserActivity::class.java).also {
                    it.putExtra(MyUtils.EXTRA_USER, data)
                    it.putExtra(MyUtils.EXTRA_ID, data.id)
                    startActivity(it)
                }
            }
        })
    }

    // Make list to arraylist
    private fun mapList(userLocal: List<FavoriteUser>): ArrayList<UsersItem> {
        val listUser = ArrayList<UsersItem>()
        for (user in userLocal) {
            val userMapped = UsersItem(user.id, user.login, user.avatarUrl)
            listUser.add(userMapped)
        }
        return listUser
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}