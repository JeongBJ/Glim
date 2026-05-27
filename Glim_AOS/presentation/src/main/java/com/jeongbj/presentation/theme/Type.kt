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
// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = glimDefaultFont,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.5.sp
    ),

    bodyMedium = TextStyle(
        fontFamily = glimDefaultFont,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.5.sp
    ),

    bodySmall = TextStyle(
        fontFamily = glimDefaultFont,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.5.sp
    )


    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)