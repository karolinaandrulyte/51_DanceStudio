package lt.ca.javau10.dancestudio.helpers;

public class WorkTimeHelper {

    private TimeSlot timeSlot;

    public WorkTimeHelper(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getWeekDay() {
        return timeSlot.getWeekDay();
    }

    public String getStartTime() {
        return timeSlot.getStartTime();
    }

    public String getEndTime() {
        return timeSlot.getEndTime();
    }

    @Override
    public String toString() {
        return "WorkTimeHelper{" +
                "timeSlot=" + timeSlot +
                ", weekDay=" + getWeekDay() +
                ", startTime=" + getStartTime() +
                ", endTime=" + getEndTime() +
                '}';
    }
}

