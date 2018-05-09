package com.example.nimolee.youtubetesttask.ui

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.nimolee.youtubetesttask.R
import com.example.nimolee.youtubetesttask.constants.Constants.Companion.PLAYLIST_ID_KEY
import com.example.nimolee.youtubetesttask.ui.playlist.VideoListFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    companion object {
        val playlist = arrayOf(
                "PL4C2OaC1jQqR3ICDBf4j1dH1Fk4uIo-Lx",
                "PLUk42fEC0DsTELmZi-QQngwzEa-8lM2_M",
                "PLMC9KNkIncKtPzgY-5rmhvj7fax8fdxoj")
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
        arguments.putString(PLAYLIST_ID_KEY, playlist[0])
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
                argument.putString(PLAYLIST_ID_KEY, playlist[0])
            }
            R.id.playlist2 -> {
                argument.putString(PLAYLIST_ID_KEY, playlist[1])
            }
            R.id.playlist3 -> {
                argument.putString(PLAYLIST_ID_KEY, playlist[2])
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
