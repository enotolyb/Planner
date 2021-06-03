package com.example.planner.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class Event(
    @PrimaryKey
    var id: Int = 0,
    var dateStart: Long = 0,
    var dateFinish: Long = 0,
    var name: String = "",
    var description: String = ""
): RealmObject()
