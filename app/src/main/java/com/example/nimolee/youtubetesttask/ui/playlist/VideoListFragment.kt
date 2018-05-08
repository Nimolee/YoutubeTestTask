package com.example.nimolee.youtubetesttask.ui.playlist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nimolee.youtubetesttask.R
import kotlinx.android.synthetic.main.fragment_videolistitem_list.*

class VideoListFragment : Fragment() {
    private var playlistId: String? = null

    companion object {
        const val playlistIdKey = "PLAYLIST_ID"
    }

    var viewModel: VideoListViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(VideoListViewModel::class.java)
        viewModel?.init(activity!!.baseContext)
        playlistId = arguments?.getString(playlistIdKey)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_videolistitem_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        update()
        swipe_to_refresh.setOnRefreshListener {
            update()
        }
    }

    private fun update() {
        swipe_to_refresh.isRefreshing = true
        if (playlistId != null) {
            val data = viewModel?.getVideoListInfo(playlistId!!)
            data?.observe(this, Observer {
                if (it != null) {
                    val items = ArrayList<VideoInfo>()
                    for (i in it) {
                        items.add(VideoInfo(
                                i.contentDetails.videoId,
                                i.snippet?.thumbnails?.standard?.url,
                                i.snippet.title,
                                i.snippet.description))
                    }
                    list.layoutManager = LinearLayoutManager(activity?.baseContext)
                    list.adapter = VideoListRecyclerAdapter(items)
                } else {
                    //TODO:Show network error placeholder
                }
                swipe_to_refresh.isRefreshing = false
            })
        }
    }


}
