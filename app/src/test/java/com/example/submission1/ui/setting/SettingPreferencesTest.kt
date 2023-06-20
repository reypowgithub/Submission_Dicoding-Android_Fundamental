package com.example.submission1.ui.setting

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.edit
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class SettingPreferencesTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var settingPreferences: SettingPreferences

    @Before
    fun setUp() {
        dataStore = mock(DataStore::class.java) as DataStore<Preferences>
        settingPreferences = SettingPreferences.getInstance(dataStore)
    }

    @Test
    fun `test getThemeSetting() returns default value`() = runBlocking {
        // given
        `when`(dataStore.data).thenReturn(flowOf(emptyPreferences()))

        // when
        val result = settingPreferences.getThemeSetting().first()

        // then
        assertEquals(false, result)
    }

    @Test
    fun`test getThemeSetting() returns saved value`() = runBlocking {
        // given
        val themeKey = booleanPreferencesKey("theme_setting")
        val savedValue = false
        val preferences = mock(Preferences::class.java)
        `when`(preferences[themeKey]).thenReturn(savedValue)
        `when`(dataStore.data).thenReturn(flowOf(preferences))

        // when
        val result = settingPreferences.getThemeSetting().first()

        // then
        assertEquals(savedValue, result)
    }
}