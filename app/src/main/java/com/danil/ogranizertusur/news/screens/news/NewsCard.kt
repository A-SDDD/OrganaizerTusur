package com.danil.ogranizertusur.news.screens.news

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.danil.ogranizertusur.news.LinkNews
import com.danil.ogranizertusur.news.NewDataClass
import com.danil.ogranizertusur.ui.theme.LightBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsCard(item: NewDataClass,
onClickNews:()->Unit){

    Card(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(start = 16.dp, end = 16.dp),
        onClick = {
            onClickNews()
            LinkNews.link = item.link
        }
    ) {
        Column(modifier = Modifier.
        fillMaxWidth()) {

        }
        Text(text = item.title, fontSize = 18.sp, modifier = Modifier.padding(start = 4.dp))
        Text(text = item.date, fontSize = 10.sp, color = LightBlue, modifier = Modifier.padding(start = 4.dp))
        Image(
            // on below line we are adding the image url
            // from which we will  be loading our image.
            painter = rememberAsyncImagePainter(item.imageLink),

            // on below line we are adding content
            // description for our image.
            contentDescription = "gfg",

            // on below line we are adding modifier for our
            // image as wrap content for height and width.
            modifier = Modifier
                .width(600.dp)
                .height(275.dp)
        )
        Text(text = item.annotation, fontSize = 12.sp, modifier = Modifier.padding(start = 4.dp))
    }
}