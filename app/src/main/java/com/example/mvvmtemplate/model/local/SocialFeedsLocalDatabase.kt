package com.example.mvvmtemplate.model.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mvvmtemplate.model.SocialFeedModel
import com.example.mvvmtemplate.model.UserModel
import com.example.mvvmtemplate.MainApplication
import com.example.mvvmtemplate.util.AppExecutors

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
                        .addCallback(callback)
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
                            SocialFeedModel(
                                id = 1,
                                created_at = "11am",
                                user = UserModel(
                                    id = 1,
                                    description = "user 1",
                                    name = "sh1"
                                )
                            )
                        )

                        socialFeedsDao.insertSocialFeed(
                            SocialFeedModel(
                                id = 2,
                                created_at = "12am",
                                user = UserModel(
                                    id = 2,
                                    description = "user 2",
                                    name = "sh2"
                                )
                            )
                        )

                        socialFeedsDao.insertSocialFeed(
                            SocialFeedModel(
                                id = 3,
                                created_at = "13am",
                                user = UserModel(
                                    id = 3,
                                    description = "user 3",
                                    name = "sh3"
                                )
                            )
                        )
                    }
                }
            }
    }

}