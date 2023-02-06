package com.tydev.core.data.repository

import com.tydev.core.datastore.UserPreferencesDataSource
import com.tydev.core.domain.model.ActivityLevel
import com.tydev.core.domain.model.Gender
import com.tydev.core.domain.model.GoalType
import com.tydev.core.domain.model.UserData
import com.tydev.core.domain.repository.UserDataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val userPreferencesDataSource: UserPreferencesDataSource,
) : UserDataRepository {

    override val userData: Flow<UserData> = userPreferencesDataSource.userData

    override suspend fun setGender(gender: Gender) {
        userPreferencesDataSource.setGender(gender)
    }

    override suspend fun setAge(age: Int) {
        userPreferencesDataSource.setAge(age)
    }

    override suspend fun setWeight(weight: Float) {
        userPreferencesDataSource.setWeight(weight)
    }

    override suspend fun setHeight(height: Int) {
        userPreferencesDataSource.setHeight(height)
    }

    override suspend fun setActivityLevel(activityLevel: ActivityLevel) {
        userPreferencesDataSource.setActivityLevel(activityLevel)
    }

    override suspend fun setGoalType(goalType: GoalType) {
        userPreferencesDataSource.setGoalType(goalType)
    }

    override suspend fun setCarbRatio(ratio: Float) {
        userPreferencesDataSource.setCarbRatio(ratio)
    }

    override suspend fun setProteinRatio(ratio: Float) {
        userPreferencesDataSource.setProteinRatio(ratio)
    }

    override suspend fun setFatRatio(ratio: Float) {
        userPreferencesDataSource.setFatRatio(ratio)
    }

    override suspend fun setShouldShowOnboarding(shouldShowOnboarding: Boolean) {
        userPreferencesDataSource.setShouldShowOnboarding(shouldShowOnboarding)
    }
}
