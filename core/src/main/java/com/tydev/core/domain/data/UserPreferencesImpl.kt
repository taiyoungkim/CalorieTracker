package com.tydev.core.domain.data

import android.content.SharedPreferences
import com.tydev.core.domain.model.ActivityLevel
import com.tydev.core.domain.model.Gender
import com.tydev.core.domain.model.GoalType
import com.tydev.core.domain.model.UserInfo
import com.tydev.core.domain.preferences.UserPreferences

// TODO: DataStore Migration
class UserPreferencesImpl(
    private val sharedPref: SharedPreferences
) : UserPreferences {
    override fun saveGender(gender: Gender) {
        sharedPref.edit()
            .putString(UserPreferences.KEY_GENDER, gender.name)
            .apply()
    }

    override fun saveAge(age: Int) {
        sharedPref.edit()
            .putInt(UserPreferences.KEY_AGE, age)
            .apply()
    }

    override fun saveWeight(weight: Float) {
        sharedPref.edit()
            .putFloat(UserPreferences.KEY_WEIGHT, weight)
            .apply()
    }

    override fun saveHeight(height: Int) {
        sharedPref.edit()
            .putInt(UserPreferences.KEY_HEIGHT, height)
            .apply()
    }

    override fun saveActivityLevel(level: ActivityLevel) {
        sharedPref.edit()
            .putString(UserPreferences.KEY_ACTIVITY_LEVEL, level.name)
            .apply()
    }

    override fun saveGoalType(type: GoalType) {
        sharedPref.edit()
            .putString(UserPreferences.KEY_GOAL_TYPE, type.name)
            .apply()
    }

    override fun saveCarbRatio(ratio: Float) {
        sharedPref.edit()
            .putFloat(UserPreferences.KEY_CARB_RATIO, ratio)
            .apply()
    }

    override fun saveProteinRatio(ratio: Float) {
        sharedPref.edit()
            .putFloat(UserPreferences.KEY_PROTEIN_RATIO, ratio)
            .apply()
    }

    override fun saveFatRatio(ratio: Float) {
        sharedPref.edit()
            .putFloat(UserPreferences.KEY_FAT_RATIO, ratio)
            .apply()
    }

    override fun loadUserInfo(): UserInfo {
        val age = sharedPref.getInt(UserPreferences.KEY_AGE, -1)
        val height = sharedPref.getInt(UserPreferences.KEY_HEIGHT, -1)
        val weight = sharedPref.getFloat(UserPreferences.KEY_WEIGHT, -1f)
        val genderString = sharedPref.getString(UserPreferences.KEY_GENDER, null)
        val activityLevelString = sharedPref.getString(UserPreferences.KEY_ACTIVITY_LEVEL, null)
        val goalType = sharedPref.getString(UserPreferences.KEY_GOAL_TYPE, null)
        val carbRatio = sharedPref.getFloat(UserPreferences.KEY_CARB_RATIO, -1f)
        val proteinRatio = sharedPref.getFloat(UserPreferences.KEY_PROTEIN_RATIO, -1f)
        val fatRatio = sharedPref.getFloat(UserPreferences.KEY_FAT_RATIO, -1f)

        return UserInfo(
            gender = Gender.fromString(genderString ?: "male"),
            age = age,
            weight = weight,
            height = height,
            activityLevel = ActivityLevel.fromString(activityLevelString ?: "medium"),
            goalType = GoalType.fromString(goalType ?: "keep_weight"),
            carbRatio = carbRatio,
            proteinRatio = proteinRatio,
            fatRatio = fatRatio
        )
    }

    override fun saveShouldShowOnboarding(shouldShow: Boolean) {
        sharedPref.edit()
            .putBoolean(UserPreferences.KEY_SHOULD_SHOW_ONBOARDING, shouldShow)
            .apply()
    }

    override fun loadShouldShowOnboarding(): Boolean =
        sharedPref.getBoolean(UserPreferences.KEY_SHOULD_SHOW_ONBOARDING, true)
}
