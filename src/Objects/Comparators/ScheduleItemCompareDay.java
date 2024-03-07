package Objects.Comparators;

import Objects.ScheduleItem;

import java.util.Comparator;

public class ScheduleItemCompareDay implements Comparator<ScheduleItem> {
    @Override
    public int compare(ScheduleItem item1, ScheduleItem item2) {
        if (item1.getDay().equals(item2.getDay()))
            return 1;
        return -1;
    }
}
