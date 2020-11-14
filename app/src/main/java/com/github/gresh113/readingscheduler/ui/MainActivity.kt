/*
 * Copyright 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.gresh113.readingscheduler.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.github.gresh113.readingscheduler.CheckBoxClickListener
import com.github.gresh113.readingscheduler.R
import com.github.gresh113.readingscheduler.ReadingScheduler
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.github.gresh113.readingscheduler.ui.component.ComponentFragment
import com.github.gresh113.readingscheduler.ui.fragments.DisplayDatePickerFragment
import com.github.gresh113.readingscheduler.ui.instruction.InstructionsFragment
import com.github.gresh113.readingscheduler.ui.themesummary.ThemeSummaryFragment
import scheduler.assignment.ReadingAssignment
import scheduler.pages.PageSpan
import scheduler.task.ReadingTask
import scheduler.task.Task
import java.time.LocalDate
import java.time.format.TextStyle.FULL_STANDALONE
import java.time.format.TextStyle.SHORT
import java.util.Locale.ENGLISH


/**
 * Single activity which contains the [MainViewPagerAdapter] that shows the [InstructionsFragment],
 * [ThemeSummaryFragment] and [ComponentFragment].
 */
open class MainActivity : AppCompatActivity() {
    private lateinit var fab: FloatingActionButton
    private lateinit var list: LinearLayout
    private lateinit var topBar: MaterialToolbar
    private lateinit var navLeft: MenuItem
    private lateinit var navRight: MenuItem
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        list = findViewById(R.id.list)
        topBar = findViewById(R.id.top_app_bar)
        var topBarMenu = topBar.menu

        navLeft = topBarMenu.findItem(R.id.top_app_bar_menu_navigate_left)
        navRight = topBarMenu.findItem(R.id.top_app_bar_menu_navigate_right)

        topBar.setOnClickListener{
            val newFragment = DisplayDatePickerFragment(this)
            newFragment.show(supportFragmentManager, "datePicker")
        }


        //makeTestAssignment()

        //viewPager.adapter = TaskListAdapterFragment(this.supportFragmentManager, this.lifecycle)
        /*viewPager = findViewById(R.id.view_pager)
        tabLayout = findViewById(R.id.tab_layout)

        tabLayout.setupWithViewPager(viewPager)
        val adapter = MainViewPagerAdapter(this, supportFragmentManager)
        viewPager.adapter = adapter*/

        (application as ReadingScheduler).preferenceRepository
                .nightModeLive.observe(this, Observer { nightMode ->
                    nightMode?.let { delegate.localNightMode = it }
                }
                )
    }

    private fun makeTestAssignment() {
        (this.application as ReadingScheduler).schedule.addAssignment(ReadingAssignment.with(
                PageSpan.fromTo(123, 456),
                LocalDate.now().minusDays(3), LocalDate.now().plusDays(4), "Test"))
    }

    override fun onResume() {
        super.onResume()
        setupFab()
        setupNavButtons()
        loadDateAndTasks()
    }

    fun loadDateAndTasks(){
        val date = (this.application as ReadingScheduler).currentDate
        setTitleToDate(date)
        val schedule = (application as ReadingScheduler).schedule

        //schedule.addTaskToDay(LocalDate.now(), TaskBuilder().called("test").build())
        val tasks = schedule.getTasksForDay(date)
        for (view: View in list.children) list.removeView(view)
        for (task: Task in tasks) run {
            if (task is ReadingTask && !task.isFinished()) list.addView(setupCardForTask(task))
        }
    }

    private fun setupFab() {
        fab = findViewById(R.id.fab)
        fab.setOnClickListener(FabClickListener(this))
    }

    private fun setupNavButtons(){
        navLeft.setOnMenuItemClickListener(NavClickListener(-1, this))
        navRight.setOnMenuItemClickListener(NavClickListener(1, this))
    }

    private class FabClickListener(val main: MainActivity): View.OnClickListener{
        override fun onClick(v: View?) {
            val intent = Intent(main, AssignmentCreateActivity::class.java)
            main.startActivity(intent)
        }

    }
    private fun setTitleToDate(date: LocalDate){
        topBar.title = "${date.dayOfWeek.getDisplayName(FULL_STANDALONE, ENGLISH)}, ${date.month.getDisplayName(SHORT, ENGLISH)}. ${date.dayOfMonth}"
        if (date == LocalDate.now()) topBar.title = "Today"
    }

    private fun setupCardForTask(task: ReadingTask): View{
        val pages = task.pages
        var card = LayoutInflater.from(this).inflate(R.layout.fragment_task_card, null)

        val title = card.findViewById<TextView>(R.id.title_text)
        val number_text = card.findViewById<TextView>(R.id.number_text)
        var checkBox: CheckBox = card.findViewById(R.id.checkbox_finished)
        checkBox.setOnCheckedChangeListener(CheckBoxClickListener(task))

        title.text = task.name
        number_text.text = "Pages ${pages.startPage} - ${pages.endPage}"

        return card
    }

     fun navigate(direction: Byte){
        val app = this.application as ReadingScheduler
        var date: LocalDate = app.currentDate
        date = date.plusDays(direction.toLong())
        app.currentDate = date
        this.loadDateAndTasks()
    }

    fun setDate(date: LocalDate){
        val app = this.application as ReadingScheduler
        app.currentDate = date
    }

    var initialX = 0f
    var initialY:Float = 0f
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event!!.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                initialX = event.x
                initialY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
            }
            MotionEvent.ACTION_UP -> {
                val finalX = event.x
                val finalY = event.y


                val TAG: String = "DEBUG"
                //Log.d(TAG, "Action was UP");
                if (initialX < finalX) {
                    Log.d(TAG, "Left to Right swipe performed");
                }
                if (initialX > finalX) {
                    Log.d(TAG, "Right to Left swipe performed");
                }
                if (initialY < finalY) {
                    Log.d(TAG, "Up to Down swipe performed");
                }
                if (initialY > finalY) {
                    Log.d(TAG, "Down to Up swipe performed");
                }
            }
            MotionEvent.ACTION_CANCEL -> {
            }
            MotionEvent.ACTION_OUTSIDE -> {
            }
        }

        return super.onTouchEvent(event)
    }


}
