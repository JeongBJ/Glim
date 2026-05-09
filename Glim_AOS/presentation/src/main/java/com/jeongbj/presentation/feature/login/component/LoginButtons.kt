package com.jeongbj.presentation.feature.login.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jeongbj.presentation.R
import com.jeongbj.presentation.feature.login.LoginEvent

@Composable
fun LoginButtons(onEvent: (LoginEvent) -> Unit) {
    Column(
        modifier = Modifier.widthIn(max = 320.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        SocialLoginButton(
            text = "카카오로 로그인",
            backgroundColor = Color(0xFFFEE500),
            textColor = Color.Black,
            icon = R.drawable.logo_kakao,
            onClick = { onEvent(LoginEvent.OnKakaoClick) }
        )

        Spacer(modifier = Modifier.height(12.dp))

        SocialLoginButton(
            text = "Google로 로그인",
            backgroundColor = Color.White,
            textColor = Color.Black,
            icon = R.drawable.logo_google,
            onClick = { onEvent(LoginEvent.OnGoogleClick) }
        )
    }

    Spacer(modifier = Modifier.height(40.dp))
}