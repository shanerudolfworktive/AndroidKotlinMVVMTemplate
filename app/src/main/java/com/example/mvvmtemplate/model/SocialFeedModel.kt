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
    var id: Long? = null,
    @Embedded(prefix = "user")
    var user: UserModel?
)

data class UserModel @JvmOverloads constructor(
    val id: Long? = 1,
    var name: String?,
    var description: String?
)