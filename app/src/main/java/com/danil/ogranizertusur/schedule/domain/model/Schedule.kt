package com.danil.ogranizertusur.schedule.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Schedule(
    @PrimaryKey val id: Int? = null,
    val faculty: String,
    val group: String
)
