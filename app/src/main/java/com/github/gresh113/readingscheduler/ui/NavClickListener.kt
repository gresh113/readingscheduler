package com.github.gresh113.readingscheduler.ui

import android.os.Build.VERSION_CODES.Q
import android.view.MenuItem
import androidx.annotation.RequiresApi

@RequiresApi(Q)
class NavClickListener(val direction: Byte, val activity: MainActivity): MenuItem.OnMenuItemClickListener {
    override fun onMenuItemClick(item: MenuItem?): Boolean {
        activity.navigate(direction)
        return true
    }
}