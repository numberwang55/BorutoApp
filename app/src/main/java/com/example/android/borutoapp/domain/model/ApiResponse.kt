package com.example.android.borutoapp.domain.model

import com.example.android.borutoapp.domain.model.entity.HeroEntity
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    val heroes: List<HeroEntity> = emptyList(),
    val message: String? = null,
    val previousPage: Int? = null,
    val nextPage: Int? = null,
    val success: Boolean,
    val lastUpdated: Long? = null
)
