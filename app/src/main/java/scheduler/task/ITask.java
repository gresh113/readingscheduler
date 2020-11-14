package scheduler.task;

public interface ITask {
    String getName();
    String getDescription();
    boolean isFinished();
    void setFinished();
}
