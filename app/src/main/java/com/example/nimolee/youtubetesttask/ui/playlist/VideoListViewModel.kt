package com.example.nimolee.youtubetesttask.ui.playlist

import android.content.Context
import com.example.nimolee.youtubetesttask.data.Repository

class VideoListViewModel(context: Context) {
    lateinit var repo: Repository

    init {
        repo = Repository(context)
    }
}