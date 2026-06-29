package com.jeongbj.presentation.feature.info.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jeongbj.domain.user.model.User
import com.jeongbj.presentation.R
import com.jeongbj.presentation.common.component.ActionButton
import com.jeongbj.presentation.common.preview.Previews
import com.jeongbj.presentation.feature.profile.component.ProfileImageSection
import com.jeongbj.presentation.theme.GlimTheme

@Composable
fun InfoHeaderSection(
    modifier: Modifier = Modifier,
    onProfileImageClicked: () -> Unit,
    onSettingClicked: () -> Unit,
    user: User?,
    isOwner: Boolean = false,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (isOwner) {
            Column (
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.End
            ) {
                ActionButton(
                    painter = painterResource(R.drawable.ic_settings),
                    onClick = onSettingClicked
                )
            }
        }

        ProfileImageSection(
            imageModel = user?.imageUrl,
            onImageClick = onProfileImageClicked,
            isOwner = isOwner
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = user?.nickname?: "",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black,
        )

    }
}

@Previews
@Composable
fun InfoHeaderSectionPreview() {
    GlimTheme {
        InfoHeaderSection(
            onProfileImageClicked = {  },
            onSettingClicked = {  },
            user = User(
                nickname = "nickname",
                imageUrl = null
            ),
            isOwner = true
        )
    }
}
