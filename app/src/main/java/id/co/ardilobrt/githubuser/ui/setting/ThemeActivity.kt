package id.co.ardilobrt.githubuser.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import id.co.ardilobrt.githubuser.R
import id.co.ardilobrt.githubuser.databinding.ActivityThemeBinding
import id.co.ardilobrt.githubuser.utils.MyUtils.dataStore

class ThemeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThemeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThemeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.setting)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val preferences = SettingPreferences.getInstance(dataStore)
        val viewModel by viewModels<ThemeViewModel> {
            ThemeViewModelFactory.getInstance(preferences)
        }

        viewModel.getThemeSettings().observe(this) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchTheme.isChecked = false
            }
        }
        
        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            viewModel.saveThemeSetting(isChecked)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}