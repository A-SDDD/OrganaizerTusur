package com.danil.ogranizertusur.news.screens.news

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.danil.ogranizertusur.news.LinkNews
import com.danil.ogranizertusur.news.NewDataClass
import com.danil.ogranizertusur.ui.theme.LightBlue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsInfo(onClickClose: () -> Unit) {
    val newsInfo = remember {
        mutableStateOf(
            NewDataClass(
                "",
                "",
                "",
                "",
                ""
            )
        )
    }
    LaunchedEffect(key1 = "ds", block ={ getNews(newsInfo) } )
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { androidx.compose.material.Text(text = "Новость", fontSize = 24.sp) },
                navigationIcon = {
                IconButton(onClick = onClickClose) {
                    Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "Close")
                }
            }
            )
        }
        ,
        bottomBar = {
            BottomAppBar {
            }
        }
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(
                    rememberScrollState(0)
                )
                .padding(it)
        ) {
            Text(
                text = newsInfo.value.title,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(start = 6.dp,end = 6.dp)
            )
            Text(
                text = newsInfo.value.date,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(start = 36.dp,end = 6.dp)
            )
            Image(
                // on below line we are adding the image url
                // from which we will  be loading our image.
                painter = rememberAsyncImagePainter(newsInfo.value.imageLink),

                // on below line we are adding content
                // description for our image.
                contentDescription = "gfg imageeqr ",

                // on below line we are adding modifier for our
                // image as wrap content for height and width.
                modifier = Modifier
                    .width(410.dp)
                    .height(250.dp)
                    .padding(start = 16.dp, end = 16.dp)
                    .clip(RoundedCornerShape(percent = 10))
                    .border(2.dp, LightBlue, RoundedCornerShape(percent = 10))

            )
            Text(
                text = newsInfo.value.annotation,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(start = 6.dp,end = 6.dp)
            )
        }
    }


}

fun getNews( newsInfo: MutableState<NewDataClass>) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val doc: Document = async { Jsoup.connect("https://tusur.ru${LinkNews.link}").get() }.await()
            launch { newsInfo.value = parseNews(doc) }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}

private fun parseNews(doc:Document):NewDataClass {
    val newsElement= doc.select("div[class=content index]").eq(0)
    val newsTitle = newsElement.select("h1").text()
    val newsDate = newsElement.select("div[class=date margin-bottom-0]").text()

    val imageLink = newsElement.select("img[class=preview-image img-responsive]").attr("src")

    val abzAnnotationElement = newsElement.select("p")
    var abzAnnotation = ""

    for (i in 0 until abzAnnotationElement.size) {
        if (abzAnnotation == "") {
            abzAnnotation = "$abzAnnotation\n${abzAnnotationElement.eq(i).text()}"
        } else {
            abzAnnotation = "$abzAnnotation\n\n ${abzAnnotationElement.eq(i).text()}"
        }
    }

    Log.d("Абзац",imageLink)
    return  NewDataClass(newsTitle,newsDate,imageLink,abzAnnotation,"")
}