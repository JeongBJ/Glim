package com.jeongbj.presentation.feature.post.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import com.jeongbj.presentation.R
import com.jeongbj.presentation.common.camera.CameraTarget
import com.jeongbj.presentation.common.component.ActionButton

@Composable
fun CameraPopupMenu(
    launchCamera: (CameraTarget) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        ActionButton(
            onClick = { expanded = !expanded },
            painter = painterResource(R.drawable.ic_camera),
            contentDescription = "이미지 촬영 및 텍스트 인식"
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("배경 이미지 촬영") },
                leadingIcon = {
                    Icon(painter = painterResource(R.drawable.ic_image_empty), contentDescription = null)
                },
                onClick = {
                    launchCamera(CameraTarget.BACKGROUND)
                    expanded = false
                },
            )
        }
    }

}