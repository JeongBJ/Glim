package com.jeongbj.presentation.feature.info.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.jeongbj.presentation.common.preview.Previews
import com.jeongbj.presentation.theme.GlimTheme
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters

@Composable
fun GlimGrassSection(
    modifier: Modifier = Modifier,
    contributions: Map<LocalDate, Int>,
) {
    val scrollState = rememberScrollState()


    val today = remember { LocalDate.now() }
    val startDate = remember(today) {
        today.minusWeeks(52)
            .with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))
    }
    val endDate = remember(today) {
        today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY))
    }

    val cellSize = 16.dp
    val spacing = 4.dp

    LaunchedEffect(Unit) {
        scrollState.scrollTo(scrollState.maxValue)
    }


    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Row(
            modifier = modifier
        ) {
            WeekdayTitle()

            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier.horizontalScroll(scrollState)
            ) {
                Canvas(
                    modifier = modifier
                        .width((53 * cellSize) + (52 * spacing))
                        .height((7 * cellSize) + (6 * spacing))
                        .background(Color.White)
                ) {
                    val cell = cellSize.toPx()
                    val gap = spacing.toPx()

                    var date = startDate

                    while (!date.isAfter(endDate)) {
                        val week = ChronoUnit.WEEKS.between(startDate, date).toInt()
                        val row = date.dayOfWeek.ordinal.plus(1) % 7
                        val count = contributions[date] ?: 0

                        val color = when {
                            count == 0 -> Color(0xFFEBEDF0)
                            count < 3 -> Color(0xFF9BE9A8)
                            count < 6 -> Color(0xFF40C463)
                            else -> Color(0xFF216E39)
                        }

                        drawRoundRect(
                            color = color,
                            topLeft = Offset(
                                x = week * (cell + gap),
                                y = row * (cell + gap)
                            ),
                            size = Size(cell, cell),
                            cornerRadius = CornerRadius(cell * 0.2f)
                        )

                        date = date.plusDays(1)
                    }
                }
            }
        }
    }
}

@Composable
fun WeekdayTitle(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        listOf("일", "월", "화", "수", "목", "금", "토").forEach {
            Box(
                modifier = Modifier.size(18.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = it,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Previews
@Composable
fun GlimGrassSectionPreview() {
    GlimTheme {
        GlimGrassSection(
            contributions = mapOf(Pair(LocalDate.now(), 5))
        )
    }
}