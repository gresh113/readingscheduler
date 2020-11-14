package scheduler.task;/*
package scheduler.task;



import java.time.Duration;
import java.time.LocalTime;

public class TimedReadingTask extends ReadingTask implements ITimed{
    private TimedTask timedTask;
    public TimedReadingTask(String nameIn, String desciptionIn, ReadingTask readingTaskIn, TimedTask taskIn) {
        super(readingTaskIn.getPages(), readingTaskIn.getAssignment(), nameIn, desciptionIn);
        timedTask = taskIn;
    }

    @Override
    public LocalTime getStartTime() {
        return timedTask.getStartTime();
    }

    @Override
    public LocalTime getEndTime() {
        return timedTask.getEndTime();
    }

    @Override
    public Duration getDuration() {
        return timedTask.getDuration();
    }
}
*/
