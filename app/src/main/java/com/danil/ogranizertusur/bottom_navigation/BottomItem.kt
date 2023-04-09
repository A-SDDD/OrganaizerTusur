package com.danil.ogranizertusur.bottom_navigation

import com.danil.ogranizertusur.R

sealed class BottomItem(val tittle: String, val iconId: Int, val route: String){
    object Screen1: BottomItem("Рассписание", R.drawable.baseline_today_24, "screen_1")
    object Screen2: BottomItem("Планировщик", R.drawable.baseline_checklist_24, "screen_2")
    object Screen3: BottomItem("Дедлайны", R.drawable.baseline_menu_book_24, "screen_3")
    object Screen4: BottomItem("Новости", R.drawable.baseline_newspaper_24, "screen_4")
}
