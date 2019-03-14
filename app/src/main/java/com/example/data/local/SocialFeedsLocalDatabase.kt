package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.model.SocialFeedModel
import com.example.data.model.UserModel
import com.example.mvvmtemplate.MainApplication
import com.example.util.AppExecutors

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
//                        .addCallback(callback)
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return INSTANCE!!
            }
        }

        private val callback: Callback
            get() = object: RoomDatabase.Callback(){
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)

                    AppExecutors().diskIO.execute{
                        val socialFeedsDao = INSTANCE!!.socialFeedsDao()
                        socialFeedsDao.insertSocialFeed(
                            SocialFeedModel(id = 1,
                                created_at = "11am",
                                userModel = UserModel(description = "user 1", name = "sh1")))

                        socialFeedsDao.insertSocialFeed(
                            SocialFeedModel(id = 2,
                                created_at = "12am",
                                userModel = UserModel(description = "user 2", name = "sh2")))

                        socialFeedsDao.insertSocialFeed(
                            SocialFeedModel(id = 3,
                                created_at = "13am",
                                userModel = UserModel(description = "user 3", name = "sh3")))
                    }
                }
            }
    }

}