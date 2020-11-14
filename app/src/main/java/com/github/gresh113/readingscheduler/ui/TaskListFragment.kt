package com.github.gresh113.readingscheduler.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.github.gresh113.readingscheduler.R
import com.github.gresh113.readingscheduler.ReadingScheduler
import scheduler.pages.PageSpan
import scheduler.task.ReadingTask
import scheduler.task.Task
import tasks.taskbuilders.ReadingTaskBuilder
import java.time.LocalDate

class TaskListFragment(val date: LocalDate): Fragment() {
    private lateinit var list: LinearLayout
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_task_card_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val app = this.activity?.application as ReadingScheduler
        list = view.findViewById(R.id.list)
        val schedule = app.schedule
        val tasks = schedule.getTasksForDay(date)
        tasks.add(ReadingTaskBuilder().withPages(PageSpan.fromTo(123,456)).called("test").build())
        for (view: View in list.children) list.removeView(view)
        for (task: Task in tasks) run {
            if (task is ReadingTask) list.addView(setupCardForTask(task))
        }
    }

    private fun setupCardForTask(task: ReadingTask): View{
        val pages = task.pages
        var card = LayoutInflater.from(this.activity).inflate(R.layout.fragment_task_card, null)

        val title = card.findViewById<TextView>(R.id.title_text)
        val number_text = card.findViewById<TextView>(R.id.number_text)
        var checkBox: CheckBox = card.findViewById(R.id.checkbox_finished)

        title.text = task.name
        number_text.text = "Pages ${pages.startPage} - ${pages.endPage}"

        return card
    }
}