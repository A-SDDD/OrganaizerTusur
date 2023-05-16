package com.danil.ogranizertusur.bottom_navigation

import com.danil.ogranizertusur.R

sealed class BottomItem(val tittle: String, val iconId: Int, val route: String){
    object Screen1: BottomItem("Расписание", R.drawable.baseline_today_24, "schedule")
    object Screen2: BottomItem("Планировщик", R.drawable.baseline_checklist_24, "workspace")
    object Screen3: BottomItem("Дедлайны", R.drawable.baseline_menu_book_24, "deadline")
    object Screen4: BottomItem("Новости", R.drawable.baseline_newspaper_24, "news")
}
