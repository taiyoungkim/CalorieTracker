package com.tydev.tracker.presentation.overview

import androidx.annotation.DrawableRes
import com.tydev.core.R
import com.tydev.core.ui.util.UiText
import com.tydev.tracker.domain.model.MealType

// presentation model
// 왜 얘 혼자만 presentation layer 에 있는거? domain 에
// ui 에 직접적으로 show 하기 위한 용도의 data class 이기 때문
// 간편하게 ui state 를 다루기 위해서
// 하나의 화면만을 위한 wrapper class
data class Meal(
    val name: UiText,
    @DrawableRes val drawableRes: Int,
    val mealType: MealType,
    val carbs: Int = 0,
    val protein: Int = 0,
    val fat: Int = 0,
    val calories: Int = 0,
    val isExpanded: Boolean = false,
)

val defaultMeals = listOf(
    Meal(
        name = UiText.StringResource(R.string.breakfast),
        drawableRes = R.drawable.ic_breakfast,
        mealType = MealType.Breakfast,
    ),

    Meal(
        name = UiText.StringResource(R.string.lunch),
        drawableRes = R.drawable.ic_lunch,
        mealType = MealType.Lunch,
    ),

    Meal(
        name = UiText.StringResource(R.string.dinner),
        drawableRes = R.drawable.ic_dinner,
        mealType = MealType.Dinner,
    ),

    Meal(
        name = UiText.StringResource(R.string.snacks),
        drawableRes = R.drawable.ic_snack,
        mealType = MealType.Snack,
    ),
)
