package com.example.nimolee.youtubetesttask.ui.playlist

import android.arch.lifecycle.ViewModel
import android.content.Context
import com.example.nimolee.youtubetesttask.data.Repository

class VideoListViewModel : ViewModel() {
    lateinit var repo: Repository

    fun init(context: Context) {
        repo = Repository(context)
    }


}