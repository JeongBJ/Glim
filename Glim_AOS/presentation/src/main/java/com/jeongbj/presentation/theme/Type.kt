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

val dohyeonFont =
    FontFamily(
        Font(R.font.dohyeon_regular, FontWeight.Normal),
    )

val yeonsungFont =
    FontFamily(
        Font(R.font.yeonsung_regular, FontWeight.Normal),
    )

val nanumBrushScriptFont =
    FontFamily(
        Font(R.font.nanum_brush_script_regular, FontWeight.Normal)
    )

val caveatFont =
    FontFamily(
        Font(R.font.caveat_variable, FontWeight.Normal)
    )

enum class GlimFonts(val fontName: String, val fontFamily: FontFamily) {
    RIDI_BATING("default", glimDefaultFont),
    DOHYEON("dohyeon", dohyeonFont),
    YEONSUNG("yeonsung", yeonsungFont),
    NANUM_BRUSH_SCRIPT("nanum_brush_script", nanumBrushScriptFont)
}

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = glimDefaultFont,
        fontWeight = FontWeight.Normal,
        fontSize = 56.sp,
        lineHeight = 70.sp,
        letterSpacing = 0.5.sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = glimDefaultFont,
        fontWeight = FontWeight.Normal,
        fontSize = 40.sp,
        lineHeight = 50.sp,
        letterSpacing = 0.5.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = glimDefaultFont,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 45.sp,
        letterSpacing = 0.5.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = glimDefaultFont,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.5.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = glimDefaultFont,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        lineHeight = 35.sp,
        letterSpacing = 0.5.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = glimDefaultFont,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 30.sp,
        letterSpacing = 0.5.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = glimDefaultFont,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 25.sp,
        letterSpacing = 0.5.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = glimDefaultFont,
        fontSize = 18.sp,
        lineHeight = 23.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.5.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = glimDefaultFont,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.5.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = glimDefaultFont,
        fontSize = 14.sp,
        lineHeight = 18.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.5.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = glimDefaultFont,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.5.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = glimDefaultFont,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 15.sp,
        letterSpacing = 0.5.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = glimDefaultFont,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        lineHeight = 13.sp,
        letterSpacing = 0.5.sp,
    ),
)