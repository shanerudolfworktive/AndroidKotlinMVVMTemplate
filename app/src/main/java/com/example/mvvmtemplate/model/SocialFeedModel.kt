package com.example.mvvmtemplate.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "SocialFeedsTable")
data class SocialFeedModel @JvmOverloads constructor(
    val created_at: String? = SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(Date()),
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    @Embedded(prefix = "user")
    val user: UserModel?
    )

data class UserModel @JvmOverloads constructor(
    val id: Long? = 1,
    val name: String?,
    val description: String?
    )