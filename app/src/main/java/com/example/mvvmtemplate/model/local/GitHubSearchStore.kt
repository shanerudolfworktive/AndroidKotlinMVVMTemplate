package com.example.mvvmtemplate.model.local

import android.content.Context
import androidx.paging.DataSource
import androidx.room.*
import com.example.mvvmtemplate.MainApplication
import com.example.mvvmtemplate.model.GitHubRepoModel
import com.example.mvvmtemplate.model.SocialFeedModel

@Dao
interface GitHubSearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts: List<GitHubRepoModel>)

    // Do a similar query as the search API:
    // Look for repos that contain the query string in the name or in the description
    // and order those results descending, by the number of stars and then by name
    @Query("SELECT * FROM GitHubRepoTable")
    fun reposByName(): DataSource.Factory<Int,GitHubRepoModel>

    @Query("select * from GitHubRepoTable limit 1")
    fun first(): GitHubRepoModel

    @Query("delete from GitHubRepoTable")
    fun deleteAllSearches()
}

@Database(entities = [GitHubRepoModel::class], version = 1)
abstract class GitHubSearchDatabase : RoomDatabase() {

    abstract fun gitHubSearchDao(): GitHubSearchDao

    companion object {

        @Volatile
        private var INSTANCE: GitHubSearchDatabase ? = null

        fun getInstance(): GitHubSearchDatabase  =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(MainApplication.appContext).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                GitHubSearchDatabase::class.java, "Github.db")
                .fallbackToDestructiveMigration()
                .build()
    }
}