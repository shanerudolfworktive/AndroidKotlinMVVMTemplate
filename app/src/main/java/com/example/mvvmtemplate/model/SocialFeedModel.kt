package com.example.mvvmtemplate.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SocialFeedsTable")
data class SocialFeedModel @JvmOverloads constructor(
    val created_at: String?,
    @PrimaryKey
    val id: Long?,
    @Embedded(prefix = "user")
    val user: UserModel?
    )

data class UserModel @JvmOverloads constructor(
    val id: Long?,
    val name: String?,
    val description: String?
    )