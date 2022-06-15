package com.example.wbinternw7part3.model

import kotlinx.serialization.Serializable

@Serializable
data class FavoriteData(
    val id: String,
    val url: String
)