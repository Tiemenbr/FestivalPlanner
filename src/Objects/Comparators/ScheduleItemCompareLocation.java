package Objects.Comparators;

import Objects.ScheduleItem;

import java.util.Comparator;

public class ScheduleItemCompareLocation implements Comparator<ScheduleItem> {
    @Override
    public int compare(ScheduleItem item1, ScheduleItem item2) {
        if (item1.getLocationId() == item2.getLocationId())
            return 1;
        return -1;
    }
}
