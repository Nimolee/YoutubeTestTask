package com.example.nimolee.youtubetesttask.ui.playlist

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nimolee.youtubetesttask.R

class VideoListFragment : Fragment() {

    companion object {
        const val playlistId = "PLAYLIST_ID"
    }

    var viewModel: VideoListViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val playlistId = arguments?.getString(playlistId)
        viewModel = ViewModelProviders.of(this).get(VideoListViewModel::class.java)
        viewModel?.init(activity!!.baseContext)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_videolistitem_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}
