package com.tydev.tracker.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class SearchDto(
    val products: List<Product>,
)
