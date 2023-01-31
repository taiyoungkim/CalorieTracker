package com.tydev.core.data

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>
) {

    val userData = userPreferences.data.map {
        it
    }
}
