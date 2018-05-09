package com.example.nimolee.youtubetesttask.ui

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.nimolee.youtubetesttask.R
import com.example.nimolee.youtubetesttask.constants.Constants.Companion.CHANNEL
import com.example.nimolee.youtubetesttask.constants.Constants.Companion.FRAGMENT
import com.example.nimolee.youtubetesttask.constants.Constants.Companion.PLAYLIST_ID_KEY
import com.example.nimolee.youtubetesttask.constants.Constants.Companion.SAVE_OPEN_DRAVER
import com.example.nimolee.youtubetesttask.ui.playlist.VideoListFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        drawer_layout.openDrawer(GravityCompat.START)
        if (savedInstanceState != null) {
            changeFragmentInActivity(supportFragmentManager.getFragment(savedInstanceState, FRAGMENT))
            if (savedInstanceState.getBoolean(SAVE_OPEN_DRAVER)) {
                drawer_layout.openDrawer(GravityCompat.START)
            } else {
                drawer_layout.closeDrawer(GravityCompat.START)
            }
        }
    }

    override fun onBackPressed() {
        if (!drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.openDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val argument = Bundle()
        when (item.itemId) {
            R.id.playlist1 -> {
                argument.putBoolean(CHANNEL, false)
                argument.putString(PLAYLIST_ID_KEY, "PLMC9KNkIncKtPzgY-5rmhvj7fax8fdxoj")
            }
            R.id.channel1 -> {
                argument.putBoolean(CHANNEL, true)
                argument.putString(PLAYLIST_ID_KEY, "UCMOgdURr7d8pOVlc-alkfRg")
            }
            R.id.localVideos -> {
                argument.putString(PLAYLIST_ID_KEY, "")
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

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        supportFragmentManager.putFragment(outState, FRAGMENT, supportFragmentManager.fragments?.get(0))
        outState?.putBoolean(SAVE_OPEN_DRAVER, drawer_layout.isDrawerOpen(GravityCompat.START))
    }
}
