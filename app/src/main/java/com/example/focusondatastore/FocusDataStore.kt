package com.example.focusondatastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FocusDataStore(private val context: Context){

    companion object {

        private const val DATASTORE_NAME = "focusDataStore"

        private val EMAIL_KEY = stringPreferencesKey("email_key")

        private val PASSWORD_KEY = stringPreferencesKey("password_key")

        private val Context.focusDataStore by preferencesDataStore(name = DATASTORE_NAME)

    }

    suspend fun setEmail(email : String){
        context.focusDataStore.edit { preferences ->
            preferences[EMAIL_KEY] = email
        }
    }

    suspend fun setPassword(password : String){
        context.focusDataStore.edit { preferences ->
            preferences[PASSWORD_KEY] = password
        }
    }

    val email: Flow<String>
        get() = context.focusDataStore.data.map {
            it[EMAIL_KEY] ?: ""
        }

    val password: Flow<String>
        get() = context.focusDataStore.data.map {
            it[PASSWORD_KEY] ?: ""
        }

}