package com.jeongbj.presentation.feature.post.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.visible
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jeongbj.presentation.R
import com.jeongbj.presentation.common.component.ActionButton
import com.jeongbj.presentation.common.preview.Previews
import com.jeongbj.presentation.feature.post.PostAction
import com.jeongbj.presentation.feature.post.PostState
import com.jeongbj.presentation.theme.GlimTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoxScope.PostButtons(
    state: PostState,
    onAction: (PostAction) -> Unit,
    modifier: Modifier = Modifier
) {

    val tooltipState = rememberTooltipState()

    Column(
        modifier = modifier
            .fillMaxHeight(),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DarkGrayRoundedSurface(
                modifier = Modifier.visible(state.buttonVisible)
            ) {
                IconButton(
                    onClick = { onAction(PostAction.OnCloseClicked) }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_close),
                        contentDescription = null
                    )
                }
            }

            DarkGrayRoundedSurface {
                IconButton(
                    onClick = { onAction(PostAction.ToggleButtonVisible) }
                ) {
                    Icon(
                        painter = painterResource(
                            if (state.buttonVisible) R.drawable.ic_visibility
                            else R.drawable.ic_ic_visibility_off
                        ),
                        contentDescription = null
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            DarkGrayRoundedSurface(
                modifier = Modifier.visible(state.buttonVisible)
            ) {
                TextButton(
                    onClick = { onAction(PostAction.OnCompleteClicked) }
                ) {
                    Text("완료", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }

    if(state.buttonVisible) {
        Surface(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 16.dp)
                .align(Alignment.CenterEnd),
            color = Color.DarkGray.copy(alpha = 0.6f),
            shape = RoundedCornerShape(12.dp),
        ) {
            Column(
                modifier = Modifier.padding(vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.End
            ) {
                TooltipBox(
                    positionProvider = TooltipDefaults.rememberTooltipPositionProvider(
                        positioning = TooltipAnchorPosition.Above
                    ),
                    state = tooltipState,
                    tooltip = { PlainTooltip { Text("") } }
                ) {
                    ActionButton(
                        onClick = { onAction(PostAction.OnImageGenerateClicked) },
                        painter = painterResource(R.drawable.ic_image_create)
                    )
                }

                CameraPopupMenu { onAction(PostAction.OnLaunchCameraClicked(it)) }

                ActionButton(
                    onClick = { onAction(PostAction.OnTextRecognitionClicked) },
                    painter = painterResource(R.drawable.ic_recogize)
                )

                ActionButton(
                    onClick = { onAction(PostAction.OnBackgroundImageClicked) },
                    painter = painterResource(R.drawable.ic_image_empty)
                )

                ActionButton(
                    onClick = { onAction(PostAction.OnCreateTextClicked) },
                    painter = painterResource(R.drawable.ic_text)
                )
            }
        }

        DarkGrayRoundedSurface(
            modifier = Modifier
                .height(280.dp)
                .padding(4.dp)
                .align(Alignment.CenterStart)
        ) {
            VerticalSlider(
                state = state,
                onAction = onAction,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

@Composable
fun DarkGrayRoundedSurface(
    modifier: Modifier = Modifier,
    alpha: Float = 0.8f,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier.padding(8.dp),
        color = Color.DarkGray.copy(alpha = alpha),
        shape = RoundedCornerShape(12.dp),
    ) {
        content()
    }
}



@Previews
@Composable
fun PostButtonsPreview() {
    GlimTheme {
        Box(

        ) {
            PostButtons(
                state = PostState(

                ),
                onAction = { }
            )
        }

    }
}