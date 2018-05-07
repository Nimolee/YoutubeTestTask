package com.example.nimolee.youtubetesttask.ui

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.nimolee.youtubetesttask.R
import com.example.nimolee.youtubetesttask.ui.playlist.VideoListFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    companion object {
        val playlist = arrayOf("", "", "")//TODO:Write youtube playlist ID
        const val playlistId = "PLAYLIST_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val arguments = Bundle()
        arguments.putString(playlistId, playlist[0])
        val fragment = VideoListFragment()
        fragment.arguments = arguments
        fragmentTransaction.add(R.id.fragment_view, fragment)
        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val argument = Bundle()
        when (item.itemId) {
            R.id.playlist1 -> {
                argument.putString(playlistId, playlist[0])
            }
            R.id.playlist2 -> {
                argument.putString(playlistId, playlist[1])
            }
            R.id.playlist3 -> {
                argument.putString(playlistId, playlist[2])
            }
        }
        val fragment = VideoListFragment()
        fragment.arguments = argument
        changeFragmentInActivity(fragment)
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun changeFragmentInActivity(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_view, fragment)
        fragmentTransaction.commit()
    }
}
