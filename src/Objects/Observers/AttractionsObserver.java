package Objects.Observers;

import Objects.Schedule;

public class AttractionsObserver extends ScheduleObserver {
    public AttractionsObserver(Schedule schedule) {
        this.schedule = schedule;
        this.schedule.setAttractionsObserver(this);
    }

    @Override
    public void update() {

    }
}
