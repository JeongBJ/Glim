package com.jeongbj.presentation.feature.book.search.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.jeongbj.presentation.R
import com.jeongbj.presentation.common.preview.Previews
import com.jeongbj.presentation.feature.book.search.SearchAction
import com.jeongbj.presentation.feature.book.search.SearchMode
import com.jeongbj.presentation.feature.book.search.SearchState
import com.jeongbj.presentation.theme.GlimTheme

@Composable
fun SearchTopSection(
    state: SearchState,
    onAction: (SearchAction) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        if (state.searchMode == SearchMode.POPULAR) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "도서 검색",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "글림, 책 제목, 작가 이름으로 찾아보세요.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                )
            }
        } else {
            Spacer(modifier = Modifier.height(16.dp))
        }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 16.dp),
            value = state.query,
            placeholder = {
                Text(
                    text = "검색어를 입력해주세요.",
                    style = MaterialTheme.typography.bodySmall
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            onValueChange = { onAction(SearchAction.OnTextChanged(it)) },
            suffix = {
                IconButton(
                    onClick = {
                        focusManager.clearFocus()
                        onAction(SearchAction.OnSearchClick)
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_search),
                        contentDescription = null
                    )
                }

            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                focusManager.clearFocus()
                onAction(SearchAction.OnSearchClick)
            }
            )
        )
    }
}

@Previews
@Composable
fun SearchTopSectionPreview() {
    GlimTheme() {
        SearchTopSection(SearchState(searchMode = SearchMode.POPULAR)) { }
    }
}