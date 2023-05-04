package com.danil.ogranizertusur.news.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.danil.ogranizertusur.news.NewDataClass
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException
import java.util.ArrayList

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(onClickNews: (NewDataClass)->Unit) {
    val newsItems = remember {
        mutableStateOf(listOf<NewDataClass>())
    }
    rememberCoroutineScope().launch(Dispatchers.IO) {
        getNews(newsItems)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                   Text( "Новости")
                }
            )
        },
        bottomBar = {
            BottomAppBar(modifier = Modifier.fillMaxHeight(0.08f)) {}
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            LazyColumn(
                modifier = Modifier

            )
            {
                itemsIndexed(newsItems.value) { int, item ->
                    NewsCard(
                        item = item,
                        onClickNews=onClickNews
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}
suspend fun getNews(newsList: MutableState<List<NewDataClass>>) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val doc = async {
                Jsoup.connect("https://tusur.ru/ru/novosti-i-meropriyatiya/novosti")
                    .get()
            }.await()
            newsList.value = parseNews(doc)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}

private fun parseNews(doc:Document):List<NewDataClass>{

    val list = ArrayList<NewDataClass>()

    var title:String
    var date:String
    var imageLink:String
    var annotation:String
    var link:String

    val newsElements: Elements = doc.select("div[class=news-page-list-item]")


    for (i in 0 until newsElements.size){
        title = newsElements.eq(i).select("a").eq(0).text()
        link = newsElements.eq(i).select("a").eq(0).attr("href")
        date = newsElements.select("div[class=news-page-list-item-since]").eq(i).text()
        imageLink = newsElements.select("img[class=img-responsive]").eq(i).attr("src")
        annotation = newsElements.select("div[class=news-page-list-item-annotation]").eq(i).text()
        list.add(
            NewDataClass(
                title,
                date,
                imageLink,
                annotation,
                link
            )
        )
    }
    Log.d(" chek", list.toString())
    return list

}