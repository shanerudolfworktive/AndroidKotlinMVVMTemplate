package com.example.mvvmtemplate.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SocialFeedsTable")
data class SocialFeedModel @JvmOverloads constructor(
    val created_at: String?,
    @PrimaryKey val id: Long?,
    @Embedded val userModel: UserModel?
    )

data class UserModel @JvmOverloads constructor(
    val name: String?,
    val description: String?
    )