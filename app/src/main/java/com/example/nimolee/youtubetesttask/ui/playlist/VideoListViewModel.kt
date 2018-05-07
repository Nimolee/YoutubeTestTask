package com.example.nimolee.youtubetesttask.ui.playlist

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.example.nimolee.youtubetesttask.data.Repository
import com.google.api.services.youtube.model.PlaylistItem

class VideoListViewModel : ViewModel() {
    private lateinit var repo: Repository

    fun init(context: Context) {
        repo = Repository(context)
    }

    fun getVideoListInfo(playlistId: String): MutableLiveData<ArrayList<PlaylistItem>?> {
        return repo.getPlaylistInfoFromNetwork(playlistId)
    }
}