package id.co.ardilobrt.githubuser.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import id.co.ardilobrt.githubuser.R
import id.co.ardilobrt.githubuser.adapter.UserAdapter
import id.co.ardilobrt.githubuser.data.model.UsersItem
import id.co.ardilobrt.githubuser.databinding.ActivityMainBinding
import id.co.ardilobrt.githubuser.ui.detail.DetailUserActivity
import id.co.ardilobrt.githubuser.ui.favorite.FavoriteActivity
import id.co.ardilobrt.githubuser.ui.setting.SettingPreferences
import id.co.ardilobrt.githubuser.ui.setting.ThemeActivity
import id.co.ardilobrt.githubuser.utils.MyUtils
import id.co.ardilobrt.githubuser.utils.MyUtils.dataStore
import id.co.ardilobrt.githubuser.utils.MyUtils.showLoading


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var factory: MainViewModelFactory
    private val viewModel by viewModels<MainViewModel> { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Make splash screen without activity & layout
        // https://developer.android.com/guide/topics/ui/splash-screen/migrate
        installSplashScreen()

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferences = SettingPreferences.getInstance(dataStore)
        factory = MainViewModelFactory.getInstance(preferences)

        setSearching()
        observerValue()
    }

    private fun setSearching() {
        binding.apply {
            edSearch.setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {

                    includeEmpty.tvEmpty.visibility = View.GONE

                    searchUser()

                    val inputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)

                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }
    }

    private fun searchUser() {
        val query = binding.edSearch.text.toString()
        if (query.isEmpty()) return
        viewModel.setSearchUser(query)
    }

    private fun observerValue() {
        viewModel.getThemeSettings().observe(this) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        viewModel.listUser.observe(this) {
            if (it != null) {
                binding.includeEmpty.tvEmpty.visibility =
                    if (it.isEmpty()) View.VISIBLE else View.GONE
                showUsers(it)
            }
        }

        viewModel.isLoading.observe(this) {
            binding.includeProgressBar.progressBar.showLoading(it)
        }

    }

    private fun showUsers(arrayList: ArrayList<UsersItem>) {
        val linearLayoutManager = LinearLayoutManager(this)
        val itemDecoration = DividerItemDecoration(this, linearLayoutManager.orientation)
        val userAdapter = UserAdapter()

        userAdapter.setList(arrayList)

        binding.rvUsers.apply {
            layoutManager = linearLayoutManager
            addItemDecoration(itemDecoration)
            adapter = userAdapter
        }

        userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UsersItem) {
                Intent(this@MainActivity, DetailUserActivity::class.java).also {
                    it.putExtra(MyUtils.EXTRA_USER, data)
                    it.putExtra(MyUtils.EXTRA_ID, data.id)
                    startActivity(it)
                }
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.setting -> {
                Intent(this, ThemeActivity::class.java).also {
                    startActivity(it)
                    return true
                }
            }
            R.id.favorite -> {
                Intent(this, FavoriteActivity::class.java).also {
                    startActivity(it)
                    return true
                }
            }
            else -> return true
        }
    }
}