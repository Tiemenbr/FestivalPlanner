package Objects.Comparators;

import Objects.ScheduleItem;

import java.util.Comparator;

public class ScheduleItemCompareDay implements Comparator<ScheduleItem> {
    @Override
    public int compare(ScheduleItem item1, ScheduleItem item2) {
        // Vergelijk de dagen van de twee ScheduleItems
        if (item1.getDay().equals(item2.getDay())) // Als de dagen gelijk zijn, return 1
            return 1;
        return -1; // Als de dagen niet gelijk zijn, return -1
    }
}
