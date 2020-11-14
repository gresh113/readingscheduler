package tasks.taskbuilders

import scheduler.assignment.AbstractAssignment
import scheduler.pages.PageSpan

interface IReadingTaskBuilder<T> where T : IReadingTaskBuilder<T> {

    fun withPages(pagesIn: PageSpan): T
    fun forAssignement(assignmentIn: AbstractAssignment): T
}