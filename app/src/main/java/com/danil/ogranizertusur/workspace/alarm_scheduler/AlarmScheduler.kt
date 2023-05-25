package com.danil.ogranizertusur.workspace.alarm_scheduler

interface AlarmScheduler {
    fun schedule (item: AlarmItem)
    fun cancel (item: AlarmItem)
}