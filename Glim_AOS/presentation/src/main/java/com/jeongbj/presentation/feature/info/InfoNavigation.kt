package com.jeongbj.presentation.feature.info

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jeongbj.presentation.common.model.UserUI
import com.jeongbj.presentation.feature.info.viewmodel.InfoViewModel
import kotlinx.serialization.Serializable


@Serializable
data class InfoRoute(val userSeq: Long? = null)

fun NavGraphBuilder.infoNav(
    navigateToProfile: (UserUI?) -> Unit,
    navigateToQuoteDetail: (Long) -> Unit,
    navigateToSettings: () -> Unit,
) {
    composable<InfoRoute> {
        val viewModel: InfoViewModel = hiltViewModel()
        InfoScreen(
            viewModel = viewModel,
            navigateToProfile = { navigateToProfile(it) },
            navigateToQuoteDetail = { navigateToQuoteDetail(it) },
            navigateToSettings = navigateToSettings
        )
    }
}