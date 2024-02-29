package Objects.Comparators;

import Objects.ScheduleItem;

import java.util.Comparator;

public class ScheduleItemCompareTimeOverlap implements Comparator<ScheduleItem> {
    // Compare method returns 1 if timespans overlap else -1
    @Override
    public int compare(ScheduleItem item1, ScheduleItem item2) {
        if (item1.getStartTime().compareTo(item2.getEndTime()) < 1 && item1.getEndTime().compareTo(item2.getStartTime()) > -1)
            return 1;
        return -1;
    }
}
