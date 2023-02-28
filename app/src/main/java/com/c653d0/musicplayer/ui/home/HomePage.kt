package com.c653d0.musicplayer.ui.home

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.c653d0.musicplayer.bean.SongBean
import com.c653d0.musicplayer.ui.theme.*
import com.c653d0.musicplayer.util.PlayerUtil

    private val player: PlayerUtil = PlayerUtil.getObj()

    @Composable
    fun showHomePage(context: Context,songList: List<SongBean>){
        val isPlay = remember { mutableStateOf(player.isPlayed) }
        showSongList(songList = songList, context = context, isPlay)
        toolBar(context = context, isPlay)
    }

    @Composable
    fun showSongList(songList:List<SongBean>, context: Context, isPlay: MutableState<Boolean>) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)){
            items(songList){song->
                itemSong(song = song, context,isPlay)
            }
            item { 
                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }

    @Composable
    private fun itemSong(song: SongBean, context: Context,isPlay: MutableState<Boolean>){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 1.dp)
                .clip(CircleShape)
                .background(ListBG)
                .clickable {
                    player.playMusic(context, song)
                    isPlay.value = true
                }
        ){
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(all = 8.dp)
            ) {
                Text(
                    song.name,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(all = 2.dp),
                    maxLines = 1
                )
                Text(
                    song.artist,
                    fontSize = 10.sp,
                    modifier = Modifier.padding(all = 2.dp),
                    maxLines = 1
                )
            }
            Spacer(modifier = Modifier
                .width(1.dp)
                .weight(0.1f))
            Box(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(0.2f)
            ) {
                Text(formatTime(song.time))
            }
        }
    }


    @Composable
    fun toolBar(context:Context, isPlay:MutableState<Boolean>){

        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .height(20.dp)
                .fillMaxWidth()
        ){
            Box(modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Bottom)
                .background(ToolbarBG)
                .clickable { }
            ) {

                BasicText(
                    modifier = Modifier.align(Alignment.BottomStart),
                    text = player.getCurrentSong().name
                )

                IconButton(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    onClick = {
                        if (isPlay.value){
                            player.pauseMusic()

                            isPlay.value=false
                            player.isPlayed = false
                        }else{
                            player.continueMusic()

                            isPlay.value=true
                            player.isPlayed=true
                        }
                    }
                ) {
                    if(isPlay.value){
                        Icon(painter = painterResource(id = com.c653d0.musicplayer.R.drawable.ic_baseline_pause_24), contentDescription = "暂停")
                    }else{
                        Icon(painter = painterResource(id = com.c653d0.musicplayer.R.drawable.ic_baseline_play_arrow_24),contentDescription = "播放")
                    }
                }
            }
        }
    }

    private fun formatTime(time:String):String{
        val s = time.toLong()/1000
        val mm = s/60
        val ss = s-mm*60

        return "${mm}:${if(ss<10) "0" else ""}${ss}"
    }