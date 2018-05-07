package com.example.nimolee.youtubetesttask.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.example.nimolee.youtubetesttask.data.entity.VideoEntity
import com.example.nimolee.youtubetesttask.tools.DbWorkerThread

class Repository(context: Context) {
    private var videoDataBase: VideoDataBase? = VideoDataBase.getInstance(context)
    private var dbWorkerThread: DbWorkerThread = DbWorkerThread("WorkerThread")

    init {
        dbWorkerThread.start()
    }

    fun getAllVideo(): LiveData<Array<VideoEntity>> {
        val resultLiveData = MutableLiveData<Array<VideoEntity>>()
        val task = Runnable {
            val result = videoDataBase?.videoDao()?.getAllVideo()
            resultLiveData.postValue(result)
        }
        dbWorkerThread.postTask(task)
        return resultLiveData
    }

    fun getVideo(videoId: Int): LiveData<VideoEntity> {
        val resultLiveData = MutableLiveData<VideoEntity>()
        val task = Runnable {
            val result = videoDataBase?.videoDao()?.getVideo(videoId)
            resultLiveData.postValue(result)
        }
        dbWorkerThread.postTask(task)
        return resultLiveData
    }

    fun insertVideo(videoEntity: VideoEntity) {
        val task = Runnable { videoDataBase?.videoDao()?.insertVideo(videoEntity) }
        dbWorkerThread.postTask(task)
    }

    fun insertVideos(videos: ArrayList<VideoEntity>) {
        val task = Runnable {
            for (i in videos) {
                videoDataBase?.videoDao()?.insertVideo(i)
            }
        }
        dbWorkerThread.postTask(task)
    }

    fun removeVideo(videoEntity: VideoEntity) {
        val task = Runnable { videoDataBase?.videoDao()?.removeVideo(videoEntity) }
        dbWorkerThread.postTask(task)
    }

    fun removeVideos(videos: ArrayList<VideoEntity>) {
        val task = Runnable { videoDataBase?.videoDao()?.removeVideos(videos) }
        dbWorkerThread.postTask(task)
    }

    fun clearTable() {
        val task = Runnable { videoDataBase?.videoDao()?.clearTable() }
        dbWorkerThread.postTask(task)
    }
}