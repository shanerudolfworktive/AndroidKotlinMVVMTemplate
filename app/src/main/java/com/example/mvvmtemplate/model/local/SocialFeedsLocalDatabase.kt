package com.example.mvvmtemplate.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mvvmtemplate.MainApplication
import com.example.mvvmtemplate.model.GitHubRepoModel
import com.example.mvvmtemplate.model.SocialFeedModel

@Database(entities = arrayOf(SocialFeedModel::class), version = 1)
abstract class SocialFeedsLocalDatabase : RoomDatabase() {
    abstract fun socialFeedsDao(): SocialFeedsDao

    companion object {
        @Volatile private var INSTANCE: SocialFeedsLocalDatabase? = null
        fun getInstance(): SocialFeedsLocalDatabase{
            return INSTANCE?: synchronized(this){
                return INSTANCE?: Room.databaseBuilder(MainApplication.appContext,
                    SocialFeedsLocalDatabase::class.java,
                    "SocialFeedsLocalDatabase.db")
                    .fallbackToDestructiveMigration()
                    .build().also { INSTANCE = it }
            }

        }
    }
}