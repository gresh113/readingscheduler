package com.github.gresh113.readingscheduler.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class TaskListAdapterFragment(frag: Fragment): FragmentStateAdapter(frag){
    private val items = mutableListOf<TaskListFragment>()
    val firstElementPosition = Int.MAX_VALUE / 2

    fun updateList(list: List<TaskListFragment>) {
        items.apply {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = if (items.isNotEmpty()) Int.MAX_VALUE else 0

    override fun createFragment(position: Int): Fragment = items[position.rem(items.size)]

}