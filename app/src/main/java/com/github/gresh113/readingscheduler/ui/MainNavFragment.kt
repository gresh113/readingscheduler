package com.github.gresh113.readingscheduler.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.github.gresh113.readingscheduler.R
import com.github.gresh113.readingscheduler.ReadingScheduler

class MainNavFragment: Fragment() {
    private lateinit var demoCollectionAdapter: TaskListAdapterFragment
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_task_pager, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        demoCollectionAdapter = TaskListAdapterFragment(this)
        viewPager = view.findViewById(R.id.task_view_pager)
        viewPager.adapter = demoCollectionAdapter
        var app = (this.activity?.application as ReadingScheduler)
        var date = app.schedule.earliestDate
        val yourModelList: ArrayList<TaskListFragment>  = ArrayList()
        while (date.isBefore(app.schedule.latestDate.plusDays(1))){
            yourModelList.add(TaskListFragment(date))
             date = date.plusDays(1)
        }
        demoCollectionAdapter.apply {
            updateList(yourModelList)
            viewPager.setCurrentItem(this.firstElementPosition, false)
        }
    }

}