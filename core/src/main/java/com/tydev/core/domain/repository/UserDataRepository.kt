package com.tydev.core.domain.repository

import com.tydev.core.domain.model.ActivityLevel
import com.tydev.core.domain.model.Gender
import com.tydev.core.domain.model.GoalType
import com.tydev.core.domain.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    fun getUserData(): Flow<UserData>

    suspend fun setGender(gender: Gender)

    suspend fun setAge(age: Int)

    suspend fun setWeight(weight: Float)

    suspend fun setHeight(height: Int)

    suspend fun setActivityLevel(activityLevel: ActivityLevel)

    suspend fun setGoalType(goalType: GoalType)

    suspend fun setCarbRatio(ratio: Float)

    suspend fun setProteinRatio(ratio: Float)

    suspend fun setFatRatio(ratio: Float)

    suspend fun setShouldShowOnboarding(shouldShowOnboarding: Boolean)
}
