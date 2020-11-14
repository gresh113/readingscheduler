package scheduler.task;

import java.time.Duration;
import java.time.LocalTime;

public interface ITimed {
    LocalTime getStartTime();

    LocalTime getEndTime();

    Duration getDuration();
}
