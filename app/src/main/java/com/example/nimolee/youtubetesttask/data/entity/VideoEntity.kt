package com.example.nimolee.youtubetesttask.data.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "video")
data class VideoEntity(@PrimaryKey(autoGenerate = false) val id: String,
                       @ColumnInfo(name = "video_name") val name: String,
                       @ColumnInfo(name = "video_description") val description: String,
                       @ColumnInfo(name = "video_URL") val url: String,
                       @ColumnInfo(typeAffinity = ColumnInfo.BLOB) val thumbnail: ByteArray)