package com.danil.ogranizertusur.workspace.screens.add_edit_notes

import android.app.TimePickerDialog
import android.widget.TimePicker
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.danil.ogranizertusur.R
import com.danil.ogranizertusur.ui.theme.LightBlue
import com.danil.ogranizertusur.workspace.alarm_scheduler.AlarmItem
import com.danil.ogranizertusur.workspace.room_model.WorkSpaceEntity
import com.danil.ogranizertusur.workspace.viewmodel.AddActivityViewModelAbstract
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

@Suppress("OPT_IN_IS_NOT_ENABLED")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(
    addViewModel: AddActivityViewModelAbstract,
    onClickClose: () -> Unit,
) {
    val note = addViewModel.selectedNoteState.value
    val textState = rememberSaveable { mutableStateOf(note?.text ?: "") }
    val dateState = rememberSaveable { mutableStateOf(note?.date ?: "") }
    val timeState = rememberSaveable { mutableStateOf(note?.time ?: "") }
    val statusState = rememberSaveable { mutableStateOf(note?.status ?: false) }

    //alarmManager
    val scheduler = addViewModel.scheduler
    var alarmItem: AlarmItem? = null
    //
    //dataPicker values
    val mContext = LocalContext.current

    val mCalendar = Calendar.getInstance()

    var pickedDate by remember {
        mutableStateOf(LocalDate.now())
    }
    var pickedTime by remember {
        mutableStateOf(LocalTime.NOON)
    }
    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("dd.MM.yyyy")
                .format(pickedDate)
        }
    }
    val formattedTime by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("HH:mm")
                .format(pickedTime)
        }
    }
    val formattedTimeOne by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("H:mm")
                .format(pickedTime)
        }
    }
    val dateDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()
    //timePicker

    // Declaring and initializing a calendar
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]

    // Value for storing time as a string

    // Creating a TimePicker dialod


    val mTimePickerDialog = TimePickerDialog(
        mContext,
        { _, mHour: Int, mMinute: Int ->
            var minute = mMinute.toString()
            val hour = mHour.toString()
            if (mMinute.toString().length == 1) {
                minute = "0$mMinute"
            }

            timeState.value = "$hour:$minute"
        }, mHour, mMinute, true
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Задача",
                        fontSize = 24.sp,
                        color = MaterialTheme.colors.onSurface
                    )
                },
                actions = {
                    IconButton(onClick = {
                        if (dateState.value.isNotEmpty()
                            && timeState.value.isNotEmpty()
                            && textState.value.isNotEmpty()
                            && textState.value != " "
                        ) {
                            note?.let {


                                addViewModel.addOrUpdateWorkspace(

                                    it.copy(
                                        date = dateState.value,
                                        time = timeState.value,
                                        text = textState.value,
                                        status = statusState.value
                                    )
                                )

                                Toast.makeText(
                                    mContext.applicationContext,
                                    "Задача обнавлена",
                                    Toast.LENGTH_LONG
                                ).show()
                            } ?: run {

                                addViewModel.addOrUpdateWorkspace(
                                    WorkSpaceEntity(
                                        date = dateState.value,
                                        time = timeState.value,
                                        text = textState.value,
                                        status = statusState.value
                                    )
                                )
                                println("Alarm triggered: ${dateState.value}, ${timeState.value}, ${textState.value}")
                                val localDateTime = addViewModel.convertToLocalDateTime(
                                    dateState.value,
                                    timeState.value
                                )
                                alarmItem = AlarmItem(
                                    time = localDateTime,
                                    text = "Установленная вами задача:${textState.value} стартует прямо сейчас! "
                                )
                                alarmItem?.let(scheduler::schedule)
                                Toast.makeText(
                                    mContext.applicationContext,
                                    "Задача добавлена",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            onClickClose()
                        } else {
                            Toast.makeText(
                                mContext.applicationContext,
                                "Заполните информацию задачи",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }) {
                        Icon(imageVector = Icons.Rounded.Done, contentDescription = "Save")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onClickClose) {
                        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "Close")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(MaterialTheme.colors.background)

            )
        },
        bottomBar = {
            BottomAppBar {
            }
        }

    ) {

        Column(
            modifier = Modifier
                .padding(it)
                // .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colors.background)
                .verticalScroll(rememberScrollState(0))
        ) {

            Row() {
                Text(
                    text = "Выберите дату",
                    modifier = Modifier.padding(
                        top = 16.dp,
                        start = 24.dp,
                        bottom = 16.dp,
                    )
                )
                IconButton(onClick = {
                    dateDialogState.show()
                    //mDatePickerDialog.show()
                }) {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.baseline_calendar_month_24
                        ),
                        contentDescription = "Date", tint = Color.Blue
                    )
                }
            }
            Text(modifier = Modifier
                .padding(
                    start = 16.dp,
                    top = 16.dp,
                    end = 16.dp,
                )
                .fillMaxWidth(),
                //  .fillMaxHeight(0.3f),
                text = dateState.value,
                color = MaterialTheme.colors.onSurface
            )

            Row() {
                Text(
                    text = "Выберите время",
                    modifier = Modifier.padding(
                        top = 16.dp,
                        start = 24.dp,
                        bottom = 16.dp
                    )
                )
                IconButton(onClick = {
                    timeDialogState.show()
                    //mTimePickerDialog.show()
                }) {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.baseline_access_time_24
                        ),
                        contentDescription = "Time", tint = Color.Blue
                    )
                }
            }
            Text(modifier = Modifier
                .padding(
                    start = 16.dp,
                    top = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
                .fillMaxWidth(),
                //   .fillMaxHeight(0.3f),
                text = timeState.value,
                color = MaterialTheme.colors.onSurface
            )
            Text(
                text = "Введите задачу",
                modifier = Modifier.padding(
                    top = 16.dp,
                    start = 24.dp,
                    bottom = 16.dp
                ),
            )
            BasicTextField(modifier = Modifier
                .padding(
                    start = 16.dp,
                    top = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
                .fillMaxWidth(),
                //.fillMaxHeight(0.3f),
                value = textState.value,
                onValueChange = { txt ->
                    textState.value = txt
                },
                textStyle = androidx.compose.ui.text.TextStyle(
                    color = MaterialTheme.colors.onSurface,
                    fontSize = 20.sp
                )
            )
        }
    }
    MaterialDialog(
        dialogState = dateDialogState,
        properties = DialogProperties(
            dismissOnClickOutside = true
        ),
        buttons = {
            positiveButton(
                text = "Ок",
                textStyle = androidx.compose.ui.text.TextStyle(color = MaterialTheme.colors.onSurface)
            ) {
                Toast.makeText(
                    mContext.applicationContext,
                    "Дата задачи установлена",
                    Toast.LENGTH_LONG
                ).show()
            }
            negativeButton(
                text = "Закрыть",
                textStyle = androidx.compose.ui.text.TextStyle(color = MaterialTheme.colors.onSurface)
            )
        }
    ) {
        datepicker(
            LocalDate.now(),
            title = "Выберите дату",
            colors = DatePickerDefaults.colors(LightBlue)
            ) {
            pickedDate = it
            dateState.value = formattedDate
        }
    }
    MaterialDialog(
        dialogState = timeDialogState,
        properties = DialogProperties(
            dismissOnClickOutside = true
        ),
        buttons = {
            positiveButton(
                text = "Ок",
                textStyle = androidx.compose.ui.text.TextStyle(color = MaterialTheme.colors.onSurface)
            ) {
                Toast.makeText(
                    mContext.applicationContext,
                    "Время задачи установлено",
                    Toast.LENGTH_LONG
                ).show()
            }
            negativeButton(
                text = "Закрыть",
                textStyle = androidx.compose.ui.text.TextStyle(color = MaterialTheme.colors.onSurface)
            )
        }
    ) {
        timepicker(
            LocalTime.now(),
            title = "Выберите время",
            colors = TimePickerDefaults.colors(LightBlue),
            timeRange = LocalTime.MIN..LocalTime.MAX,
            is24HourClock = true,
        ) {
            pickedTime = it
            timeState.value = if (pickedTime.hour<10 && pickedTime.hour != 0){
                formattedTimeOne
            }else{
                formattedTime
            }



        }
    }
}
