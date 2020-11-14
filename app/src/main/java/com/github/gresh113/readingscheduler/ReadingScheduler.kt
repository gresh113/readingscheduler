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

package com.github.gresh113.readingscheduler

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.github.gresh113.readingscheduler.data.PreferenceRepository
import scheduler.Schedule
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.Q)
class ReadingScheduler : Application() {

  lateinit var preferenceRepository: PreferenceRepository
  lateinit var schedule: Schedule
  lateinit var currentDate: LocalDate

  override fun onCreate() {
    super.onCreate()
    preferenceRepository = PreferenceRepository(getSharedPreferences(DEFAULT_PREFERENCES, Context.MODE_PRIVATE))
    preferenceRepository.isDarkTheme = true
    schedule = Schedule()
    currentDate = LocalDate.now()
  }

  companion object {
    const val DEFAULT_PREFERENCES = "default_preferences"
  }
}
