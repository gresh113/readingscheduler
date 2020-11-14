package com.github.gresh113.readingscheduler.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.github.gresh113.readingscheduler.R
import scheduler.task.ReadingTask
import scheduler.task.Task

class TaskCardFragment(var task: Task): Fragment() {
    lateinit var title: TextView
    lateinit var numbers: TextView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_task_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title = view.findViewById(R.id.title_text)
        numbers = view.findViewById(R.id.number_text)
    }
}