package com.c653d0.musicplayer.util

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import com.c653d0.musicplayer.bean.SongBean

class PlayerUtil private constructor(){
    val TAG = "PlayerUtil"

    private lateinit var mediaPlayer:MediaPlayer
    var lastClickTime = 0L
    var isPlayed = false
    private var currentSong:SongBean = SongBean()

    fun playMusic(context: Context, song:SongBean){
        if(System.currentTimeMillis()-200 < lastClickTime){
            return
        }
        try{
            mediaPlayer.stop()
            mediaPlayer.release()
            isPlayed=false
        }catch (_:Exception){

        }
        mediaPlayer = MediaPlayer().apply {
            setDataSource(context, Uri.parse(song.path))
            currentSong = song
            prepareAsync()
            setOnPreparedListener {
                it.start()
                isPlayed=true
            }
        }
        lastClickTime = System.currentTimeMillis()
    }

    fun pauseMusic(){
        mediaPlayer.pause()
        isPlayed=false
    }

    fun stopMusic(){
        mediaPlayer.stop()
        isPlayed=false
    }

    fun continueMusic(){
        mediaPlayer.start()
        isPlayed=true
    }

    fun getCurrentSong():SongBean{
        return currentSong
    }


    companion object{
        private var instance:PlayerUtil?=null
            get(){
                if (field == null){
                    field = PlayerUtil()
                }
                return field
            }
        fun getObj():PlayerUtil{
            return instance!!
        }
    }
}