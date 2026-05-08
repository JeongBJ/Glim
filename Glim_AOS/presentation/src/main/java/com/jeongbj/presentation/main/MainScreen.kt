package com.jeongbj.presentation.main

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.jeongbj.presentation.navigation.AppNavGraph

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    AppNavGraph(navController)
}