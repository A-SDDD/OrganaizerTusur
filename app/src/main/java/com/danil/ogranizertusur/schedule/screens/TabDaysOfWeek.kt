package com.danil.ogranizertusur.schedule.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.danil.ogranizertusur.R
import com.danil.ogranizertusur.schedule.ScheduleInfoDataClass
import com.danil.ogranizertusur.ui.theme.LightBlue
import com.google.accompanist.pager.*
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.util.*

@SuppressLint("CoroutineCreationDuringComposition", "UnusedMaterialScaffoldPaddingParameter",
    "SuspiciousIndentation"
)
@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabDaysOfWeek() {

    val scaffoldState = rememberScaffoldState()

    val expandedForDropDownMenu = remember {
        mutableStateOf(false)
    }

    val facultyOrGroups = remember {
        mutableStateOf(1)
    }

    val faculty  = remember {
        mutableStateOf("ф.")
    }
    val group  = remember {
        mutableStateOf("гр.")
    }
    val update = remember {
        mutableStateOf(1)
    }

    val week: Long by remember {
        mutableStateOf(0)
    }

    val scheduleWeek = remember {
        mutableStateOf(listOf<ScheduleInfoDataClass>())
    }
    val groupsByFaculty = remember {
        mutableStateOf(listOf<String>())
    }

    val scheduleDay = scheduleWeek.value.chunked(7)

    val doublePeriodInfo: MutableState<ScheduleInfoDataClass> = remember {
        mutableStateOf(ScheduleInfoDataClass("", "", "", "", "", ""))
    }

    val daysList = remember {
        daysOfWeekS(week)
    }
    val tabList = remember {
        mutableStateListOf(
            "Пн.\n " + daysList[0],
            "Вт.\n " + daysList[1],
            "Ср.\n " + daysList[2],
            "Чт.\n " + daysList[3],
            "Пт.\n " + daysList[4],
            "Сб.\n " + daysList[5]
        )
    }

    val currentDateAndTime = remember {
        DateTimeFormatter.ofPattern(
            "dd.MM",
            Locale.getDefault()
        ).format(LocalDateTime.now())
    }
    val maxWeek = remember { 54 }

    val initPage = initialPage(tabList)

    val pagerState = rememberPagerState(initialPage = initPage)
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()
    val pagerWeekState = rememberPagerState((maxWeek / 2))

    if(faculty.value != "ф." && facultyOrGroups.value == 2)
    coroutineScope.launch(Dispatchers.IO) {
        groupsByFaculty.value = listOf()
        getGroupsByFaculties(faculty,groupsByFaculty)
    }

    if (update.value == 2){
        coroutineScope.launch(Dispatchers.IO) {
            val weekId =
                mutableStateOf(generationWeekId(pagerWeekState.currentPage - maxWeek / 2))
            scheduleWeek.value= listOf()
            getData2(scheduleWeek, weekId.value, faculty, group.value)
            update.value = 1
        }
    }

    LaunchedEffect(pagerWeekState.currentPage) {
        try {
            snapshotFlow { pagerWeekState }.collect {
                if (pagerState.currentPage != initPage) {
                    coroutineScope.launch(Dispatchers.Main) {
                        pagerState.animateScrollToPage(0)
                    }
                }

                val weekId =
                    mutableStateOf(generationWeekId(pagerWeekState.currentPage - maxWeek / 2))
                coroutineScope.launch(Dispatchers.Default) {

                    weekChanger(pagerWeekState.currentPage.toLong() - maxWeek / 2, tabList)

                    coroutineScope.launch(Dispatchers.IO) {

                        withContext(Dispatchers.Default) {
                            scheduleWeek.value = listOf()
                        }

                        if(faculty.value != "ф."
                            && group.value !="гр."
                        ){
                            getData2(scheduleWeek, weekId.value, faculty, group.value)
                        }


                    }
                    //}
                }
            }
        } catch (ex: Exception) {
            println("d")
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Расписание", fontSize = 14.sp)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.055f),
                actions = {
                    Row {
                        DropDownMenu(
                            expanded = expandedForDropDownMenu,
                            faculty,
                            groupsByFaculty,
                            facultyOrGroups,
                            group,
                            update
                        )
                        Button(
                            onClick = {
                                expandedForDropDownMenu.value = !expandedForDropDownMenu.value
                            },
                            colors = ButtonDefaults.buttonColors(Color.White),
                            modifier = Modifier
                                .fillMaxHeight(0.9f)
                                .padding(
                                    top = 2.dp,
                                    end = 2.dp
                                )
                        ) {
                            Text(
                                "${faculty.value} и ${group.value}",
                                color = LightBlue,
                                fontSize = 12.sp
                            )
                        }
                        Button(

                            onClick = {
                                coroutineScope.launch(Dispatchers.Main) {
                                    var a = 0
                                    a = async {
                                        pagerWeekState.animateScrollToPage(54 / 2)
                                        return@async pagerWeekState.currentPage
                                    }.await()
                                    if (a == 54/2) {
                                        pagerState.animateScrollToPage(initialPage(tabList))
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(Color.White),
                            modifier = Modifier
                                .fillMaxHeight(0.9f)
                                .padding(top = 2.dp)
                        ) {
                            Text(
                                "К текущему дню",
                                color = LightBlue,
                                fontSize = 12.sp
                            )
                        }
                    }
                },
                backgroundColor = Color.White,
                contentColor = LightBlue
            )
        },
        bottomBar = {
            BottomAppBar {
            }
        }
    ) {
        Image(
            painter = painterResource(id = R.drawable.fon),
            contentDescription = "fon1",
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.7f),
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier
                .fillMaxWidth(1f)
                .clip(RoundedCornerShape(10.dp))
                .padding(start = 1.dp)
        ) {
            HorizontalPager(
                count = maxWeek,
                state = pagerWeekState,
                modifier = Modifier
                    .fillMaxHeight(0.1f)
                    .fillMaxWidth(1f)
            ) {

                Column(
                    modifier = Modifier.padding(start = 2.dp, top = 2.dp,  end = 1.dp)
                ) {
                    TabRow(
                        selectedTabIndex = tabIndex,
                        indicator = { pos ->
                        },
                        modifier = Modifier
                            .fillMaxWidth(1.0f)
                            .alpha(0.9f)
                            .clip(shape = RoundedCornerShape(5.dp)),
                        backgroundColor = Color.White,
                        divider = { Divider(color = LightBlue, startIndent = 2.dp)},
                    ) {
                        tabList.forEachIndexed { index, text ->

                            when (text.substringAfter(" ")) {
                                currentDateAndTime -> {
                                    Tab(
                                        selected = true, onClick = {
                                            coroutineScope.launch(Dispatchers.Main) {
                                                pagerState.animateScrollToPage(index)
                                            }


                                        },

                                        text = {
                                            Column(horizontalAlignment = CenterHorizontally) {
                                                Text(
                                                    text = text.substringBefore("\n"),
                                                    fontSize = 16.sp,
                                                    textAlign = TextAlign.Center,
                                                    color = Color.White
                                                )
                                                Text(
                                                    text = text.substringAfter(" "),
                                                    fontSize = 10.sp,
                                                    textAlign = TextAlign.Center,
                                                    color = Color.White,
                                                    modifier = Modifier.padding(top = 2.dp)
                                                )
                                            }
                                        }, modifier = Modifier
                                            .border(
                                                width = 2.dp,
                                                color = LightBlue,
                                                shape = RoundedCornerShape(20.dp)
                                            )
                                            .background(
                                                if (pagerState.currentPage == index) Color.Blue
                                                else LightBlue,
                                                RoundedCornerShape(20.dp)
                                            )
                                            .fillMaxHeight(1f)
                                            .fillMaxWidth()
                                    )
                                }

                                else -> {
                                    Tab(
                                        selected = true, onClick = {
                                            coroutineScope.launch(Dispatchers.Main) {
                                                pagerState.animateScrollToPage(index)
                                            }

                                        },

                                        text = {
                                            Column(horizontalAlignment = CenterHorizontally) {
                                                Text(
                                                    text = text.substringBefore("\n"),
                                                    fontSize = 16.sp,
                                                    textAlign = TextAlign.Center,
                                                    color = Color.Black
                                                )
                                                Text(
                                                    text = text.substringAfter(" "),
                                                    fontSize = 10.sp,
                                                    textAlign = TextAlign.Center,
                                                    color = Color.Black,
                                                    modifier = Modifier.padding(top = 2.dp)
                                                )
                                            }

                                        }, modifier = Modifier
                                            .border(
                                                width = 1.dp,
                                                color = LightBlue,
                                                shape = RoundedCornerShape(20.dp)
                                            )
                                            .background(
                                                if (pagerState.currentPage == index) Color.Gray else Color.LightGray,
                                                RoundedCornerShape(20.dp)

                                            )
                                            .fillMaxHeight(1f)
                                    )

                                }

                            }
                        }

                    }
                }
            }
            HorizontalPager(
                count = tabList.size,
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth(1.0f)
                    .fillMaxHeight(1f)

            ) { ara ->


                if (scheduleWeek.value != listOf<ScheduleInfoDataClass>()) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxHeight(1f)
                            .fillMaxWidth(1.0f)
                    ) {


                        itemsIndexed(scheduleDay[ara])
                        { _, text ->

                            doublePeriodInfo.value = text
                            DoublePeriodCard(doublePeriodInfo.value)
                        }


                    }

                } else {
                    if(faculty.value == "ф." || group.value =="гр."){
                        Text(
                            text = "Выберите факультет и группу",
                            fontSize = (60.sp),
                            textAlign = TextAlign.Center
                        )
                    } else{Text(
                        text = "Ожидаем ответ сервера",
                        fontSize = (60.sp),
                        textAlign = TextAlign.Center
                    )
                    }
                }
            }
        }
    }
}

