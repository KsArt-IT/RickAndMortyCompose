package pro.ksart.rickandmorty.data.repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import pro.ksart.rickandmorty.di.IoDispatcher
import pro.ksart.rickandmorty.domain.repository.PreferencesRepository
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(
    @ApplicationContext context: Context,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : PreferencesRepository {

    private val _isDarkThemeState = MutableStateFlow(false)
    override val isDarkThemeState = _isDarkThemeState.asStateFlow()

    private val defaultPreferences by lazy {
        context.getSharedPreferences(context.packageName + "_preferences", Context.MODE_PRIVATE)
    }

    private var isDarkTheme: Boolean
        get() = defaultPreferences.getBoolean(PREF_KEY_DARK_THEME, false)
        private set(value) = defaultPreferences.edit().putBoolean(PREF_KEY_DARK_THEME, value)
            .apply()

    private val listener by lazy {
        SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == PREF_KEY_DARK_THEME) _isDarkThemeState.value = isDarkTheme
        }
    }

    override suspend fun switchDarkTheme() {
        withContext(dispatcher) {
            isDarkTheme = !isDarkTheme
            Log.d("ram151", "PreferencesRepositoryImpl|switchDarkTheme $isDarkTheme")
        }
    }

    override suspend fun registerChangeSettings() {
        withContext(dispatcher) {
            defaultPreferences.registerOnSharedPreferenceChangeListener(listener)
            _isDarkThemeState.value = isDarkTheme
        }
    }

    override fun unregisterChangeSettings() {
        defaultPreferences.unregisterOnSharedPreferenceChangeListener(listener)
    }

    private companion object {
        const val PREF_KEY_DARK_THEME = "PREF_KEY_DARK_THEME"
    }
}
