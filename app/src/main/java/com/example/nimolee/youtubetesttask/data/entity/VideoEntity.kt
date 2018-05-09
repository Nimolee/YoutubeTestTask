package com.example.nimolee.youtubetesttask.data.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "video")
data class VideoEntity(@PrimaryKey(autoGenerate = false) val id: String,
                       @ColumnInfo(name = "video_name") val name: String,
                       @ColumnInfo(name = "video_description") val description: String,
                       @ColumnInfo(name = "video_URL") val url: String,
                       @ColumnInfo(typeAffinity = ColumnInfo.BLOB) val thumbnail: ByteArray?) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as VideoEntity

        if (id != other.id) return false
        if (name != other.name) return false
        if (description != other.description) return false
        if (url != other.url) return false
        if (!Arrays.equals(thumbnail, other.thumbnail)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + url.hashCode()
        result = 31 * result + (thumbnail?.let { Arrays.hashCode(it) } ?: 0)
        return result
    }
}