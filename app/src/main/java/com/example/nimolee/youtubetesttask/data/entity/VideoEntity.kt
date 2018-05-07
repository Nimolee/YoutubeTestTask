package com.example.nimolee.youtubetesttask.data.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.media.Image

@Entity(tableName = "video")
data class VideoEntity(@PrimaryKey(autoGenerate = true) val id: Int,
                       @ColumnInfo(name = "video_name") val name: String,
                       @ColumnInfo(name = "video_description") val description: String,
                       @ColumnInfo(typeAffinity = ColumnInfo.BLOB) val thumbnail: ByteArray)