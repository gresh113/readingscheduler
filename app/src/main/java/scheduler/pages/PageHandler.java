package scheduler.pages;

import android.os.Build;
import androidx.annotation.RequiresApi;
import scheduler.assignment.AbstractAssignment;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;

@RequiresApi(api = Build.VERSION_CODES.Q)
public class PageHandler {
    PageSpan pages;
    private final HashMap<LocalDate, PageSpan> pagesPerDay = new HashMap<>();
    private PageHandler(PageSpan pagesIn){
        pages = pagesIn;
    }

    public static PageHandler with(PageSpan pagesIn){
        return new PageHandler(pagesIn);
    }

    public PageSpan getPages() {
        return pages;
    }

    private int sumReadPagesBefore(LocalDate date){
        int sum = 0;
        for (LocalDate key : pagesPerDay.keySet())
            if (key.isBefore(date))
                sum += pagesPerDay.get(key).getLength();
        return sum;
    }

    public HashMap<LocalDate, PageSpan> getPagesPerDay() {
        return pagesPerDay;
    }

    public void generatePagesPerDay(AbstractAssignment assignmentIn, LocalDate startDateIn){
        startDateIn = assignmentIn.getStartDate();
        LocalDate actualStartDate = startDateIn.getDayOfWeek() == DayOfWeek.SUNDAY ? startDateIn.plusDays(1) : startDateIn;
        int daysTillDue = assignmentIn.daysUntilDueFrom(actualStartDate);
        int startingPages = Math.floorDiv(pages.getLength(), daysTillDue);
        PageSpan startingSpan = PageSpan.fromWith(pages.getStartPage(), startingPages);
        pagesPerDay.put(actualStartDate, startingSpan);
        for (LocalDate date = startDateIn.plusDays(1); date.isBefore(assignmentIn.getDueDate()); date = date.plusDays(1)){
            if (date.getDayOfWeek() == DayOfWeek.SUNDAY) continue;
            int readPages = this.sumReadPagesBefore(date);
            int pagesLeft = pages.getLength() - this.sumReadPagesBefore(date);
            int daysLeft = assignmentIn.daysUntilDueFrom(date);
            int nextPages = Math.floorDiv(pagesLeft,daysLeft) - 1;
            int nextStartingPage = pages.getStartPage() + readPages + 1;
            PageSpan nextSpan = PageSpan.fromWith(nextStartingPage, nextPages);
            pagesPerDay.put(date, nextSpan);
        }
    }
}
