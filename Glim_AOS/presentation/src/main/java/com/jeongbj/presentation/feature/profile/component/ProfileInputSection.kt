package com.jeongbj.presentation.feature.profile.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ProfileInputSection(
    nickname: String,
    onNicknameChange: (String) -> Unit,
    onCompleteClick: () -> Unit,
    isButtonEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = nickname,
            onValueChange = onNicknameChange,
            label = { Text("닉네임") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onCompleteClick,
            enabled = isButtonEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("시작하기")
        }
    }
}

@Preview
@Composable
fun ProfileInputSectionPreview() {
    ProfileInputSection("닉네임", {} , {}, true)
}