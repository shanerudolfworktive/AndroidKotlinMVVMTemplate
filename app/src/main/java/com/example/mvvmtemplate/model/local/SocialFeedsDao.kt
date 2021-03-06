package com.example.mvvmtemplate.model.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.example.mvvmtemplate.model.SocialFeedModel


@Dao
interface SocialFeedsDao {

    @Query("select * from SocialFeedsTable order by id ASC")
    fun getSocialFeeds(): LiveData<List<SocialFeedModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSocialFeeds(socialFeeds: List<SocialFeedModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSocialFeed(socialFeed: SocialFeedModel)

    @Delete
    fun deleteSocialFeed(socialFeed: SocialFeedModel)

    @Delete
    fun deleteSocialFeeds(vararg socialFeeds: SocialFeedModel)

    @Delete
    fun deleteSocialFeeds(socialFeeds: List<SocialFeedModel>)

    @Query("delete from SocialFeedsTable")
    fun deleteAllSocialFeeds()

    @Query("select * from SocialFeedsTable limit 1")
    fun first(): SocialFeedModel?

}