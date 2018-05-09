package com.example.nimolee.youtubetesttask.data.network

import android.content.Context
import com.example.nimolee.youtubetesttask.R
import com.example.nimolee.youtubetesttask.constants.Constants.Companion.API_KEY
import com.google.api.client.http.HttpRequestInitializer
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.PlaylistItem

class NetworkDataSource(context: Context) {
    private val youTube: YouTube = YouTube.Builder(NetHttpTransport(), JacksonFactory(), HttpRequestInitializer {
    }).setApplicationName(context.getString(R.string.app_name)).build()


    fun getPlaylistInfo(playlistId: String): ArrayList<PlaylistItem>? {
        val result: ArrayList<PlaylistItem> = ArrayList()
        try {
            val list = youTube.playlistItems().list("snippet,contentDetails")
            list.playlistId = playlistId
            list.fields = "items(contentDetails/videoId,snippet/title,snippet/description,snippet/thumbnails/standard/url)"
            list.key = API_KEY
            list.maxResults = 50
            var nextToken: String? = ""
            do {
                list.pageToken = nextToken
                val playlistResult = list.execute()
                result.addAll(playlistResult.items)
                nextToken = playlistResult.nextPageToken
            } while (nextToken != null)
            return result
        } catch (e: Exception) {
            return null
        }
    }
}