package com.tydev.core.datastore

data class UserData(
    val genderEnum: GenderEnum,
    val age: Int,
    val weight: Float,
    val height: Int,
    val activityLevelEnum: ActivityLevelEnum,
    val goalTypeEnum: GoalTypeEnum,
    val carbRatio: Float,
    val proteinRatio: Float,
    val fatRatio: Float,
    val shouldShowOnboarding: Boolean,
)