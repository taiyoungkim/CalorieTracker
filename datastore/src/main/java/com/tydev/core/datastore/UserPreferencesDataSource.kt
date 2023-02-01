package com.tydev.core.datastore

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>
) {

    val userData = userPreferences.data
        .map {
            UserData(
                genderEnum = when (it.gender) {
                    GenderProto.MALE -> GenderEnum.MALE
                    GenderProto.FEMALE -> GenderEnum.FEMALE
                    else -> GenderEnum.FEMALE
                },
                age = it.age,
                weight = it.weight,
                height = it.height,
                activityLevelEnum = when (it.activityLevel) {
                    ActivityLevelProto.LOW -> ActivityLevelEnum.LOW
                    ActivityLevelProto.MEDIUM -> ActivityLevelEnum.MEDIUM
                    ActivityLevelProto.HIGH -> ActivityLevelEnum.HIGH
                    else -> ActivityLevelEnum.MEDIUM
                },
                goalTypeEnum = when (it.goalType) {
                    GoalTypeProto.GAIN_WEIGHT -> GoalTypeEnum.GAIN_WEIGHT
                    GoalTypeProto.KEEP_WEIGHT -> GoalTypeEnum.KEEP_WEIGHT
                    GoalTypeProto.LOSE_WEIGHT -> GoalTypeEnum.LOSE_WEIGHT
                    else -> GoalTypeEnum.KEEP_WEIGHT
                },
                carbRatio = it.carbRatio,
                proteinRatio = it.proteinRatio,
                fatRatio = it.fatRatio,
                shouldShowOnboarding = it.shouldShowOnboarding
            )
        }

    suspend fun setGender(genderEnum: GenderEnum) {
        userPreferences.updateData {
            it.copy {
                this.gender = when (genderEnum) {
                    GenderEnum.MALE -> GenderProto.MALE
                    GenderEnum.FEMALE -> GenderProto.FEMALE
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

    suspend fun setActivityLevel(activityLevelEnum: ActivityLevelEnum) {
        userPreferences.updateData {
            it.copy {
                this.activityLevel = when (activityLevelEnum) {
                    ActivityLevelEnum.LOW -> ActivityLevelProto.LOW
                    ActivityLevelEnum.MEDIUM -> ActivityLevelProto.MEDIUM
                    ActivityLevelEnum.HIGH -> ActivityLevelProto.HIGH
                }
            }
        }
    }

    suspend fun setGoalType(goalTypeEnum: GoalTypeEnum) {
        userPreferences.updateData {
            it.copy {
                this.goalType = when (goalTypeEnum) {
                    GoalTypeEnum.GAIN_WEIGHT -> GoalTypeProto.GAIN_WEIGHT
                    GoalTypeEnum.KEEP_WEIGHT -> GoalTypeProto.KEEP_WEIGHT
                    GoalTypeEnum.LOSE_WEIGHT -> GoalTypeProto.LOSE_WEIGHT
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
