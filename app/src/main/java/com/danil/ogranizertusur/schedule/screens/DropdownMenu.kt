package com.danil.ogranizertusur.schedule.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DropDownMenu(
    expanded: MutableState<Boolean>,
    selectedItem:MutableState<String>,
    groupsByFaculties:MutableState<List<String>>,
    facultyOrGroups:MutableState<Int>,
    group:MutableState<String>,
    update:MutableState<Int>
){

    val list: List<String> = when(facultyOrGroups.value){
        1-> listOf("aspirantura","rkf", "fsu","fit","gf","fb","rtf","fvs","fet","ef","yuf","zivf")
        else->{
            groupsByFaculties.value
        }
    }
    
    Column(modifier = Modifier.padding(20.dp)) {
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false
                               facultyOrGroups.value=1
                               },
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .fillMaxHeight()
        ) {
            list.forEach {label ->
                DropdownMenuItem(onClick = {



                    when (facultyOrGroups.value){
                        1->{
                            selectedItem.value = label
                            facultyOrGroups.value = 2
                            expanded.value = false
                            expanded.value = true
                        }

                        2->{
                            group.value = label
                            facultyOrGroups.value = 1
                            expanded.value = false
                            update.value = 2
                            groupsByFaculties.value = listOf()
                        }
                    }
                }) {
                    Text(text = label)
                }
            }
        }
    }
}