package com.danil.ogranizertusur.workspace.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.danil.ogranizertusur.workspace.room_model.WorkSpaceEntity
import com.danil.ogranizertusur.workspace.viewmodel.AddActivityViewModelAbstract

@Composable
fun NoteItemCard(note:WorkSpaceEntity, addViewModel: AddActivityViewModelAbstract){
   // var status1 = false

    val status =  remember{mutableStateOf(note.status)
    }

    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
        Card {
            Row() {
                RadioButton(selected = status.value, onClick = {
                    addViewModel.deleteWorkspace(note)
                    addViewModel.addOrUpdateWorkspace(
                    WorkSpaceEntity(date = note.date,
                    time = note.time,
                    text = note.text,
                    status = !status.value)
                )
                    status.value = !status.value
                }, modifier = Modifier.padding(end = 5.dp))
                Text(
                    modifier = Modifier
                        .padding(end = 5.dp),
                    text = note.date
                )
                Text(
                    modifier = Modifier
                        .padding(end = 5.dp),
                    text = note.time
                )
                Text(
                    modifier = Modifier
                        .padding(end = 5.dp),
                    text = note.text
                )
            }

        }
    }
}