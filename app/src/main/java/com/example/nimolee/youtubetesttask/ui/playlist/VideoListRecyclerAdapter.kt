package com.example.nimolee.youtubetesttask.ui.playlist


import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.nimolee.youtubetesttask.R
import com.example.nimolee.youtubetesttask.constants.Constants.Companion.INTENT_IMAGE_URL
import com.example.nimolee.youtubetesttask.constants.Constants.Companion.INTENT_VIDEO_DESCRIPTION
import com.example.nimolee.youtubetesttask.constants.Constants.Companion.INTENT_VIDEO_ID
import com.example.nimolee.youtubetesttask.constants.Constants.Companion.INTENT_VIDEO_NAME
import com.example.nimolee.youtubetesttask.constants.Constants.Companion.INTENT_VIDEO_THUMBNAILS
import com.example.nimolee.youtubetesttask.tools.inflate
import com.example.nimolee.youtubetesttask.tools.load
import com.example.nimolee.youtubetesttask.ui.player.PlayerActivity

class VideoListRecyclerAdapter(private val videos: List<VideoInfo>, private val local: Boolean)
    : RecyclerView.Adapter<VideoListRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = parent.inflate(R.layout.fragment_videolistitem)
        return ViewHolder(viewHolder)
    }

    override fun getItemCount() = videos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(videos[position])
    }


    inner class ViewHolder(private val mView: View) : RecyclerView.ViewHolder(mView) {
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
                if (local) {
                    thumbnailImage?.setImageBitmap(item.bitmap)
                } else {
                    thumbnailImage?.load(item.Image)
                }
            }
            nameText?.text = item.name
            descriptionText?.text = item.description
            mView.setOnClickListener {
                val context = itemView.context
                val openNewPlayer = Intent(context, PlayerActivity::class.java)
                openNewPlayer.putExtra(INTENT_VIDEO_ID, item.Id)
                openNewPlayer.putExtra(INTENT_VIDEO_NAME, item.name)
                openNewPlayer.putExtra(INTENT_VIDEO_DESCRIPTION, item.description)
                openNewPlayer.putExtra(INTENT_VIDEO_THUMBNAILS, item.Image)
                openNewPlayer.putExtra(INTENT_IMAGE_URL, item.Image)
                context.startActivity(openNewPlayer)
            }
        }
    }

}
