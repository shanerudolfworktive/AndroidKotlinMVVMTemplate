package com.example.mvvmtemplate.di.modules

import android.content.Context
import androidx.room.Room
import com.example.mvvmtemplate.model.local.SocialFeedsDao
import com.example.mvvmtemplate.model.local.SocialFeedsLocalDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class DataBaseModule {

    fun provideSocialFeedsDao(dao: SocialFeedsDao ) = dao

    @Module
    companion object {
        @Provides
        @Singleton
        @JvmStatic
        fun provideSocialFeedsDao(context: Context): SocialFeedsDao {
            return Room.databaseBuilder(
                context,
                SocialFeedsLocalDatabase::class.java,
                "SocialFeedsLocalDatabase.db")
                .fallbackToDestructiveMigration()
                .build().socialFeedsDao()
        }
    }
}