fun initialPage(listOf: List<String>): Int {
    /*/val sdf = SimpleDateFormat("dd.MM")
    val currentDateAndTime = sdf.format(Date())*/
    val currentDateAndTime = DateTimeFormatter.ofPattern(
        "dd.MM",
        Locale.getDefault()
    ).format(LocalDateTime.now())
    var a = 5
    listOf.forEachIndexed { index, text ->
        if (text.substringAfter(" ") == currentDateAndTime) a = index
    }
    return a
}

fun daysOfWeekS(first: Long = 0): List<String> {
    val toDDay = LocalDate.now()
    val week: List<String>
    val firstDayOfWeek = toDDay.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))


    week = listOf(
        firstDayOfWeek.plusDays(first * 7).format(DateTimeFormatter.ofPattern("dd.MM")),
        firstDayOfWeek.plusDays(1 + (first * 7)).format(DateTimeFormatter.ofPattern("dd.MM")),
        firstDayOfWeek.plusDays(2 + (first * 7)).format(DateTimeFormatter.ofPattern("dd.MM")),
        firstDayOfWeek.plusDays(3 + (first * 7)).format(DateTimeFormatter.ofPattern("dd.MM")),
        firstDayOfWeek.plusDays(4 + (first * 7)).format(DateTimeFormatter.ofPattern("dd.MM")),
        firstDayOfWeek.plusDays(5 + (first * 7)).format(DateTimeFormatter.ofPattern("dd.MM")),
    )

    return week
}

