package com.example.nimolee.youtubetesttask.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.example.nimolee.youtubetesttask.data.entity.VideoEntity
import com.example.nimolee.youtubetesttask.data.network.NetworkDataSource
import com.example.nimolee.youtubetesttask.tools.DbWorkerThread
import com.google.api.services.youtube.model.PlaylistItem
import com.google.api.services.youtube.model.SearchResult

class Repository(context: Context) {
    private var videoDataBase: VideoDataBase? = VideoDataBase.getInstance(context)
    private var dbWorkerThread: DbWorkerThread = DbWorkerThread("WorkerThread")
    private var networkDataSource: NetworkDataSource = NetworkDataSource(context)

    init {
        dbWorkerThread.start()
    }

    fun getAllVideo(): LiveData<List<VideoEntity>> {
        val resultLiveData = MutableLiveData<List<VideoEntity>>()
        val task = Runnable {
            val result = videoDataBase?.videoDao()?.getAllVideo()
            resultLiveData.postValue(result)
        }
        dbWorkerThread.postTask(task)
        return resultLiveData
    }

    fun insertVideo(videoEntity: VideoEntity) {
        val task = Runnable { videoDataBase?.videoDao()?.insertVideo(videoEntity) }
        dbWorkerThread.postTask(task)
    }

    fun getPlaylistInfoFromNetwork(playlistId: String): MutableLiveData<ArrayList<PlaylistItem>?> {
        val resultLiveData = MutableLiveData<ArrayList<PlaylistItem>?>()
        val task = Runnable {
            val result: ArrayList<PlaylistItem>? = networkDataSource.getPlaylistInfo(playlistId)
            resultLiveData.postValue(result)
        }
        dbWorkerThread.postTask(task)
        return resultLiveData
    }

    fun getChannelInfo(channelId: String): MutableLiveData<ArrayList<SearchResult>?> {
        val resultLiveData = MutableLiveData<ArrayList<SearchResult>?>()
        val task = Runnable {
            val result: ArrayList<SearchResult>? = networkDataSource.getChannelInfo(channelId)
            resultLiveData.postValue(result)
        }
        dbWorkerThread.postTask(task)
        return resultLiveData
    }

}