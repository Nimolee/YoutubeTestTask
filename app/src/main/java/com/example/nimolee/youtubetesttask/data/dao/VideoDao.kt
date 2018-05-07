package com.example.nimolee.youtubetesttask.data.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.example.nimolee.youtubetesttask.data.entity.VideoEntity

@Dao
interface VideoDao {
    @Query("select * from video")
    fun getAllVideo(): Array<VideoEntity>?

    @Query("select * from video where id = :videoId")
    fun getVideo(videoId: Int): VideoEntity?

    @Insert(onConflict = REPLACE)
    fun insertVideo(videoEntity: VideoEntity)

    @Delete
    fun removeVideo(videoEntity: VideoEntity)

    @Delete
    fun removeVideos(videos: ArrayList<VideoEntity>): Int

    @Query("delete from video")
    fun clearTable()
}