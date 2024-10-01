package lt.ca.javau10.dancestudio.helpers;

public enum TimeSlot {
    MONDAY_18(1, "Monday", "18:00", "19:00"),
    MONDAY_19(2, "Monday", "19:00", "20:00"),
    TUESDAY_18(3, "Tuesday", "18:00", "19:00"),
    TUESDAY_19(4, "Tuesday", "19:00", "20:00"),
    WEDNESDAY_18(5, "Wednesday", "18:00", "19:00"),
    WEDNESDAY_19(6, "Wednesday", "19:00", "20:00"),
    THURSDAY_18(7, "Thursday", "18:00", "19:00"),
    THURSDAY_19(8, "Thursday", "19:00", "20:00"),
    FRIDAY_18(9, "Friday", "18:00", "19:00"),
    FRIDAY_19(10, "Friday", "19:00", "20:00");

    private final int classNumber;
    private final String weekDay;
    private final String startTime;
    private final String endTime;

    TimeSlot(int classNumber, String weekDay, String startTime, String endTime) {
        this.classNumber = classNumber;
        this.weekDay = weekDay;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getClassNumber() {
        return classNumber;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public static TimeSlot getTimeSlot(int classNumber) {
        for (TimeSlot slot : TimeSlot.values()) {
            if (slot.classNumber == classNumber) {
                return slot;
            }
        }
        throw new IllegalArgumentException("Invalid class number: " + classNumber);
    }
}

