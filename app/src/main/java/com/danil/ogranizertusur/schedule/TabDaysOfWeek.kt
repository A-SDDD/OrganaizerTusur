package com.danil.ogranizertusur

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.danil.ogranizertusur.schedule.Week
import com.danil.ogranizertusur.ui.theme.DoublePeriodCard
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
    var tex = remember {
        mutableStateOf(listOf<Week>())
    }
    val week: Long by remember {
        mutableStateOf(0)
    }
    val daysList = daysOfWeekS(week)

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
    val maxWeek = 54
    val pagerState = rememberPagerState(initialPage = initialPage(tabList))
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()
    val pagerWeekState = rememberPagerState((maxWeek / 2))

    Log.d("web", tex.value.toString())
    LaunchedEffect(pagerWeekState) {
        snapshotFlow { pagerWeekState.currentPage }.collect { page ->

            coroutineScope.launch(Dispatchers.Default) {
                var weekId =
                    mutableStateOf(generationWeekId(pagerWeekState.currentPage - maxWeek / 2))

                weekChanger(pagerWeekState.currentPage.toLong() - maxWeek / 2, tabList)
                coroutineScope.launch(Dispatchers.IO) {
                    getData2(tex, weekId.value)
                    Log.d("web", tex.value.toString())
                }
            }
        }
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->


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
                modifier = Modifier
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
                        .border(
                            width = 1.dp,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(50.dp),
                        ),
                    backgroundColor = Color.LightGray
                ) {

                    tabList.forEachIndexed { index, text ->

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
                                    textAlign = TextAlign.Center
                                )
                            }, modifier = Modifier
                                .background(
                                    if (pagerState.currentPage == index) Color.Gray else Color.LightGray,
                                    RoundedCornerShape(5.dp)
                                )
                                .fillMaxHeight(1f)
                        )
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

        ) { index ->


            var week1: Week

            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight(1f)
                    .fillMaxWidth(1.0f)
            ) {


                itemsIndexed(tex.value)
                { count, item ->

                    if (count < 7) {
                        var a = pagerState.currentPage * 7
                        a = a + count
                        if (pagerState.currentPage == 0) {
                            a = count
                        }
                        week1 = tex.value.get(a)
                        DoublePeriodCard(week1)
                    }
                }

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
    coroutineScope() {
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
    val bgn = sdf.parse("2023-04-03 00:00:01")
    val end = sdf.parse(currentDateAndTime)
    val milliseconds = end.time - bgn.time
    val week = milliseconds / 1000 / 3600 / 24 / 7

    val startWeekId = 659

    return startWeekId - week.toInt() + int
}

fun getData2(textt: MutableState<List<Week>>, week: Int) {
    try {
        val doca: Document
        doca =
            Jsoup.connect("https://timetable.tusur.ru/faculties/fvs/groups/532?week_id=$week").get()
        Log.d("Conecting", week.toString())
        val list = getData(doca)
        textt.value = list
    } catch (e: IOException) {
        e.printStackTrace()

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
    var list = ArrayList<Week>()
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