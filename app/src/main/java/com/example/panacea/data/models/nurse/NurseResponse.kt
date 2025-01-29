package com.example.panacea.data.models.nurse

import kotlinx.serialization.Serializable

@Serializable
data class NurseResponse(
    val count: Int,
    val data: List<Nurse>,
    val status: String
)