package com.danil.ogranizertusur.news.screens.calendar_events

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.danil.ogranizertusur.news.EventsDataClass
import com.danil.ogranizertusur.news.LinkNews
import com.danil.ogranizertusur.ui.theme.GrayForEvent
import com.danil.ogranizertusur.ui.theme.LightBlue

@Composable
fun EventCard(
    item: EventsDataClass,
    onClickEvent: () -> Unit,
) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .clickable {
                    LinkNews.link = item.link
                    onClickEvent()
                },
            colors = CardDefaults.cardColors(Color.White),
            shape = RoundedCornerShape(0.dp)
        ) {
            Row(modifier = Modifier.height(IntrinsicSize.Min),
                verticalAlignment = Alignment.CenterVertically){
                Column(modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .background(GrayForEvent)
                    .align(Alignment.CenterVertically),
                ) {
                    Row(
                        modifier = Modifier
                        .padding(start = 4.dp, top = 12.dp)
                    ){
                        Text(
                            text = item.dateStart,
                            fontSize = 20.sp
                        )
                        Text(
                            text = " ${item.monthStart} ",
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                        Text(
                            text = " ${item.dash} ",
                            modifier = Modifier.padding(top = 6.dp)
                        )
                        Text(
                            text = item.dateEnd,
                            fontSize = 20.sp
                        )
                        Text(
                            text = " ${item.monthEnd}",
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }

                    Text(
                        text = item.year,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                        .padding(start = 4.dp, top = 2.dp)
                    )

                }
                Divider(
                    color = Color.LightGray,
                    modifier = Modifier
                        .fillMaxHeight()  //fill the max height
                        .width(2.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(2f)
                        .background(MaterialTheme.colors.surface)
                ) {
                    Text(
                        text = item.title,
                        modifier = Modifier
                            .padding(start = 4.dp, top = 12.dp),
                        color = LightBlue)
                    Text(
                        text = item.location,
                        modifier = Modifier
                            .padding(start = 4.dp, top = 4.dp),
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }
            }
        }
}
