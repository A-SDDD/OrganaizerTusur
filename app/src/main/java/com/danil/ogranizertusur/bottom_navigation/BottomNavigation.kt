package com.danil.ogranizertusur.bottom_navigation

import android.content.res.Resources.Theme
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.danil.ogranizertusur.ui.theme.LightBlue
import com.danil.ogranizertusur.ui.theme.NoteColor
import com.danil.ogranizertusur.ui.theme.OgranizerTusurTheme

@Composable
fun BottomNavigation (navController: NavController) {
    val listItems = listOf(
        BottomItem.Screen1,
        BottomItem.Screen2,
        BottomItem.Screen3,
        BottomItem.Screen4,
        BottomItem.Screen5
    )
    androidx.compose.material.BottomNavigation(
        backgroundColor = MaterialTheme.colors.background
    ) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route
        listItems.forEach{ item->
            BottomNavigationItem(
                selected = currentRoute == item.route,
                onClick = { navController.navigate(item.route) },
                icon = {
                Icon(
                    painter = painterResource(id = item.iconId),
                    contentDescription = "Icon" )
            },
        label = {
            Text(text = item.tittle, fontSize = 9.sp)
        },
                selectedContentColor = Color.Blue,
                unselectedContentColor = LightBlue
        )
        }
    }
}