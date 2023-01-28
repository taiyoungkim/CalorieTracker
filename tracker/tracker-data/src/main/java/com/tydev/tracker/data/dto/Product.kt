package com.tydev.tracker.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    @SerialName("image_front_thumb_url")
    val imageFrontThumbUrl: String? = null,
    val nutriments: Nutriments,
    @SerialName("product_name")
    val productName: String? = null
)
