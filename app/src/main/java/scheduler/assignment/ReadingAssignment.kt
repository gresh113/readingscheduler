package scheduler.assignment

import android.os.Build
import androidx.annotation.RequiresApi
import scheduler.pages.IPaged
import scheduler.pages.PageHandler
import scheduler.pages.PageSpan
import scheduler.task.ReadingTask
import scheduler.task.Task
import tasks.taskbuilders.ReadingTaskBuilder
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.*

@RequiresApi(api = Build.VERSION_CODES.Q)
class ReadingAssignment : AbstractAssignment, IPaged {
    private val pageHandler: PageHandler

    private constructor(pagesIn: PageSpan, startDateIn: LocalDate, dueDateIn: LocalDate, nameIn: String) : super(startDateIn, dueDateIn, nameIn) {
        pageHandler = PageHandler.with(pagesIn)
    }

    private constructor(pagesIn: PageSpan, dueDateIn: LocalDate, nameIn: String) : super(dueDateIn, nameIn) {
        pageHandler = PageHandler.with(pagesIn)
    }

    val pagesPerDay: HashMap<LocalDate, PageSpan>
        get() {
            if (pageHandler.pagesPerDay.isEmpty()) pageHandler.generatePagesPerDay(this, LocalDate.now())
            return pageHandler.pagesPerDay
        }

    override fun getPages(): PageSpan {
        return pageHandler.pages
    }

    override fun getTaskForDay(dayIn: LocalDate): Optional<Task> {
        var taskOptional: Optional<Task> = Optional.empty()
        if (dayIn.dayOfWeek != DayOfWeek.SUNDAY) {
            val pagesForDay = pagesPerDay.getOrDefault(dayIn, PageSpan.fromTo(0, 0))
            val task = ReadingTaskBuilder()
                    .forAssignement(this)
                    .withPages(pagesForDay)
                    .called("Read $name")
                    .build() as ReadingTask
            taskOptional = Optional.of(task)
        }
        return taskOptional
    }

    companion object {
        fun with(pagesIn: PageSpan, startDateIn: LocalDate, dueDateIn: LocalDate, nameIn: String): ReadingAssignment {
            return ReadingAssignment(pagesIn, startDateIn, dueDateIn, nameIn)
        }

        fun with(pagesIn: PageSpan, dueDateIn: LocalDate, nameIn: String): ReadingAssignment {
            return ReadingAssignment(pagesIn, dueDateIn, nameIn)
        }
    }
}