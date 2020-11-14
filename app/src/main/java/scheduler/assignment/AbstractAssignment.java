package scheduler.assignment;

import android.os.Build;
import androidx.annotation.RequiresApi;
import scheduler.task.Task;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Optional;

@RequiresApi(api = Build.VERSION_CODES.Q)
public abstract class AbstractAssignment {
    protected LocalDate dueDate;
    protected Optional<LocalDate> startDate = Optional.empty();
    protected String name;

    public AbstractAssignment(LocalDate startDateIn, LocalDate dueDateIn, String nameIn){
        dueDate = dueDateIn;
        startDate = Optional.of(startDateIn);
        name = nameIn;
    }

    public AbstractAssignment(LocalDate dueDateIn, String nameIn){
        dueDate = dueDateIn;
        name = nameIn;
    }

    public int daysUntilDue(){ return daysUntilDueFrom(LocalDate.now());}


    public int daysUntilDueFrom(LocalDate startDate){
        int daysUntil = startDate.until(dueDate).getDays();
        return removeSundayFromDayCount(startDate, daysUntil);
    }

    private int removeSundayFromDayCount(LocalDate startDate, int days){
        if (days > 7) return days - ((days - (days % 7))/7);
        else {
            for (LocalDate date = startDate; date.isBefore(startDate.plusDays(days)); date = date.plusDays(1)){
                if (date.getDayOfWeek() == DayOfWeek.SUNDAY) --days;
            }
            return days;
        }
    }

    public LocalDate getStartDate() {
        return startDate.orElseGet(LocalDate::now);
    }

    public LocalDate getDueDate() {return dueDate;}

    public String getName() {return name;}

    public abstract Optional<Task> getTaskForDay(LocalDate dayIn);
}
