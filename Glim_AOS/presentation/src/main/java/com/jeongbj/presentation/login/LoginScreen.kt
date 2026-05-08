package com.jeongbj.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.jeongbj.presentation.R
import com.jeongbj.presentation.preview.Previews

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateHome: () -> Unit
) {

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            if(event is LoginEvent.LoginSuccess) {

            }
        }
    }

    LoginContent(
        onKakaoClick = {
            viewModel.onClickEvent(LoginEvent.OnKakaoClick)
        },
        onGoogleClick = {
            viewModel.onClickEvent(LoginEvent.OnGoogleClick)
        }
    )

}

@Composable
fun LoginContent(
    onKakaoClick: () -> Unit,
    onGoogleClick: () -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0A0A1A),
                        Color(0xFF000000)
                    )
                )
            )
    ) {

        when {
            maxWidth < 600.dp -> {
                LoginPortrait(onKakaoClick, onGoogleClick)
            }
            else -> {
                LoginLandscape(onKakaoClick, onGoogleClick)
            }
        }
    }
}

@Composable
fun LoginPortrait(onKakaoClick: () -> Unit, onGoogleClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(40.dp))

        LogoSection()

        LoginButtons(onGoogleClick, onGoogleClick)
    }
}

@Composable
fun LoginLandscape(onKakaoClick: () -> Unit, onGoogleClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LogoSection()
        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            LoginButtons(onKakaoClick, onGoogleClick)
        }
    }
}

@Composable
fun LoginButtons(onKakaoLogin: () -> Unit, onGoogleLogin: () -> Unit) {
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
            onClick = onKakaoLogin
        )

        Spacer(modifier = Modifier.height(12.dp))

        SocialLoginButton(
            text = "Google로 로그인",
            backgroundColor = Color.White,
            textColor = Color.Black,
            icon = R.drawable.logo_google,
            onClick = onGoogleLogin
        )
    }

    Spacer(modifier = Modifier.height(40.dp))
}

@Composable
fun LogoSection() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Image(
            painter = painterResource(id = R.drawable.logo_glim_image),
            contentDescription = null,
            modifier = Modifier.size(120.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.glim_logo_text),
            contentDescription = null,
            modifier = Modifier.size(120.dp)
        )
    }
}


@Composable
fun SocialLoginButton(
    text: String,
    backgroundColor: Color,
    textColor: Color,
    icon: Int,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = text,
                color = textColor,
                fontWeight = FontWeight.Medium
            )
        }
    }
}


@Previews
@Composable
fun LoginScreenPreview() {
    LoginContent(
        onKakaoClick = {},
        onGoogleClick = {}
    )
}