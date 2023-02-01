package com.c653d0.musicplayer.util

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import android.util.Log
import com.c653d0.musicplayer.bean.SongBean

class MusicUtils {
    private val TAG = "MusicUtils"

    fun getAllSongs(context:Context):List<SongBean>{
        val cursor = context.contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            null,null, null,MediaStore.Audio.Media.IS_MUSIC)
        val result = ArrayList<SongBean>()

        cursor?.apply {
            while (this.moveToNext()){
                try{
                    val song = SongBean(
                        name = this.getString(this.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)),
                        artist = this.getString(this.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)),
                        time = this.getString(this.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)),
                        size = this.getString(this.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)),
                        album = this.getString(this.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)),
                        path = this.getString(this.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA))
                    )
                    result.add(song)
                }catch (e:NullPointerException){
                    Log.e(TAG, "getAllSongs: ", e)
                }
            }
        }
        cursor?.close()
        return result
    }
}