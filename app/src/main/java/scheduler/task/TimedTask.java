package scheduler.task;/*
package scheduler.task;

import spans.TimeSpan;

import java.time.Duration;
import java.time.LocalTime;

public class TimedTask extends Task implements ITask, ITimed {
    private TimeSpan timeSpan;

    public TimedTask(String nameIn, String descriptionIn, TimeSpan time) {
        super(nameIn, descriptionIn);
        timeSpan = time;
    }
    public TimedTask(TimeSpan time, Task taskIn) {
        this(taskIn.getName(), taskIn.getDescription(), time);
    }

    public Duration getDuration() {
        return timeSpan.getDuration();
    }

    public LocalTime getStartTime() {
        return timeSpan.getStartTime();
    }

    public LocalTime getEndTime() {
        return timeSpan.getEndTime();
    }
}
*/
