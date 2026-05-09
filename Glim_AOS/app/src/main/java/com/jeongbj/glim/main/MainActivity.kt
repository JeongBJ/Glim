package com.jeongbj.glim.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.jeongbj.presentation.common.preview.Previews
import com.jeongbj.presentation.theme.GlimTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GlimTheme {
                MainScreen()
            }
        }
    }
}

@Previews
@Composable
fun GreetingPreview() {
    GlimTheme {
        MainScreen(
        )
    }
}