package com.c653d0.musicplayer

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.ColorUtils
import com.c653d0.musicplayer.bean.SongBean
import com.c653d0.musicplayer.ui.home.HomePage
import com.c653d0.musicplayer.ui.theme.*
import com.c653d0.musicplayer.util.MusicUtils
import com.c653d0.musicplayer.util.PlayerUtil
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState


class MainActivity : ComponentActivity() {
    private val mPlayer = PlayerUtil.getObj()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicPlayerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BackGround
                ) {
                    //Greeting("Android", context = applicationContext)
                    if(PermissionPageContent()){
                        val musicUtils = MusicUtils()
                        HomePage().showHomePage(songList = musicUtils.getAllSongs(applicationContext), context = applicationContext)
                    }
                }
            }
        }
    }
}



@Preview
@Composable
fun preview(){
    //itemSong(song = SongBean(name="name", artist = "artist", time="${60*4}", size = "${1024*5*8}", album = "album",path = "/abc/efg"))
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionPageContent() : Boolean{
    //当前需要申请的权限
    val readStoragePermissionState = rememberPermissionState(
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    )

    when (readStoragePermissionState.status) {
        PermissionStatus.Granted -> {//已授权
            return true
        }
        is PermissionStatus.Denied -> {
            Column {
                val textToShow = if ((readStoragePermissionState.status as PermissionStatus.Denied).shouldShowRationale) {
                    //如果用户拒绝了该权限但可以显示理由，那么请温和地解释为什么应用程序需要此权限(拒绝权限)
                    "如拒绝权限将无法扫描本地歌曲，也将无法使用本应用"
                } else {
                    //如果这是用户第一次登陆此功能，或者用户不想再次被要求获得此权限，请说明该权限是必需的(用户选择拒绝且不再询问)
                    "本应用需要读取外部存储权限用于扫描本地歌曲"
                }

                Text(textToShow)
                Button(onClick = {
                    //申请权限
                    readStoragePermissionState.launchPermissionRequest()
                }) {
                    Text("申请权限")
                }
            }
        }
    }
    return false
}

