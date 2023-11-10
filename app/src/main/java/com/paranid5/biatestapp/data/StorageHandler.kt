package com.paranid5.biatestapp.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.paranid5.biatestapp.data.retrofit.Employee
import com.paranid5.biatestapp.data.retrofit.Employer
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StorageHandler @Inject constructor(@ApplicationContext context: Context) :
    CoroutineScope by CoroutineScope(Dispatchers.IO) {
    companion object {
        private val EMPLOYEE = stringPreferencesKey("employee")
        private val EMPLOYER = stringPreferencesKey("employer")
    }

    private val Context.dataStore by preferencesDataStore("logistics")
    private val dataStore = context.dataStore

    private val gson = Gson()

    @OptIn(ExperimentalCoroutinesApi::class)
    val employeeFlow: Flow<Employee?> = dataStore.data
        .mapLatest { preferences -> preferences[EMPLOYEE] }
        .mapLatest { gson.fromJson(it, Employee::class.java) }

    val employeeState = employeeFlow
        .stateIn(this, SharingStarted.Eagerly, null)

    suspend fun storeEmployee(employee: Employee?) {
        dataStore.edit { preferences ->
            preferences[EMPLOYEE] = employee?.let(gson::toJson) ?: ""
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val employerFlow: Flow<Employer?> = dataStore.data
        .mapLatest { preferences -> preferences[EMPLOYER] }
        .mapLatest { gson.fromJson(it, Employer::class.java) }

    val employerState = employerFlow
        .stateIn(this, SharingStarted.Eagerly, null)

    suspend fun storeEmployer(employer: Employer?) {
        dataStore.edit { preferences ->
            preferences[EMPLOYER] = employer?.let(gson::toJson) ?: ""
        }
    }
}