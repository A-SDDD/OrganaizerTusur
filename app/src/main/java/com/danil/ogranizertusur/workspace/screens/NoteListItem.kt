@file:OptIn(ExperimentalMaterialApi::class)

package com.danil.ogranizertusur.workspace.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.danil.ogranizertusur.workspace.room_model.WorkSpaceEntity
import com.danil.ogranizertusur.workspace.viewmodel.AddActivityViewModelAbstract

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteListItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    note: WorkSpaceEntity,
    dismissState: DismissState,
    addViewModel: AddActivityViewModelAbstract
) {
    SwipeToDismiss(state = dismissState,
        background = {
            val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
            val color by animateColorAsState(
                when (dismissState.targetValue) {
                    DismissValue.Default -> Color.Blue
                    DismissValue.DismissedToEnd -> Color.Red
                    DismissValue.DismissedToStart -> Color.Red
                }
            )
            val alignment = when (direction) {
                DismissDirection.StartToEnd -> Alignment.CenterStart
                DismissDirection.EndToStart -> Alignment.CenterEnd
            }
            val iconTint = when (dismissState.targetValue) {
                DismissValue.Default -> Color.Red
                DismissValue.DismissedToEnd -> Color.White
                DismissValue.DismissedToStart -> Color.White
            }
            val scale by animateFloatAsState(
                if (dismissState.targetValue == DismissValue.Default) 0.8f else 1f
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = color)
                    .padding(horizontal = 16.dp),
                contentAlignment = alignment
            ) {
                Icon(
                    modifier = Modifier.scale(scale),
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = "Delete",
                    tint = iconTint
                )
            }
        }
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.background)
                .combinedClickable(
                    onClick = {
                        onClick()
                        /*addViewModel.selectedNote(note)
                        onClickNote(note)*/
                    },
                    onLongClick = {
                        onDelete()
                        // addViewModel.deleteWorkspace(note)
                    }
                )

                .height(54.dp),
        ) {


            NoteItemCard(note = note, addViewModel)

          /*  Text(
                modifier = Modifier
                    .align(Alignment.CenterStart),
                text = note.date
            )
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = note.time
            )
            Text(
                modifier = Modifier
                    .align(Alignment.CenterEnd),
                text = note.text
            )*/
        }

    }

}