package com.example.nimolee.youtubetesttask.ui.playlist


import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.nimolee.youtubetesttask.R
import com.example.nimolee.youtubetesttask.tools.inflate
import com.example.nimolee.youtubetesttask.tools.load
import com.example.nimolee.youtubetesttask.ui.player.PlayerActivity

class VideoListRecyclerAdapter(private val videos: List<VideoInfo>)
    : RecyclerView.Adapter<VideoListRecyclerAdapter.ViewHolder>() {
    private val INTENT_VIDEO_ID = "INTENT_VIDEO_ID"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = parent.inflate(R.layout.fragment_videolistitem)
        return ViewHolder(viewHolder)
    }

    override fun getItemCount() = videos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(videos[position])
    }


    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        private var thumbnailImage: ImageView? = null
        private var nameText: TextView? = null
        private var descriptionText: TextView? = null

        init {
            thumbnailImage = mView.findViewById(R.id.thumbnail)
            nameText = mView.findViewById(R.id.video_name)
            descriptionText = mView.findViewById(R.id.video_description)
        }

        fun bind(item: VideoInfo) {
            if (item.Image != null) {
                thumbnailImage?.load(item.Image!!)
            }
            nameText?.text = item.name
            descriptionText?.text = item.description
            mView.setOnClickListener {
                val context = itemView.context
                val openNewPlayer = Intent(context, PlayerActivity::class.java)
                openNewPlayer.putExtra(INTENT_VIDEO_ID, item.Id)
                context.startActivity(openNewPlayer)
            }
        }
    }

}
