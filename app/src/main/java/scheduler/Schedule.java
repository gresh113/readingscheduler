package scheduler;


import android.os.Build;
import androidx.annotation.RequiresApi;
import scheduler.assignment.AbstractAssignment;
import scheduler.task.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

@RequiresApi(api = Build.VERSION_CODES.Q)
public class Schedule {
    private LocalDate latestDate = LocalDate.of(2000, 11, 03);
    private LocalDate earliestDate = LocalDate.of(10000, 11, 03);
    private ArrayList<AbstractAssignment> ASSIGNMENTS = new ArrayList<AbstractAssignment>();
    private HashMap<LocalDate, ArrayList<Task>> TASKS_PER_DAY = new HashMap<>();

    public void addAssignment(AbstractAssignment assignmentIn) {
        if (!ASSIGNMENTS.contains(assignmentIn)) {
            ASSIGNMENTS.add(assignmentIn);
            this.generateTasksForAssignment(assignmentIn, assignmentIn.getStartDate(), assignmentIn.getDueDate());
            if (assignmentIn.getDueDate().isAfter(latestDate)) latestDate = assignmentIn.getDueDate();
            if (assignmentIn.getStartDate().isBefore(earliestDate)) earliestDate = assignmentIn.getStartDate();
        }
    }

    public ArrayList<AbstractAssignment> getAssignments() {
        return ASSIGNMENTS;
    }

    public void generateAllTasks() {
        TASKS_PER_DAY.clear();
        for (AbstractAssignment assignment : ASSIGNMENTS)
            generateTasksForAssignment(assignment, assignment.getStartDate(), assignment.getDueDate());
    }

    public void generateTasksForAssignment(AbstractAssignment assignment, LocalDate start, LocalDate end) {
        for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1))
            if (assignment.getTaskForDay(date).isPresent())
                this.addTaskToDay(date, assignment.getTaskForDay(date).get());
    }

    public void addTaskToDay(LocalDate dayIn, Task taskIn) {
        ArrayList<Task> taskList = TASKS_PER_DAY.getOrDefault(dayIn, new ArrayList<>());
        taskList.add(taskIn);
        TASKS_PER_DAY.put(dayIn, taskList);
    }

    public ArrayList<Task> getTasksForDay(LocalDate dateIn) {
        return (TASKS_PER_DAY.get(dateIn) == null) ? new ArrayList<>() : TASKS_PER_DAY.get(dateIn);
    }

    public HashMap<LocalDate, ArrayList<Task>> getAllTasks() {
        return TASKS_PER_DAY;
    }

    public LocalDate getEarliestDate() {
        return earliestDate;
    }

    public LocalDate getLatestDate() {
        return latestDate;
    }
}
