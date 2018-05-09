package com.example.nimolee.youtubetesttask.ui.player

import android.arch.lifecycle.ViewModel
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.example.nimolee.youtubetesttask.data.Repository
import com.example.nimolee.youtubetesttask.data.entity.VideoEntity
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream
import java.lang.Exception

class PlayerViewModel : ViewModel() {
    private lateinit var repository: Repository

    fun init(context: Context) {
        repository = Repository(context)
    }


    fun saveVideoInfoToDatabase(videoId: String, videoName: String, videoDescription: String, videoURL: String) {
        val target = object : com.squareup.picasso.Target {
            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            }

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                var image: ByteArray? = null
                if (bitmap != null) {
                    val stream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                    image = stream.toByteArray()

                }
                val video = VideoEntity(id = videoId, name = videoName, description = videoDescription, url = videoURL, thumbnail = image)
                repository.insertVideo(video)
            }
        }
        Picasso.get().load(videoURL).into(target)
    }

}