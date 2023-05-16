package com.danil.ogranizertusur.schedule.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.danil.ogranizertusur.schedule.domain.model.Schedule

@Composable
fun DropDownMenu(
    expanded: MutableState<Boolean>,
    selectedItem:MutableState<String>,
    groupsByFaculties:MutableState<List<String>>,
    facultyOrGroups:MutableState<Int>,
    groupAndFacultyFromDb:State<List<Schedule>>,
    group:MutableState<String>,
    update:MutableState<Int>,
    scheduleViewModel:ScheduleViewModel
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
                            if(groupAndFacultyFromDb.value == emptyList<Schedule>()){
                                scheduleViewModel.setSchedule(Schedule(id = 0,faculty = label, group = "гр."))
                            }else{
                                scheduleViewModel.setSchedule(Schedule(
                                    id = 0, faculty = label, group = "гр."
                                ))
                            }

                        }

                        2->{
                            group.value = label
                                scheduleViewModel.setSchedule(Schedule(
                                    id = 0, faculty = groupAndFacultyFromDb.value[0].faculty, group = label
                                ))

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