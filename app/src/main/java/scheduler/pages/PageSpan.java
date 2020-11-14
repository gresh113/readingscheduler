package scheduler.pages;

public class PageSpan {
    private int pageStart;
    private int pageEnd;
    private PageSpan(int start, int end){
        pageStart = start;
        pageEnd = end;
    }
    public static PageSpan fromTo(int start, int end){
        return new PageSpan(start, end);
    }
    public static PageSpan fromWith(int start, int length){
        return new PageSpan(start, start + length);
    }
    public static PageSpan withTo(int with, int end){
        return new PageSpan(end - with, end);
    }
    public int getStartPage() {
        return pageStart;
    }
    public int getEndPage() {
        return pageEnd;
    }
    public int getLength(){
        return pageEnd - pageStart;
    }
}
