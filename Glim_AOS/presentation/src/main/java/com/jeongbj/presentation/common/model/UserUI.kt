package com.jeongbj.presentation.common.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserUI(
    val nickname: String,
    val imageUrl: String? = null
) : Parcelable