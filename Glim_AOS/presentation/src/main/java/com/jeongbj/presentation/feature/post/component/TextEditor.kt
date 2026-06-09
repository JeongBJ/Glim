package com.jeongbj.presentation.feature.post.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jeongbj.android.R
import com.jeongbj.presentation.common.component.ActionButton
import com.jeongbj.presentation.common.preview.Previews
import com.jeongbj.presentation.feature.post.PostAction
import com.jeongbj.presentation.feature.post.PostState
import com.jeongbj.presentation.feature.post.component.texteditor.FontFamilySelector
import com.jeongbj.presentation.feature.post.component.texteditor.TextColorSelector
import com.jeongbj.presentation.feature.post.component.texteditor.TextSizeController
import com.jeongbj.presentation.feature.post.component.texteditor.TextStyleController
import com.jeongbj.presentation.theme.GlimTheme

@Composable
fun TextEditor(
    state: PostState,
    onAction: (PostAction) -> Unit,
    modifier: Modifier = Modifier
) {
    var additionalTextOption by remember { mutableStateOf(AdditionalTextOption.NONE) }

    Column(
        modifier = modifier
            .imePadding()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (additionalTextOption == AdditionalTextOption.COLOR) {
            TextColorSelector(selectedColor = { onAction(PostAction.OnTextColorSelected(it)) } )
        } else if (additionalTextOption == AdditionalTextOption.FONT) {
            FontFamilySelector(selectedFontFamily = { onAction(PostAction.OnFontFamilySelected(it)) })
        }

        DarkGrayRoundedSurface {
            Row(
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                TextSizeController(
                    onIncreaseFontSize = { onAction(PostAction.OnIncreaseFontSize) },
                    onDecreaseFontSize = { onAction(PostAction.OnDecreaseFontSize) }
                )

                TextStyleController(
                    isBold = state.postText.textStyleState.isBold,
                    isItalic = state.postText.textStyleState.isItalic,
                    onToggleBold = { onAction(PostAction.OnToggleBold) },
                    onToggleItalic = { onAction(PostAction.OnToggleItalic) }
                )

                ActionButton(
                    onClick = { additionalTextOption = AdditionalTextOption.FONT },
                    painter = painterResource(R.drawable.ic_font)
                )

                ActionButton(
                    onClick = { additionalTextOption = AdditionalTextOption.COLOR },
                    painter = painterResource(R.drawable.ic_pallete)
                )
            }
        }
    }
}





enum class AdditionalTextOption {
    NONE,
    COLOR,
    FONT
}

@Previews
@Composable
fun TextEditorPreview() {
    GlimTheme {
        TextEditor(
            state = PostState(),
            onAction = { }
        )
    }
}
