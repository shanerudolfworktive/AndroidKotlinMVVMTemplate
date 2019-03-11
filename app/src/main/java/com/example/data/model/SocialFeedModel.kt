package com.example.data.model

data class FeedModel @JvmOverloads constructor(
    val created_at: String?,
    val id: Long?,
    val userModel: UserModel?
    )

data class UserModel @JvmOverloads constructor(
    val id: Long?,
    val name: String?,
    val description: String?
    )