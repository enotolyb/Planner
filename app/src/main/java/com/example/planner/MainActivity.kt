package com.example.planner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.EventDay
import com.example.planner.models.Event
import com.example.planner.models.Slot
import com.example.planner.services.EventsServices
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private val milsInHour = 1000 * 60 * 60
    private lateinit var calendarView: CalendarView
    private lateinit var recyclerView: RecyclerView
    lateinit var eventsServices: EventsServices

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        eventsServices = EventsServices(this)

        calendarView = findViewById(R.id.calendarView)
        recyclerView = findViewById(R.id.recView)
        calendarView.setOnDayClickListener { eventDay: EventDay -> this.onDayClick(eventDay) }

        eventsServices.updateEvent(
            1,
            1625050800000,
            1625054400000,
            "Занятия",
            "Описание"
        )
        eventsServices.updateEvent(
            2,
            1623470400000,
            1623474000000,
            "Занятия",
            "Описание"
        )
        eventsServices.updateEvent(
            3,
            1623510000000,
            1623513600000,
            "Занятия",
            "Описание"
        )
        eventsServices.updateEvent(
            4,
            1624086000000,
            1624089600000,
            "Занятия",
            "Описание"
        )

    }

    override fun onResume() {
        super.onResume()
        recyclerView.isNestedScrollingEnabled = false
        recyclerView.layoutManager = LinearLayoutManager(this)
        addAllEventsToCalendar()
    }


    private fun addAllEventsToCalendar() {
        val events = eventsServices.getAllEvents()
        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        val calendarEvents = ArrayList<EventDay>()
        for (i in events) {
            val calendar: Calendar = Calendar.getInstance()
            calendar.timeInMillis = i.dateStart
            calendarEvents.add(EventDay(calendar, R.drawable.ic_circle))
        }
        calendarView.setEvents(calendarEvents)
    }

    private fun onDayClick(eventDay: EventDay) {
        val dateStart = eventDay.calendar.timeInMillis
        val dateFinish = eventDay.calendar.timeInMillis + (milsInHour * 24 - 1000)
        val events = eventsServices.getEventsBetweenDates(
            dateStart,
            dateFinish
        )
        val adapter = CustomRecyclerAdapter(
            generateSlots(eventDay.calendar.timeInMillis, events)
        )

        recyclerView.adapter = adapter
    }


    private fun generateSlots(currentDay: Long, events: Array<Event>): List<Slot> {
        val values = mutableListOf<Slot>()
        for (i in 0..23) {
            val dateStart = currentDay + (i * milsInHour)
            val dateFinish = dateStart + milsInHour
            val event = events.find { it.dateStart == dateStart }
            Log.d("xxx events", "$event $dateStart")
            values.add(
                Slot(
                    dateStart,
                    dateFinish,
                    event
                )
            )
        }
        return values
    }
}