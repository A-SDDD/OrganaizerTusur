package com.danil.ogranizertusur.workspace.screens.notes.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material.Card
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.danil.ogranizertusur.ui.theme.LightBlue
import com.danil.ogranizertusur.workspace.room_model.WorkSpaceEntity
import com.danil.ogranizertusur.workspace.viewmodel.AddActivityViewModelAbstract

@Composable
fun NoteItemCard(note: WorkSpaceEntity, addViewModel: AddActivityViewModelAbstract) {
    // var status1 = false

    val status = remember {
        mutableStateOf(note.status)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 1.dp),
        backgroundColor = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            RadioButton(
                selected = status.value, onClick = {
                    addViewModel.deleteWorkspace(note)
                    addViewModel.addOrUpdateWorkspace(
                        WorkSpaceEntity(
                            date = note.date,
                            time = note.time,
                            text = note.text,
                            status = !status.value
                        )
                    )
                    status.value = !status.value
                },
                colors = RadioButtonDefaults.colors(LightBlue),
                modifier = Modifier
                    .padding(end = 5.dp)
                    .align(CenterVertically)
            )
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .wrapContentHeight(CenterVertically),
                    text = note.text
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp)
                )
                Card(
                    border = BorderStroke(1.dp, color = Color.Black),
                    colors = CardDefaults.cardColors(Color.LightGray)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(end = 5.dp)
                            .align(CenterHorizontally),
                        text = " ${note.date},${note.time} "
                    )
                }
            }
        }
    }
}
