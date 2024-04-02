package Objects.Observers;

import Objects.Schedule;

public class ScheduleItemsObserver extends ScheduleObserver {
    public ScheduleItemsObserver(Schedule schedule) {
        this.schedule = schedule;
        this.schedule.setScheduleItemsObserver(this);
    }

    @Override
    public void update() {

    }
}
