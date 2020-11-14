package com.github.gresh113.readingscheduler

import android.R
import android.R.attr.animation
import android.R.attr.button
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.CheckBox
import android.widget.CompoundButton
import com.github.gresh113.readingscheduler.ui.MainActivity
import scheduler.task.Task
import java.security.AccessController.getContext


class CheckBoxClickListener(var task: Task) : CompoundButton.OnCheckedChangeListener {

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        /*  if (isChecked && buttonView != null && buttonView.animation != null) {
          val animation: Animation = CheckBox.
          animation.setAnimationListener(object : Animation.AnimationListener {
              override fun onAnimationStart(animation: Animation) {}
              override fun onAnimationEnd(animation: Animation) {

              }
              override fun onAnimationRepeat(animation: Animation) {}
          })
          buttonView.startAnimation(animation)*/

        task.setFinished()
        if (buttonView != null) if (buttonView.context is MainActivity) (buttonView.context as MainActivity).loadDateAndTasks()

    }

}