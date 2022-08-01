package id.co.ardilobrt.githubuser.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import id.co.ardilobrt.githubuser.R
import id.co.ardilobrt.githubuser.adapter.SectionsPagerAdapter
import id.co.ardilobrt.githubuser.data.local.entity.FavoriteUser
import id.co.ardilobrt.githubuser.data.model.DetailUserResponse
import id.co.ardilobrt.githubuser.utils.MyUtils
import id.co.ardilobrt.githubuser.utils.MyUtils.loadImage
import id.co.ardilobrt.githubuser.utils.NumberStyle
import id.co.ardilobrt.githubuser.data.model.UsersItem
import id.co.ardilobrt.githubuser.databinding.ActivityDetailUserBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var username: UsersItem
    private val viewModel: DetailUserViewModel by viewModels {
        DetailViewModelFactory.getInstance(this)
    }
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = getString(R.string.detail)
            setDisplayHomeAsUpEnabled(true)
            supportActionBar?.elevation = 0f
        }

        setContent()
        observerValue()
    }

    private fun setContent() {
        username = intent.getParcelableExtra<UsersItem>(MyUtils.EXTRA_USER) as UsersItem

        val sectionsPagerAdapter = SectionsPagerAdapter(this, username)
        binding.viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(MyUtils.TAB_TITLES[position])
        }.attach()
    }

    private fun observerValue() {

        viewModel.getDetailUser(username.login)

        viewModel.user.observe(this) {
            if (it != null) {
                // Make object from class NumberStyle (1000 -> 1k)
                val numberFollower = NumberStyle.setFormat(it.followers)
                val numberFollowing = NumberStyle.setFormat(it.following)
                val numberRepo = NumberStyle.setFormat(it.publicRepos)

                binding.apply {
                    ivDetailPhoto.loadImage(it.avatarUrl)
                    tvDetailName.text = it.name
                    tvDetailUsername.text = it.login
                    // View binding include layout
                    includeDetail.apply {
                        includeFollower.tvTotalFollower.text = numberFollower
                        includeFollower.tvTotalFollowing.text = numberFollowing
                        tvTotalRepo.text = numberRepo
                        tvLocation.text = it.location?.ifBlank { null } ?: "-"
                        tvCompany.text = it.company?.ifBlank { null } ?: "-"
                    }
                }

                setFavoriteListener(it)
            }
        }
    }

    private fun setFavoriteListener(user: DetailUserResponse) {
        val id = intent.getIntExtra(MyUtils.EXTRA_ID, 0)
        val userLocal = FavoriteUser(id, user.login, user.avatarUrl, isFavorite)

        binding.fabFavorite.setOnClickListener {
            if (!isFavorite) {
                isFavorite = true
                setIconFavorite(isFavorite)
                viewModel.saveFavoriteUser(userLocal)
            } else showAlertDialog(id)
        }

        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkFavorite(id)
            withContext(Dispatchers.Main) {
                if (count > 0) setIconFavorite(true) else setIconFavorite(false)
            }
        }
    }

    private fun showAlertDialog(id: Int) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.apply {
            setTitle("Delete Bookmark")
            setMessage("Are you sure to delete this favorite?")
            setCancelable(false)
            setPositiveButton("Yes") { _, _ ->
                isFavorite = false
                setIconFavorite(isFavorite)
                viewModel.deleteFavorite(id)
            }
            setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
            create()
            show()
        }
    }

    private fun setIconFavorite(isActive: Boolean) {
        isFavorite = isActive
        binding.fabFavorite.apply {
            if (isFavorite) setImageResource(R.drawable.ic_favorite_active)
            else setImageResource(R.drawable.ic_favorite_inactive)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}