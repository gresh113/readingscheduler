package com.github.gresh113.readingscheduler.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.github.gresh113.readingscheduler.R
import com.github.gresh113.readingscheduler.ReadingScheduler
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.github.gresh113.readingscheduler.ui.fragments.DatePickerFragment
import scheduler.assignment.ReadingAssignment
import scheduler.pages.PageSpan
import java.time.LocalDate

class AssignmentCreateActivity: AppCompatActivity() {
    private lateinit var nameEntry: TextInputEditText
    private lateinit var startPage: TextInputEditText
    private lateinit var endPage: TextInputEditText
    private lateinit var startDatePicker: TextInputEditText
    private lateinit var endDatePicker: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.assignment_create)

        nameEntry = findViewById(R.id.text_input_name_edit)
        startPage = findViewById(R.id.text_input_pagestart_edit)
        endPage = findViewById(R.id.text_input_pageend_edit)

        startDatePicker = findViewById(R.id.text_input_datestart_edit)
        endDatePicker = findViewById(R.id.text_input_dateend_edit)

        /*viewPager = findViewById(R.id.view_pager)
        tabLayout = findViewById(R.id.tab_layout)

        tabLayout.setupWithViewPager(viewPager)
        val adapter = MainViewPagerAdapter(this, supportFragmentManager)
        viewPager.adapter = adapter*/
    }

    fun showDatePickerDialogEnd(view:View) {
        val newFragment = DatePickerFragment(endDatePicker)
        newFragment.show(supportFragmentManager, "datePicker")
    }

    fun showDatePickerDialogStart(view: View) {
        val newFragment = DatePickerFragment(startDatePicker)
        newFragment.show(supportFragmentManager, "datePicker")
    }

    fun addAssignment(view:View) {
        if (checkAllFieldFilled()) {
            println("all filled!")
            val span = PageSpan.fromTo(startPage.text.toString().toInt(), endPage.text.toString().toInt())
            val startDate = LocalDate.parse(startDatePicker.text.toString())
            val endDate = LocalDate.parse(endDatePicker.text.toString())
            val name = nameEntry.text.toString()
            val assignment = ReadingAssignment.with(span, startDate, endDate, name)
            val schedule = (this.application as ReadingScheduler).schedule
            schedule.addAssignment(assignment)
            (this.application as ReadingScheduler).schedule = schedule
            finish()
        }
    }

    private fun checkAllFieldFilled(): Boolean {
        val contextView = findViewById<View>(R.id.button_submit)

        return if (nameEntry.text.toString() == ""
                || startPage.text.toString() == ""
                || endPage.text.toString() == ""
                || startDatePicker.text.toString() == ""
                || endDatePicker.text.toString() == ""
        ) {
            Snackbar.make(contextView, R.string.empty_fields, Snackbar.LENGTH_SHORT).show()
            false
        } else true
    }
}