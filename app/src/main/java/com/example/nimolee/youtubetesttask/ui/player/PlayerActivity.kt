package com.example.nimolee.youtubetesttask.ui.player

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import com.example.nimolee.youtubetesttask.R
import com.example.nimolee.youtubetesttask.constants.Constants
import com.example.nimolee.youtubetesttask.constants.Constants.Companion.INTENT_VIDEO_DESCRIPTION
import com.example.nimolee.youtubetesttask.constants.Constants.Companion.INTENT_VIDEO_ID
import com.example.nimolee.youtubetesttask.constants.Constants.Companion.INTENT_VIDEO_NAME
import com.example.nimolee.youtubetesttask.constants.Constants.Companion.RECOVERY_DIALOG_REQUEST
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import kotlinx.android.synthetic.main.activity_player.*
import kotlinx.android.synthetic.main.content_player.*

class PlayerActivity : AppCompatActivity(), YouTubePlayer.OnInitializedListener {

    private var videoId: String? = null
    private var videoName: String? = null
    private var videoDescription: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        videoId = intent.extras[INTENT_VIDEO_ID] as String
        videoName = intent.extras[INTENT_VIDEO_NAME] as String
        videoDescription = intent.extras[INTENT_VIDEO_DESCRIPTION] as String
        supportActionBar?.title = videoName
        video_name.text = videoName
        video_description.text = videoDescription
        val fragment = YouTubePlayerSupportFragment()
        fragment.initialize(Constants.API_KEY, this)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.player, fragment)
        fragmentTransaction.commit()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                this.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onInitializationSuccess(p0: YouTubePlayer.Provider?, p1: YouTubePlayer?, p2: Boolean) {
        if (!p2) {
            p1?.cueVideo(videoId)
        }
    }

    override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {
        if (p1?.isUserRecoverableError!!) {
            p1.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show()
        } else {
            Toast.makeText(this, "Can`t initialize player.", Toast.LENGTH_SHORT).show()
            this.finish()
        }
    }
}
