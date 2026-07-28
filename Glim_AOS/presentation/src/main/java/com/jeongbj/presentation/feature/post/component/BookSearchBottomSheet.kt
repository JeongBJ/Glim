package com.jeongbj.presentation.feature.post.component

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jeongbj.domain.book.model.Book
import com.jeongbj.presentation.feature.book.search.SearchScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookSearchBottomSheet(
    onDismiss: () -> Unit,
    onBookSelected: (Book) -> Unit,
) {
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        contentColor = Color.Black,
        sheetState = bottomSheetState,
        tonalElevation = 8.dp,
        shape = MaterialTheme.shapes.large,
        contentWindowInsets = { WindowInsets(0.dp, 0.dp, 0.dp, 0.dp) }
    ) {
        SearchScreen(
            popBackStack = onDismiss,
            onQuoteBookSelected = onBookSelected
        )
    }
}