//function for changing the date on the top tabs of the schedule page
suspend fun weekChanger(first: Long, tabList: SnapshotStateList<String>) {
    coroutineScope {
        val list = listOf("Пн.", "Вт.", "Ср.", "Чт.", "Пт.", "Сб.")
        Log.d("Изменение недели", "ДА да я работаю!")
        launch {
            for (i in 0 until 6) {
                tabList.set(i, list[i] + "\n " + daysOfWeekS(first)[i])
            }
        }
    }
}

//function for generation week id for link that we want to parse
fun generationWeekId(int: Int): Int {

    /*val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val currentDateAndTime = sdf.format(Date())*/
    val sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val currentDateAndTime = LocalDateTime.now()
    val localDate = LocalDateTime.parse("2023-04-03 00:00:00", sdf)
    val bgn = localDate.atOffset(ZoneOffset.UTC).toInstant().toEpochMilli()
    val end = currentDateAndTime.atOffset(ZoneOffset.UTC).toInstant().toEpochMilli()
    //val bgn = sdf.parse("2023-04-03 00:00:00")
    //val end = sdf.parse(currentDateAndTime)
    //val milliseconds = end!!.time - bgn!!.time
    val milliseconds = end - bgn
    val week = milliseconds / 1000 / 3600 / 24 / 7

    val startWeekId = 659

    return startWeekId + week.toInt() + int
}

