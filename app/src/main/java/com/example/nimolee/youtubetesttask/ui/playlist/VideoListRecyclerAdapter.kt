package com.example.nimolee.youtubetesttask.ui.playlist


import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.nimolee.youtubetesttask.R
import com.example.nimolee.youtubetesttask.tools.inflate

class VideoListRecyclerAdapter(private val videos: List<VideoInfo>)
    : RecyclerView.Adapter<VideoListRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = parent.inflate(R.layout.fragment_videolistitem)
        return ViewHolder(viewHolder)
    }

    override fun getItemCount() = videos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.thumbnailImage?.setImageBitmap(videos[position].Image)
        holder.nameText?.text = videos[position].name
        holder.descriptionText?.text = videos[position].description
    }


    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        var thumbnailImage: ImageView? = null
        var nameText: TextView? = null
        var descriptionText: TextView? = null

        init {
            thumbnailImage = mView.findViewById(R.id.thumbnail)
            nameText = mView.findViewById(R.id.video_name)
            descriptionText = mView.findViewById(R.id.video_description)
            mView.setOnClickListener {
                TODO("Write open player")
            }
        }
    }
}
