package com.example.submission1.ui.setting

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.submission1.R
import com.example.submission1.databinding.ActivityThemeBinding
import com.google.android.material.switchmaterial.SwitchMaterial

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class ThemeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityThemeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThemeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)

        val pref = SettingPreferences.getInstance(dataStore)
        val themeViewModel = ViewModelProvider(this, ViewModelFactory(pref))[ThemeViewModel::class.java]

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        themeViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            themeViewModel.saveThemeSetting(isChecked)
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        val pref = SettingPreferences.getInstance(dataStore)
        val themeViewModel = ViewModelProvider(this, ViewModelFactory(pref))[ThemeViewModel::class.java]

        val currentThemeSetting = themeViewModel.getThemeSettings()
        currentThemeSetting.observe(this) { isDarkModeActive: Boolean ->
            themeViewModel.saveThemeSetting(isDarkModeActive)
            finish()
        }
        return super.onSupportNavigateUp()
    }

}