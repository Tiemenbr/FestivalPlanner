package Objects.Observers;

import Objects.Schedule;

public class LocationsObserver extends ScheduleObserver{
    public LocationsObserver(Schedule schedule) {
        this.schedule = schedule;
        this.schedule.setLocationsObserver(this);
    }

    @Override
    public void update() {

    }
}
