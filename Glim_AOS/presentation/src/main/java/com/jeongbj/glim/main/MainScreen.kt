package com.jeongbj.glim.main

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.jeongbj.glim.navigation.AppNavGraph

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    AppNavGraph(navController)
}