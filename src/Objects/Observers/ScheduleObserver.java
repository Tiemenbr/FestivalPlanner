package Objects.Observers;

import Objects.Schedule;

public abstract class ScheduleObserver {
    protected Schedule schedule;

    public abstract void update();
}
