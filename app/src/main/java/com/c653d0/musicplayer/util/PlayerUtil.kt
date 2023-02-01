package com.c653d0.musicplayer.util

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri

class PlayerUtil private constructor(){
    private lateinit var mediaPlayer:MediaPlayer
    var lastClickTime = 0L
    var isPlayed = false
    val TAG = "PlayerUtil"

    fun playMusic(context: Context, path:String){
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
            setDataSource(context, Uri.parse(path))
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