package Objects.Comparators;

import Objects.ScheduleItem;

import java.util.Comparator;

public class ScheduleItemCompareDayLocationTime implements Comparator<ScheduleItem> {
    private ScheduleItemCompareDay dayComparator;
    private ScheduleItemCompareLocation locationComparator;
    private ScheduleItemCompareTimeOverlap timeComparator;

    public ScheduleItemCompareDayLocationTime() {
        this.dayComparator = new ScheduleItemCompareDay();
        this.locationComparator = new ScheduleItemCompareLocation();
        this.timeComparator = new ScheduleItemCompareTimeOverlap();
    }

    @Override
    public int compare(ScheduleItem item1, ScheduleItem item2) {
        if (this.dayComparator.compare(item1, item2) < 0)
            return -1;
        if (this.locationComparator.compare(item1, item2) < 0)
            return -1;
        if (this.timeComparator.compare(item1, item2) < 0)
            return -1;
        return 1;
    }
}
