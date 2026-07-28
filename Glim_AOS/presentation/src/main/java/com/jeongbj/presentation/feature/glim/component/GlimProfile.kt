package com.jeongbj.presentation.feature.glim.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.jeongbj.presentation.R
import com.jeongbj.presentation.common.preview.Previews
import com.jeongbj.presentation.feature.post.component.DarkGrayRoundedSurface

@Composable
fun GlimProfile(
    modifier: Modifier = Modifier,
    imageModel: Any?,
    nickname: String,
    onProfileClicked: () -> Unit,
) {
    DarkGrayRoundedSurface(modifier = modifier
        .clickable { onProfileClicked() }
    ) {
        Row(
            modifier = modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
        ) {
            AsyncImage(
                model = imageModel,
                contentDescription = "프로필 이미지",
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape),
                error = painterResource(R.drawable.img_empty_profile)
            )

            Spacer(Modifier.width(8.dp))

            Text(
                text = nickname,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Previews
@Composable
fun GlimProfilePreview() {
    GlimProfile(
        imageModel = null,
        nickname = "nickname",
        onProfileClicked = {}
    )
}