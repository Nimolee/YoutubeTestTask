package com.example.nimolee.youtubetesttask.ui.player

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import com.example.nimolee.youtubetesttask.R
import com.example.nimolee.youtubetesttask.constants.Constants
import com.example.nimolee.youtubetesttask.constants.Constants.Companion.INTENT_VIDEO_ID
import com.example.nimolee.youtubetesttask.constants.Constants.Companion.RECOVERY_DIALOG_REQUEST
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import kotlinx.android.synthetic.main.activity_player.*

class PlayerActivity : AppCompatActivity(), YouTubePlayer.OnInitializedListener {

    var videoId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        videoId = intent.extras.getString(INTENT_VIDEO_ID)
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
