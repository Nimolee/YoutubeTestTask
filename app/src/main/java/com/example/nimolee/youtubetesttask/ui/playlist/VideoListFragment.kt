package com.example.nimolee.youtubetesttask.ui.playlist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.nimolee.youtubetesttask.R
import com.example.nimolee.youtubetesttask.constants.Constants
import com.example.nimolee.youtubetesttask.constants.Constants.Companion.LOCAL_VIDEOS
import kotlinx.android.synthetic.main.fragment_videolistitem_list.*

class VideoListFragment : Fragment() {
    private var playlistId: String? = null


    var viewModel: VideoListViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(VideoListViewModel::class.java)
        viewModel?.init(activity!!.baseContext)
        playlistId = arguments?.getString(Constants.PLAYLIST_ID_KEY)
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
        if (playlistId != null && !playlistId.equals(LOCAL_VIDEOS)) {
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
                    Toast.makeText(this.context, "Network error.", Toast.LENGTH_LONG).show()
                    showLocalVideos()
                    //TODO:Show network error placeholder
                }
                swipe_to_refresh.isRefreshing = false
            })
        } else {
            showLocalVideos()
        }
    }

    private fun showLocalVideos() {
        swipe_to_refresh.isRefreshing = true
        val data = viewModel?.getLocalVideoList()
        data?.observe(this, Observer {
            if (it != null) {
                val items = ArrayList<VideoInfo>()
                for (i in it) {
                    items.add(VideoInfo(
                            i.id,
                            i.url,
                            i.name,
                            i.description))
                }
                list.layoutManager = LinearLayoutManager(activity?.baseContext)
                list.adapter = VideoListRecyclerAdapter(items)
            } else {
                Toast.makeText(this.context, "Empty database.", Toast.LENGTH_LONG).show()
                //TODO:Show empty database placeholder
            }
            swipe_to_refresh.isRefreshing = false
        })

    }

}
