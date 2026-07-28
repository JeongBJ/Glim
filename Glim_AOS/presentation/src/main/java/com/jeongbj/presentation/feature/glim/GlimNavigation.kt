package com.jeongbj.presentation.feature.glim

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.jeongbj.presentation.feature.glim.viewmodel.GlimViewModel
import kotlinx.serialization.Serializable

@Serializable
data class GlimRoute(
    val quoteSeq: Long? = null
)

fun NavGraphBuilder.glimNav(
    navigateToBookDetail: (String) -> Unit,
    navigateToInfo: (Long) -> Unit
) {
    composable<GlimRoute>(
        deepLinks = listOf(
            navDeepLink {
                uriPattern = "https://jeongbj.kro.kr/glim/share/{quoteSeq}"
            }
        )
    ){
        val viewModel: GlimViewModel = hiltViewModel()
        GlimScreen(
            viewModel = viewModel,
            navigateToBookDetail = { navigateToBookDetail(it) },
            navigateToInfo = navigateToInfo
        )
    }
}