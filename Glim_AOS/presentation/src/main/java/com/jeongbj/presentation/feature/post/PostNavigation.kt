package com.jeongbj.presentation.feature.post

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jeongbj.presentation.feature.post.viewmodel.PostViewModel
import kotlinx.serialization.Serializable

@Serializable
data object PostRoute

fun NavGraphBuilder.postNav(

) {
    composable<PostRoute> {
        val viewModel: PostViewModel = hiltViewModel()
        PostScreen(
            viewModel = viewModel
        )
    }
}