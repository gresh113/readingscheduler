package com.github.gresh113.readingscheduler.ui.fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Build.VERSION_CODES.Q
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.github.gresh113.readingscheduler.ui.MainActivity
import java.time.LocalDate
import java.util.*

@RequiresApi(Q)
class DisplayDatePickerFragment(val mainActivity: MainActivity) : DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreate(savedInstanceState)
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(this.requireContext(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        val monthString: String = if (month < 9) "0${month+1}" else "${month+1}"
        val dayString: String = if (day < 9) "0$day" else "$day"
        mainActivity.setDate(LocalDate.parse("$year-$monthString-$dayString"))
        mainActivity.loadDateAndTasks()
    }
}