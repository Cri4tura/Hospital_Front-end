package com.example.panacea.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object SPLASH

@Serializable
object HOME

@Serializable
object LOGIN

@Serializable
object SIGNING

@Serializable
object DIRECTORY

@Serializable
data class DETAIL(val nurseId: Int)

@Serializable
object PROFILE

@Serializable
object DOCUMENTS

@Serializable
object NEWS

@Serializable
object HISTORY

