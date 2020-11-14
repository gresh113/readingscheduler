package tasks.taskbuilders

import scheduler.assignment.AbstractAssignment
import scheduler.pages.PageSpan
import scheduler.task.ReadingTask


open class ReadingTaskBuilder(): TaskBuilder(), IReadingTaskBuilder<ReadingTaskBuilder> {
    var pages: PageSpan = PageSpan.fromTo(0,0)
    var assignment: AbstractAssignment? = null
    override fun withPages(pagesIn: PageSpan): ReadingTaskBuilder {
        pages = pagesIn
        return this
    }
    override fun forAssignement(assignmentIn: AbstractAssignment): ReadingTaskBuilder {
        assignment = assignmentIn
        return this
    }
    override fun build(): ReadingTask {
        return ReadingTask(pages, assignment, this.name, this.description)
    }
}