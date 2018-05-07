package com.example.nimolee.youtubetesttask.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.nimolee.youtubetesttask.data.dao.VideoDao
import com.example.nimolee.youtubetesttask.data.entity.VideoEntity

@Database(entities = [VideoEntity::class], version = 1)
abstract class VideoDataBase : RoomDatabase() {
    abstract fun videoDao(): VideoDao

    companion object {
        private var INSTANCE: VideoDataBase? = null

        fun getInstance(context: Context): VideoDataBase? {
            if (INSTANCE == null) {
                synchronized(VideoDataBase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            VideoDataBase::class.java,
                            "video.db").build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}