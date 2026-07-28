package com.jeongbj.presentation.feature.glim.component

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
import com.jeongbj.presentation.common.component.ActionButton
import com.jeongbj.presentation.common.component.ConfirmDialog

@Composable
fun GlimPopupMenu(
    onDeleteClicked: () -> Unit,
    onBlockUserClicked: () -> Unit,
    onBlockQuoteClicked: () -> Unit,
    isOwner: Boolean = false,
) {
    var expanded by remember { mutableStateOf(false) }
    var showConfirmDialog by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(GLIM_POPUP_ITEM.DELETE) }
    Box {
        ActionButton(
            onClick = { expanded = !expanded },
            painter = painterResource(R.drawable.ic_more_vert)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            if (isOwner) {
                DropdownMenuItem(
                    text = { Text("삭제") },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_delete),
                            contentDescription = null
                        )
                    },
                    onClick = {
                        selectedItem = GLIM_POPUP_ITEM.DELETE
                        showConfirmDialog = true
                    },
                )
            } else {
                DropdownMenuItem(
                    text = { Text("글림 차단") },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_error),
                            contentDescription = null
                        )
                    },
                    onClick = {
                        selectedItem = GLIM_POPUP_ITEM.BLOCK_QUOTE
                        showConfirmDialog = true
                    },
                )

                DropdownMenuItem(
                    text = { Text("사용자 차단") },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_error),
                            contentDescription = null
                        )
                    },
                    onClick = {
                        selectedItem = GLIM_POPUP_ITEM.BLOCK_USER
                        showConfirmDialog = true
                    },
                )
            }
        }

        if (showConfirmDialog) {
            ConfirmDialog(
                onDismiss = {
                    showConfirmDialog = false
                    expanded = false
                },
                title = when (selectedItem) {
                    GLIM_POPUP_ITEM.DELETE -> selectedItem.string
                    GLIM_POPUP_ITEM.BLOCK_QUOTE -> selectedItem.string
                    GLIM_POPUP_ITEM.BLOCK_USER -> selectedItem.string
                },
                message = when (selectedItem) {
                    GLIM_POPUP_ITEM.DELETE -> "삭제하면 복구할 수 없어요.\n정말 삭제하시겠어요?"
                    GLIM_POPUP_ITEM.BLOCK_QUOTE -> "차단한 글림은 다시 볼 수 없어요.\n차단하시겠어요?"
                    GLIM_POPUP_ITEM.BLOCK_USER -> "차단한 유저가 작성한 모든 글림을 볼 수 없어요.\n차단하시겠어요?"
                },
                onConfirm = {
                    expanded = false
                    when (selectedItem) {
                        GLIM_POPUP_ITEM.DELETE -> onDeleteClicked()
                        GLIM_POPUP_ITEM.BLOCK_QUOTE -> onBlockQuoteClicked()
                        GLIM_POPUP_ITEM.BLOCK_USER -> onBlockUserClicked()
                    }
                },
            )
        }
    }
}

private enum class GLIM_POPUP_ITEM(val string: String) {
    DELETE("글림 삭제"), BLOCK_USER("사용자 차단"), BLOCK_QUOTE("글림 차단")
}