package tasks.taskbuilders

import scheduler.task.Task
import java.util.*

open class TaskBuilder() {
    protected var name: String  = ""
    protected var description: String = ""

    fun called(nameIn: String): TaskBuilder {
        this.name = nameIn
        return this
    }

    fun description(descriptionIn: String?): TaskBuilder {
        if (descriptionIn != null)
            this.description = descriptionIn
        return this
    }

    open fun build(): Task {
        return Task(this.name, this.description)
    }
}