package com.example.nimolee.youtubetesttask.ui.playlist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.nimolee.youtubetesttask.R
import com.example.nimolee.youtubetesttask.constants.Constants.Companion.CHANNEL
import com.example.nimolee.youtubetesttask.constants.Constants.Companion.LOCAL_VIDEOS
import com.example.nimolee.youtubetesttask.constants.Constants.Companion.PLAYLIST_ID_KEY
import kotlinx.android.synthetic.main.fragment_videolistitem_list.*


class VideoListFragment : Fragment() {
    private var playlistId: String? = null
    private var channel: Boolean? = false


    var viewModel: VideoListViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(VideoListViewModel::class.java)
        viewModel?.init(activity!!.baseContext)
        playlistId = arguments?.getString(PLAYLIST_ID_KEY)
        channel = arguments?.getBoolean(CHANNEL)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_videolistitem_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (channel != null) {
            if (channel as Boolean) {
                updateChannel()
            } else {
                update()
            }
        } else {
            showLocalVideos()
        }
        swipe_to_refresh.setOnRefreshListener {
            if (channel != null) {
                if (channel as Boolean) {
                    updateChannel()
                } else {
                    update()
                }
            } else {
                showLocalVideos()
            }
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
                                i.snippet.description,
                                null))
                    }
                    list.layoutManager = LinearLayoutManager(activity?.baseContext)
                    list.adapter = VideoListRecyclerAdapter(items, false)
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
                    val bitmap = BitmapFactory.decodeByteArray(i.thumbnail, 0, i.thumbnail?.size!!)
                    items.add(VideoInfo(
                            i.id,
                            i.url,
                            i.name,
                            i.description,
                            bitmap
                    ))
                }
                list.layoutManager = LinearLayoutManager(activity?.baseContext)
                list.adapter = VideoListRecyclerAdapter(items, true)
            } else {
                Toast.makeText(this.context, "Empty database.", Toast.LENGTH_LONG).show()
                //TODO:Show empty database placeholder
            }
            swipe_to_refresh.isRefreshing = false
        })

    }

    fun updateChannel() {
        swipe_to_refresh.isRefreshing = true
        val data = viewModel?.getChannelListInfo(playlistId!!)
        data?.observe(this, Observer {
            if (it != null) {
                val items = ArrayList<VideoInfo>()
                for (i in it) {
                        items.add(VideoInfo(
                                i.id.videoId,
                                i.snippet?.thumbnails?.high?.url,
                                i.snippet.title,
                                i.snippet.description,
                                null))
                }
                list.layoutManager = LinearLayoutManager(activity?.baseContext)
                list.adapter = VideoListRecyclerAdapter(items, false)
            } else {
                Toast.makeText(this.context, "Network error.", Toast.LENGTH_LONG).show()
                showLocalVideos()
                //TODO:Show network error placeholder
            }
            swipe_to_refresh.isRefreshing = false
        })
    }

}
