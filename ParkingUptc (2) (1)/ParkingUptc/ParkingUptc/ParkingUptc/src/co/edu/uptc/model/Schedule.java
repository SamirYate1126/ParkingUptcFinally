package co.edu.uptc.model;

public class Schedule {
    private String days;
    private String openingTime;
    private String closingTime;

    

    public Schedule() {
    }


    public Schedule(String days, String openingTime, String closingTime) {
        this.days = days;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    
    public String getDays() {
        return days;
    }


    public void setDays(String days) {
        this.days = days;
    }


    public String getOpeningTime() {
        return openingTime;
    }


    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }


    public String getClosingTime() {
        return closingTime;
    }


    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }


    @Override
    public String toString() {
        return "Schedule [days=" + days + ", openingTime=" + openingTime + ", closingTime=" + closingTime + "]";
    }

    
}
