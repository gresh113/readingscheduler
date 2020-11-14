package scheduler.task;


import scheduler.assignment.AbstractAssignment;
import scheduler.pages.IHasAssignment;
import scheduler.pages.IPaged;
import scheduler.pages.PageSpan;

public class ReadingTask extends Task implements IPaged, ITask, IHasAssignment {
    //private Double pageLength;
    private PageSpan pageSpan;
    private Task task;
    AbstractAssignment assignment;

    public ReadingTask(PageSpan pagesIn, AbstractAssignment assignmentIn, String nameIn, String desciptionIn) {
        super(nameIn, desciptionIn);
        pageSpan = pagesIn;
        assignment = assignmentIn;
    }

    @Override
    public PageSpan getPages() {
        return pageSpan;
    }

    @Override
    public AbstractAssignment getAssignment() {
        return assignment;
    }
}
