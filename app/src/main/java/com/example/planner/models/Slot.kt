package com.example.planner.models

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class Slot {
    var name: String = ""
    var event: Event? = null
    var dateStart: Long = 0
    var dateFinish: Long = 0
    var description: String = ""

    @SuppressLint("SimpleDateFormat")
    constructor(dateStart: Long, dateFinish: Long, event: Event?) {
        this.event = event
        this.name = SimpleDateFormat("HH:mm ").format(Date(dateStart))
        this.name += SimpleDateFormat("- HH:mm ").format(Date(dateFinish))
        this.dateStart = dateStart
        this.dateFinish = dateFinish

        if (event != null) {
            this.name += " " + event.name;
        }
    }
}