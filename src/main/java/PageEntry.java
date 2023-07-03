public class PageEntry implements Comparable<PageEntry> {
    private final String namePdf;
    private final int page;
    private final int count;

    public PageEntry(String namePdf, int page, int count) {
        this.namePdf = namePdf;
        this.page = page;
        this.count = count;
    }



    public int getCount() {
        return count;
    }

    @Override
    public int compareTo(PageEntry o) {
        return Integer.compare(o.getCount(), this.getCount());
    }

    @Override
    public String toString() {
        return "PageEntry{" +
                "\n\t namePdf='" + namePdf +
                "\n\t page=" + page +
                "\n\t count=" + count +
                "\n\t" + '}';
    }

}