package com.tydev.core.datastore

import androidx.datastore.core.DataStore
import com.tydev.core.domain.model.ActivityLevel
import com.tydev.core.domain.model.Gender
import com.tydev.core.domain.model.GoalType
import com.tydev.core.domain.model.UserData
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>,
) {

    val userData = userPreferences.data
        .map {
            UserData(
                gender = when (it.gender) {
                    GenderProto.MALE -> Gender.MALE
                    GenderProto.FEMALE -> Gender.FEMALE
                    else -> Gender.FEMALE
                },
                age = it.age,
                weight = it.weight,
                height = it.height,
                activityLevel = when (it.activityLevel) {
                    ActivityLevelProto.LOW -> ActivityLevel.LOW
                    ActivityLevelProto.MEDIUM -> ActivityLevel.MEDIUM
                    ActivityLevelProto.HIGH -> ActivityLevel.HIGH
                    else -> ActivityLevel.MEDIUM
                },
                goalType = when (it.goalType) {
                    GoalTypeProto.GAIN_WEIGHT -> GoalType.GAIN_WEIGHT
                    GoalTypeProto.KEEP_WEIGHT -> GoalType.KEEP_WEIGHT
                    GoalTypeProto.LOSE_WEIGHT -> GoalType.LOSE_WEIGHT
                    else -> GoalType.KEEP_WEIGHT
                },
                carbRatio = it.carbRatio,
                proteinRatio = it.proteinRatio,
                fatRatio = it.fatRatio,
                shouldShowOnboarding = it.shouldShowOnboarding,
            )
        }

    suspend fun setGender(gender: Gender) {
        userPreferences.updateData {
            it.copy {
                this.gender = when (gender) {
                    Gender.MALE -> GenderProto.MALE
                    Gender.FEMALE -> GenderProto.FEMALE
                }
            }
        }
    }

    suspend fun setAge(age: Int) {
        userPreferences.updateData {
            it.copy {
                this.age = age
            }
        }
    }

    suspend fun setWeight(weight: Float) {
        userPreferences.updateData {
            it.copy {
                this.weight = weight
            }
        }
    }

    suspend fun setHeight(height: Int) {
        userPreferences.updateData {
            it.copy {
                this.height = height
            }
        }
    }

    suspend fun setActivityLevel(activityLevel: ActivityLevel) {
        userPreferences.updateData {
            it.copy {
                this.activityLevel = when (activityLevel) {
                    ActivityLevel.LOW -> ActivityLevelProto.LOW
                    ActivityLevel.MEDIUM -> ActivityLevelProto.MEDIUM
                    ActivityLevel.HIGH -> ActivityLevelProto.HIGH
                }
            }
        }
    }

    suspend fun setGoalType(goalType: GoalType) {
        userPreferences.updateData {
            it.copy {
                this.goalType = when (goalType) {
                    GoalType.GAIN_WEIGHT -> GoalTypeProto.GAIN_WEIGHT
                    GoalType.KEEP_WEIGHT -> GoalTypeProto.KEEP_WEIGHT
                    GoalType.LOSE_WEIGHT -> GoalTypeProto.LOSE_WEIGHT
                }
            }
        }
    }

    suspend fun setCarbRatio(ratio: Float) {
        userPreferences.updateData {
            it.copy {
                this.carbRatio = ratio
            }
        }
    }

    suspend fun setProteinRatio(ratio: Float) {
        userPreferences.updateData {
            it.copy {
                this.proteinRatio = ratio
            }
        }
    }

    suspend fun setFatRatio(ratio: Float) {
        userPreferences.updateData {
            it.copy {
                this.fatRatio = ratio
            }
        }
    }

    suspend fun setShouldShowOnboarding(shouldShowOnboarding: Boolean) {
        userPreferences.updateData {
            it.copy {
                this.shouldShowOnboarding = shouldShowOnboarding
            }
        }
    }
}
