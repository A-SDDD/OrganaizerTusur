package com.danil.ogranizertusur.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.danil.ogranizertusur.schedule.Week


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun DoublePeriodCard(item: Week) {
    val h = 600 / 7
    Card(
        modifier = Modifier
            //.width(1f)
            .fillMaxWidth(1f)
            .fillMaxHeight(0.125f)
            .alpha(0.9f)
            .padding(top = 3.dp),
        shape = RoundedCornerShape(5.dp),
        elevation = 0.dp,
        backgroundColor = Color.DarkGray
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight(1f)
                .padding(top = 5.dp, start = 5.dp, end = 5.dp), Arrangement.SpaceBetween
        ) {
            Column (modifier = Modifier.fillMaxHeight(1f)){
                Text(text = item.timeBegin.toString().ifEmpty { "" }, style = TextStyle(Color.White))
                Text(text = item.timeEnd.toString().ifEmpty { "" }, style = TextStyle(Color.White))
            }
            Column( horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.wrapContentSize(Alignment.Center)) {

                    Text(
                        text = item.obj.toString().ifEmpty { "" },
                        textAlign = TextAlign.Center,
                        style = TextStyle(Color.White)
                    )
                    Text(
                        text = item.objType.toString().ifEmpty { "" },
                        textAlign = TextAlign.Center,

                        style = TextStyle(Color.White),
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    Text(
                        text = item.prepod.toString().ifEmpty { "" },
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(Color.White)
                    )

            }

            Text(text = item.numClass.toString().ifEmpty { "" }, style = TextStyle(Color.White))
        }

    }

}