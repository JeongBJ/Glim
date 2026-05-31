package com.jeongbj.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jeongbj.presentation.R

val glimDefaultFont = FontFamily(
    Font(R.font.ridi_batang, FontWeight.Normal),
)

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = glimDefaultFont,
        fontWeight = FontWeight.Normal,
        fontSize = 56.sp,
        letterSpacing = 0.5.sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = glimDefaultFont,
        fontWeight = FontWeight.Normal,
        fontSize = 40.sp,
        letterSpacing = 0.5.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = glimDefaultFont,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        letterSpacing = 0.5.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = glimDefaultFont,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        letterSpacing = 0.5.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = glimDefaultFont,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        letterSpacing = 0.5.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = glimDefaultFont,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        letterSpacing = 0.5.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = glimDefaultFont,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        letterSpacing = 0.5.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = glimDefaultFont,
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.5.sp
    ),

    bodyMedium = TextStyle(
        fontFamily = glimDefaultFont,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.5.sp
    ),

    bodySmall = TextStyle(
        fontFamily = glimDefaultFont,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.5.sp
    ),
    labelLarge =
        TextStyle(
            fontFamily = glimDefaultFont,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            letterSpacing = 0.5.sp,
        ),
    labelMedium =
        TextStyle(
            fontFamily = glimDefaultFont,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            letterSpacing = 0.5.sp,
        ),
    labelSmall =
        TextStyle(
            fontFamily = glimDefaultFont,
            fontWeight = FontWeight.Normal,
            fontSize = 10.sp,
            letterSpacing = 0.5.sp,
        ),
)