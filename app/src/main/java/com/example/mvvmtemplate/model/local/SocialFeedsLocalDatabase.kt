package com.example.mvvmtemplate.model.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mvvmtemplate.MainApplication
import com.example.mvvmtemplate.model.SocialFeedModel

@Database(entities = arrayOf(SocialFeedModel::class), version = 1)
abstract class SocialFeedsLocalDatabase : RoomDatabase() {
    abstract fun socialFeedsDao(): SocialFeedsDao

    companion object {
        private var INSTANCE: SocialFeedsLocalDatabase? = null
        private val lock = Any()
        fun getInstance(): SocialFeedsLocalDatabase{
            synchronized(lock) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(MainApplication.appContext,
                        SocialFeedsLocalDatabase::class.java,
                        "SocialFeedsLocalDatabase.db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return INSTANCE!!
            }
        }
    }

}