package com.leshen.letseatmobile.model

import kotlinx.serialization.Serializable
@Serializable
data class Restaurant (
    val Name: String,
    val category: String,
)