package com.danil.ogranizertusur.schedule


object ThemeStatusObj {
     var theme: Boolean = false
     fun provide(status:Boolean){
          theme = status
     }
}