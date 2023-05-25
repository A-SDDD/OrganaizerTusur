package com.danil.ogranizertusur.news.screens.calendar_events

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.danil.ogranizertusur.news.EventsDataClass
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException
import java.util.ArrayList

@SuppressLint("CoroutineCreationDuringComposition", "SuspiciousIndentation")
@Composable
fun EventsPager(onClickEvent:()->Unit) {
    val eventItems = remember {
        mutableStateOf(listOf<EventsDataClass>())
    }
    rememberCoroutineScope().launch(Dispatchers.IO) {
       getEvents(eventItems)
    }

  //  if(eventItems.value!=listOf<EventsDataClass>())
   // EventCard(modifier1 = Modifier, item = eventItems.value[0],onClickEvent)


        Column(modifier = Modifier.verticalScroll(rememberScrollState(0))) {
            eventItems.value.forEachIndexed{_,item->
                Column() {
                    Divider(color = Color.LightGray,thickness = 1.dp)
                    EventCard(
                        item = item,
                        onClickEvent = onClickEvent
                    )
                }
            }
        }


        LazyColumn(
            modifier = Modifier
        )
        {
            itemsIndexed(eventItems.value) { int, item ->

            }
        }
}

private suspend fun getEvents(eventsList: MutableState<List<EventsDataClass>>) {
        var eList:List<EventsDataClass> = listOf()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                for (i in 1 until 4){
                val doc = async {
                        Jsoup.connect("https://tusur.ru/ru/novosti-i-meropriyatiya/anonsy-meropriyatiy?page=$i")
                            .get()
                }.await()
                     eList = eList + parseEvent(doc)
            }
                eventsList.value= eList
                Log.d("work",eventsList.value.size.toString())
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
}

fun parseEvent(doc: Document): List<EventsDataClass> {
    val list = ArrayList<EventsDataClass>()
    var dateStart: String
    var monthStart: String
    var dash:String
    var dateEnd: String
    var monthEnd: String
    var year: String
    var title: String
    var location: String
    var link: String

    val eventElements: Elements = doc.select("tr")
    for (i in 0 until eventElements.size){
        dateStart = eventElements.eq(i)
            .select("td[class=date]").eq(0)
            .select("span[class=dates-range]").eq(0).text()

        dateEnd = eventElements.eq(i)
            .select("td[class=date]").eq(0)
            .select("span[class=dates-range]").eq(1).text()

        monthStart= eventElements.eq(i)
            .select("td[class=date]").eq(0)
            .select("span[class=short-month]").eq(0).text().ifEmpty {eventElements.eq(i)
                .select("td[class=date]").eq(0)
                .select("span[class=one-month]").eq(0).text()}

        monthEnd =  eventElements.eq(i)
            .select("td[class=date]").eq(0)
            .select("span[class=short-month]").eq(1).text()

        dash = eventElements.eq(i)
            .select("td[class=date]").eq(0)
            .select("span[class=dash]").eq(0).text()
        year = eventElements.eq(i)
            .select("td[class=date]").eq(0)
            .select("span[class=year]").eq(0).text()

        title =  eventElements.eq(i)
            .select("td").eq(1)
            .select("a").eq(0).text()
        location = eventElements.eq(i)
            .select("td").eq(1)
            .select("div[class=location]").eq(0).text()
        link = eventElements.eq(i)
            .select("td").eq(1)
            .select("a").eq(0).attr("href")
        if(dateStart!="") {
            list.add(
                EventsDataClass(
                    dateStart,
                    monthStart,
                    dash,
                    dateEnd,
                    monthEnd,
                    year,
                    title,
                    location,
                    link
                )
            )
        }
    }
    return list
}
