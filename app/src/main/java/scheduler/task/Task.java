package scheduler.task;

import scheduler.pages.PageSpan;

public class Task implements ITask {
    private String name;
    private String description = "";
    private boolean finished = false;

    public Task(String nameIn, String descriptionIn) {
        name = nameIn;
        description = descriptionIn;
    }

    public Task(Task taskIn) {
        this(taskIn.getName(), taskIn.getDescription());
    }

    public String generateString() {
        boolean parenthesesFlag = this instanceof ITimed ;
        String outString = this.getName() + ": ";
        outString = getDescription(outString);
        if (parenthesesFlag)  outString = outString.concat("(");
        outString = getTimeString(outString);
        if (parenthesesFlag) outString = outString.concat(") ");
        outString = getPagesString(outString);
        return outString;
    }

    private String getPagesString(String outString) {
        if (this instanceof ReadingTask){
            ReadingTask readingTask = (ReadingTask) this;
            PageSpan pages = readingTask.getPages();
            outString = outString.concat("Pages " + pages.getStartPage() + " - " + pages.getEndPage());
        }
        return outString;
    }

    private String getDescription(String outString) {
        outString = outString.concat(this.getDescription());
        outString = outString.concat(" ");
        return outString;
    }

    private String getTimeString(String outString) {
        if (this instanceof ITimed) {
            ITimed timedTask = (ITimed) this;
            String timeBlock = timedTask.getStartTime() + " - " + timedTask.getEndTime();
            outString = outString.concat(timeBlock);
        }
        return outString;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isFinished(){
        return finished;
    }

    @Override
    public void setFinished() {
        finished = true;
    }

    @Override
    public String getDescription() {
        return description ;
    }
}