//function for getting html doc from Tusur.ru
suspend fun getData2(scheduleWeek: MutableState<List<ScheduleInfoDataClass>>, week: Int, faculty:MutableState<String>, group: String ) {

    val retranslator = group
        .replace("з", "z")
        .replace("в","v")
        .replace("С","s")
        .replace("Т","t")
        .replace("У","u")
        .replace("П","p")
        .replace("Э","e")
        .replace("А","a")
        .replace("Б","b")
        .replace("Р","r")
        .replace("М","m")
        .replace("инд","ind")
    Log.d("Группа",retranslator)
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val doc: Document = async {
                Jsoup.connect("https://timetable.tusur.ru/faculties/${faculty.value}/groups/$retranslator?week_id=$week")
                    .get()
            }.await()
            Log.d("Conecting", week.toString())

            // val list =
            withContext(Dispatchers.Default) { scheduleWeek.value = getData(doc) }

            //async {   }.await()

            //list!!
        } catch (e: IOException) {
            e.printStackTrace()

        }
    }

}

//function for parsing schedule page from Tusur.ru
fun getData(doc: Document): List<ScheduleInfoDataClass> {
    var lessonElements: Elements
    //time
    var timeElement: Elements
    var timeBegin: String?
    var timeEnd: String?

    //objects
    var objElement: Elements
    var obj: String?
    //type of object
    var objTypeElement: Elements
    var objType: String?
    //prepods
    var prepodElement: Elements
    var prepod: String?
    //numClasses
    var numClass: String?
    var numClassElements: Elements
    //list
    val list = ArrayList<ScheduleInfoDataClass>()
    //var day = 2
    var lessonsCellElement: Elements
    Log.d("Starting", "Стартуем")

    for (day in 1 until 7) {
        for (l in 1 until 8) {

            lessonElements = doc.select("tr[class=lesson_$l]")
            lessonsCellElement = lessonElements.select("td[class=lesson_cell  day_$day]")
                .ifEmpty { lessonElements.select("td[class=lesson_cell  day_$day current_day]") }
                .ifEmpty { lessonElements.select("td[class=lesson_cell  day_$day current_day current_lesson ]") }
            timeElement = lessonElements.select("th[class=time]")
                .ifEmpty { lessonElements.select("th[class=time current_time]").eq(0) }

            //beginning and ending time of double period(lesson)
            timeBegin = timeElement.eq(0).select("span").eq(0).text()
            timeEnd = timeElement.eq(0).select("span").eq(1).text()

            objElement = lessonsCellElement.select("div[class=lesson-cell]")
                .select("div[class=hidden for_print]").eq(0)
            obj = objElement.eq(0).select("span[class=discipline]").eq(0).text()

            objTypeElement = lessonsCellElement.select("div[class=lesson-cell]")
                .select("div[class=hidden for_print]").eq(0)
            objType = objTypeElement.eq(0).select("span[class=kind]").eq(0).text()

            prepodElement = lessonsCellElement.select("div[class=lesson-cell]")
                .select("div[class=hidden for_print]").eq(0)
            prepod = prepodElement.eq(0).select("span[class=group]").eq(0).text()
            if (prepod.length > 40) {
                prepod = prepod.substring(0, 40)
            }

            numClassElements =
                lessonsCellElement.select("div[class=lesson-cell]")
                    .select("div[class=hidden for_print]").eq(0)
            numClass = numClassElements.eq(0).select("span[class=auditoriums]").eq(0).text()
            if (numClass.length > 27) {
                numClass = numClass.substring(0, 27)
            }
            list.add(
                ScheduleInfoDataClass(
                    timeBegin,
                    timeEnd,
                    obj,
                    objType,
                    prepod,
                    numClass
                )
            )
        }
    }
    return list
}
suspend fun getGroupsByFaculties(faculty:MutableState<String>,groupsByFaculty:MutableState<List<String>>){
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val doc: Document = async {
                Jsoup.connect("https://timetable.tusur.ru/faculties/${faculty.value}")
                    .get()
            }.await()
            withContext(Dispatchers.Default) { groupsByFaculty.value = getGroupsParsing(doc) }
        } catch (e: IOException) {
            e.printStackTrace()

        }
    }


}
fun getGroupsParsing(doc: Document):List<String>{

    val list = ArrayList<String>()

    val lessonElements: Elements = doc.select("ul[class=list-inline]").select("li")

    for (i in 0 until lessonElements.size){
        list.add(lessonElements.eq(i).text())
    }
    Log.d(" chek", list.toString())
    return list

}