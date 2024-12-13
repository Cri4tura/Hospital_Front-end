package com.example.hospital_front_end.ui.screens.splash_screen

import android.content.Context
import android.net.Uri
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import com.example.hospital_front_end.R
import com.example.hospital_front_end.ui.components.IconVideoPlayer
import com.example.hospital_front_end.utils.Constants
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(key1 = true) {
        delay(2000)
        navController.popBackStack()
        navController.navigate(Constants.Screen.Login.route)
    }
    Splash()
}

@Composable
fun Splash() {
    val context = LocalContext.current
    Box( // Usar Box para el video en pantalla completa
        modifier = Modifier
            .fillMaxSize() // Ocupa toda la pantalla
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            VideoScreenSlpash(
                context = context,
                videoResId = R.raw.splash_video_final,
                modifier = Modifier
                    .fillMaxHeight()
            )
        }
    }
}


@OptIn(UnstableApi::class)
@Composable
fun VideoScreenSlpash(
    context: Context,
    videoResId: Int,
    modifier: Modifier = Modifier,
) {

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val videoUri = Uri.parse("android.resource://${context.packageName}/$videoResId")
            val mediaItem = MediaItem.fromUri(videoUri)
            setMediaItem(mediaItem)
            repeatMode = ExoPlayer.REPEAT_MODE_ALL
            prepare()
            playWhenReady = true
        }
    }

    DisposableEffect(exoPlayer) {
        onDispose {
            exoPlayer.release()
        }
    }

    AndroidView(
        factory = {
            PlayerView(it).apply {
                player = exoPlayer
                useController = false // Ocultar controles
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM // Forzar zoom para llenar el contenedor
            }
        },
        modifier = modifier
    )
}


@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SplashScreenPreview() {
    Splash()
}