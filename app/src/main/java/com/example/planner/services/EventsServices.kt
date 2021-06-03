package com.example.planner.services

import com.example.planner.MainActivity
import com.example.planner.models.Event
import io.realm.Realm
import io.realm.RealmConfiguration

class EventsServices {
    private var realm: Realm

    constructor(activity: MainActivity) {
        Realm.init(activity)
        realm = Realm.getDefaultInstance()
        val configuration = RealmConfiguration.Builder()
            .name("Planner.db")
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(configuration)
    }

    fun updateEvent(
        id: Int,
        dateStart: Long,
        dateFinish: Long,
        name: String,
        description: String
    ) {
        val event = Event(
            id,
            dateStart,
            dateFinish,
            name,
            description
        )

        realm.beginTransaction()
        realm.copyToRealmOrUpdate(event)
        realm.commitTransaction()
    }

    fun getEventsBetweenDates(dateStart: Long, dateFinish: Long): Array<Event> {
        return realm.where(Event::class.java)
            .between("dateStart", dateStart, dateFinish)
            .findAll()
            .toTypedArray()
    }

    fun getAllEvents(): Array<Event> {
        return realm.where(Event::class.java).findAll().toTypedArray()
    }
}