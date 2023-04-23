package com.danil.ogranizertusur.schedule

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.*
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.util.*

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabDaysOfWeek() {
    val tex = remember {
        mutableStateOf(listOf<Week>())
    }
    val week: Long by remember {
        mutableStateOf(0)
    }
    val daysList = remember{ daysOfWeekS(week) }

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
    val sdf = remember { SimpleDateFormat("dd.MM")}
    val currentDateAndTime = remember {sdf.format(Date())}
    val maxWeek = remember {54}

    val initPage = initialPage(tabList)

    val pagerState = rememberPagerState(initialPage = initPage)
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()
    val pagerWeekState = rememberPagerState((maxWeek / 2))

    LaunchedEffect(pagerWeekState) {
        snapshotFlow { pagerWeekState.currentPage }.collect {
            if(pagerState.currentPage != initPage) {
                coroutineScope.launch(Dispatchers.Main) {

                    pagerState.animateScrollToPage(0)
                }
            }
            coroutineScope.launch(Dispatchers.Default) {

                val weekId =
                    withContext(Dispatchers.Default) {
                        mutableStateOf(generationWeekId(pagerWeekState.currentPage - maxWeek / 2))
                    }

                weekChanger(pagerWeekState.currentPage.toLong() - maxWeek / 2, tabList)

                    coroutineScope.launch(Dispatchers.IO) {

                        withContext(Dispatchers.Default) {
                            tex.value = listOf()
                        }
                            getData2(tex, weekId.value)
                        Log.d("web", tex.value.size.toString())
                    }
                //}
            }
        }
    }


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
            Row(
                modifier = Modifier.padding(start = 1.dp, bottom = 5.dp)
            ) {
                TabRow(
                    selectedTabIndex = tabIndex,
                    indicator = { pos ->
                        TabRowDefaults.Indicator(
                            Modifier.pagerTabIndicatorOffset(pagerState, pos)
                        )
                    },


                    modifier = Modifier
                        .fillMaxWidth(1.0f)
                        .alpha(0.9f)
                        .clip(shape = RoundedCornerShape(5.dp))
                        /*.border(
                            width = 1.dp,
                            color = Color.White,
                            shape = RoundedCornerShape(50.dp),
                        )*/,
                    backgroundColor = Color.White
                ) {

                    tabList.forEachIndexed { index, text ->
                        if (text.substringAfter(" ") == currentDateAndTime){

                            Tab(
                                selected = true, onClick = {
                                    coroutineScope.launch(Dispatchers.Main) {
                                        pagerState.animateScrollToPage(index)
                                    }


                                },

                                text = {
                                    Text(
                                        text = text,
                                        fontSize = 12.sp,
                                        textAlign = TextAlign.Center,
                                        color = Color.White
                                    )
                                }, modifier = Modifier
                                    .background(
                                        Color(0xFF2B5CA8),
                                        RoundedCornerShape(20.dp)

                                    )
                                    .fillMaxHeight(1f)
                                    .fillMaxWidth()
                            )
                        }
                        else{
                        Tab(
                            selected = true, onClick = {
                                coroutineScope.launch(Dispatchers.Main) {
                                    pagerState.animateScrollToPage(index)
                            }

                            },

                            text = {
                                Text(
                                    text = text,
                                    fontSize = 12.sp,
                                    textAlign = TextAlign.Center,
                                    color = Color.Black
                                )
                            }, modifier = Modifier
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
        HorizontalPager(
            count = tabList.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth(1.0f)
                .fillMaxHeight(1f)

        ) {


          /*  when (it){

                0-> coroutineScope.launch(Dispatchers.Main) {
                    pagerWeekState.animateScrollToPage(pagerWeekState.currentPage-1)
                    pagerState.animateScrollToPage(1)
                }
            }*/



            var week1: Week
            if (tex.value != listOf<Week>()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight(1f)
                        .fillMaxWidth(1.0f)
                ) {


                    itemsIndexed(tex.value)
                    { count, _ ->

                        if (count < 7) {
                            var a = it * 7
                            a += count
                            if (it == 0) {
                                a = count
                            }

                            week1 = tex.value[a]


                            DoublePeriodCard(week1)

                        }
                    }


                }

            } else {
                Text(
                    text = "Ожидаем ответ сервера",
                    fontSize = (60.sp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

fun initialPage(listOf: List<String>): Int {
    val sdf = SimpleDateFormat("dd.MM")
    val currentDateAndTime = sdf.format(Date())
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


/*suspend fun getDoc(week: Int): Document {
    var doca: Document
    try {
        doca =
            Jsoup.connect("https://timetable.tusur.ru/faculties/fvs/groups/532?week_id=$week").get()
        Log.d("Conecting", "Все законектился")
    } catch (e: IOException) {
        e.printStackTrace()
        doca = " " as Document
    }

    return doca
}*/

fun generationWeekId(int: Int): Int {

    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val currentDateAndTime = sdf.format(Date())
    val bgn = sdf.parse("2023-04-03 00:00:00")
    val end = sdf.parse(currentDateAndTime)
    val milliseconds = end!!.time - bgn!!.time
    val week = milliseconds / 1000 / 3600 / 24 / 7

    val startWeekId = 659

    return startWeekId + week.toInt() + int
}

 suspend fun getData2(textt: MutableState<List<Week>>, week: Int) {
   CoroutineScope(Dispatchers.IO).launch {
        try {
            val doca: Document = async {
                Jsoup.connect("https://timetable.tusur.ru/faculties/fvs/groups/552-m?week_id=$week")
                    .get()
            }.await()
            Log.d("Conecting", week.toString())

           // val list =
            withContext(Dispatchers.Default){textt.value =  getData(doca!!)}

                //async {   }.await()

                //list!!
        } catch (e: IOException) {
            e.printStackTrace()

       }
    }

 }

 fun getData(doc: Document): List<Week> {
    val doca = doc
    // var doca = doc as Document
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
    val list = ArrayList<Week>()
    //var day = 2
    var lessonsCellElement: Elements
    Log.d("Starting", "Стартуем")

    for (day in 1 until 7) {
        for (l in 1 until 8) {

            lessonElements = doca.select("tr[class=lesson_$l]")
            lessonsCellElement = lessonElements.select("td[class=lesson_cell  day_$day]")
                .ifEmpty { lessonElements.select("td[class=lesson_cell  day_$day current_day]") }
                .ifEmpty { lessonElements.select("td[class=lesson_cell  day_$day current_day current_lesson ]") }
            timeElement = lessonElements.select("th[class=time]")
                .ifEmpty { lessonElements.select("th[class=time current_time]").eq(0) }


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
            if (numClass.length > 17) {
                numClass = numClass.substring(0, 17)
            }
            list.add(
                Week(
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