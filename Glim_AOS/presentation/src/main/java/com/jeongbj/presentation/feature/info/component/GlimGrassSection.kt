package com.jeongbj.presentation.feature.info.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.zIndex
import com.jeongbj.presentation.common.preview.Previews
import com.jeongbj.presentation.theme.GlimTheme
import java.time.DayOfWeek
import java.time.LocalDate
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

    var selectedDate by remember {
        mutableStateOf<LocalDate?>(null)
    }

    val cellSize = 16.dp
    val spacing = 4.dp

    val weeks = remember(startDate, endDate) {
        buildList {
            var weekStart = startDate

            while (!weekStart.isAfter(endDate)) {
                buildList {
                    repeat(7) { day ->
                        val date = weekStart.plusDays(day.toLong())

                        if (!date.isAfter(endDate)) {
                            add(date)
                        }
                    }
                }.let(::add)

                weekStart = weekStart.plusWeeks(1)
            }
        }
    }

    LaunchedEffect(Unit) {
        scrollState.scrollTo(scrollState.maxValue)
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .background(Color.White)
    ) {
        WeekdayTitle()

        Spacer(modifier = Modifier.width(8.dp))

        Row(
            modifier = Modifier
                .horizontalScroll(scrollState),
            horizontalArrangement = Arrangement.spacedBy(spacing)
        ) {
            weeks.forEach { week ->
                Column(
                    verticalArrangement = Arrangement.spacedBy(spacing)
                ) {
                    week.forEach { date ->
                        val count = contributions[date] ?: 0

                        val color = when {
                            count == 0 -> Color(0xFFEBEDF0)
                            count < 3 -> Color(0xFF9BE9A8)
                            count < 6 -> Color(0xFF40C463)
                            else -> Color(0xFF216E39)
                        }

                        Box(
                            modifier = Modifier
                                .size(cellSize)
                                .zIndex(if (selectedDate == date) 1f else 0f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(cellSize)
                                    .clip(RoundedCornerShape(cellSize * 0.2f))
                                    .background(color)
                                    .clickable {
                                        selectedDate =
                                            if (selectedDate == date) null
                                            else date
                                    }
                            )

                            if (selectedDate == date) {
                                Popup(
                                    alignment = Alignment.TopCenter,
                                    onDismissRequest = {
                                        selectedDate = null
                                    }
                                ) {
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        shadowElevation = 4.dp
                                    ) {
                                        Text(
                                            text = "${date.monthValue}/${date.dayOfMonth} · ${count}회",
                                            modifier = Modifier.padding(
                                                horizontal = 8.dp,
                                                vertical = 6.dp
                                            ),
                                            fontSize = 12.sp,
                                            maxLines = 1
                                        )
                                    }
                                }
                            }
                        }
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