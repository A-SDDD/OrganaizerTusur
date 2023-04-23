package com.danil.ogranizertusur.workspace.screens

import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.danil.ogranizertusur.workspace.room_model.WorkSpaceEntity
import com.danil.ogranizertusur.workspace.viewmodel.AddActivityViewModelAbstract
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

@Suppress("OPT_IN_IS_NOT_ENABLED")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(
    addViewModel: AddActivityViewModelAbstract,
    onClickClose: () -> Unit,
) {
    val note = addViewModel.selectedNoteState.value
    val textState = rememberSaveable { mutableStateOf(note?.text ?: " ") }
    val dateState = rememberSaveable { mutableStateOf(note?.date ?: " ") }
    val timeState = rememberSaveable { mutableStateOf(note?.time ?: " ") }
    val statusState = rememberSaveable { mutableStateOf(note?.status ?: false) }

    //dataPicker values
    val mContext = LocalContext.current

    val mDay: Int
    val mMonth: Int
    val mYear: Int

    val mCalendar = Calendar.getInstance()

    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
    mMonth = mCalendar.get(Calendar.MONTH)
    mYear = mCalendar.get(Calendar.YEAR)

   /* val mDate = remember { mutableStateOf("") }

    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            dateState.value = "$mDayOfMonth.${mMonth+1}.$mYear"
        }, mYear, mMonth, mDay
    )
*/
    var pickedDate by remember {
        mutableStateOf(LocalDate.now())
    }
    var pickedTime by remember {
        mutableStateOf(LocalTime.NOON)
    }
    val formattedDate by remember {
        derivedStateOf{
            DateTimeFormatter
                .ofPattern("dd.MM.yyyy")
                .format(pickedDate)
        }
    }
    val formattedTime by remember {
        derivedStateOf{
            DateTimeFormatter
                .ofPattern("hh:mm")
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
        {_, mHour : Int, mMinute: Int ->
            var minute = mMinute.toString()
            var hour = mHour.toString()
            if(mMinute.toString().length == 1){
                minute = "0$mMinute"
            }

            timeState.value = "$hour:$minute"
        }, mHour, mMinute, true
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Задача", fontSize = 24.sp)
                },
                actions = {
                    /*  Row(
                          modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                      ) {*/

                    IconButton(onClick = {
                        note?.let {
                            addViewModel.addOrUpdateWorkspace(
                                it.copy(
                                    date = dateState.value,
                                    time = timeState.value,
                                    text = textState.value,
                                    status = statusState.value
                                )
                            )
                        } ?: run {
                            addViewModel.addOrUpdateWorkspace(
                                WorkSpaceEntity(
                                    date = dateState.value,
                                    time = timeState.value,
                                    text = textState.value,
                                    status = statusState.value
                                )
                            )
                        }
                        onClickClose()
                    }) {
                        Icon(imageVector = Icons.Rounded.Done, contentDescription = "Save")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onClickClose) {
                        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "Close")
                    }
                }


            )
        }

    ) {

        Column(
            modifier = Modifier
                .padding(it)
                // .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colors.background)
        ) {

          Row(){
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
              Icon(painter = painterResource(
                  id = R.drawable.baseline_calendar_month_24),
                  contentDescription = "Date", tint = Color.Blue)
          }
          }
            BasicTextField(modifier = Modifier
                .padding(
                    start = 16.dp,
                    top = 16.dp,
                    end = 16.dp,
                )
                .fillMaxWidth(),
                //  .fillMaxHeight(0.3f),
                value = dateState.value,
                onValueChange = { date ->
                    dateState.value = date
                }
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
                    //timeDialogState.show()
                    mTimePickerDialog.show()
                }) {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.baseline_access_time_24
                        ),
                        contentDescription = "Time", tint = Color.Blue
                    )
                }
            }
            BasicTextField(modifier = Modifier
                .padding(
                    start = 16.dp,
                    top = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
                .fillMaxWidth(),
                //   .fillMaxHeight(0.3f),
                value = timeState.value,
                onValueChange = { time ->
                    timeState.value = time
                }
            )
            Text(
                text = "Введите задачу",
                modifier = Modifier.padding(
                    top = 16.dp,
                    start = 24.dp,
                    bottom = 16.dp
                )
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
                }
            )


        }
    }
    MaterialDialog(
        dialogState = dateDialogState,
        properties = DialogProperties(
            dismissOnClickOutside = true
        ),
        buttons = {
            positiveButton(text = "Ok"){
                Toast.makeText(
                    mContext.applicationContext,
                    "Clicked ok",
                    Toast.LENGTH_LONG
                ).show()
            }
            negativeButton(text = "Cancel")
        }
    ) {
        datepicker(
            LocalDate.now(),
            title = "Выберите дату",

        ){
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
            positiveButton(text = "Ok"){
                Toast.makeText(
                    mContext.applicationContext,
                    "Clicked ok",
                    Toast.LENGTH_LONG
                ).show()
            }
            negativeButton(text = "Cancel")
        }
    ) {
        timepicker(
            LocalTime.now(),
            title = "Выберите дату",
            is24HourClock = true,
            timeRange = LocalTime.MIN..LocalTime.MAX
            ){
            pickedTime = it
            timeState.value = formattedTime
        }
    }
}