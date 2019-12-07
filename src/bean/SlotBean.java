package bean;

/**
 * ? ?
 * ?@author?luo tianyue?
 * ?@Date?2019/12/3 ?
 * ?@Time?13:13 ?
 */
public class SlotBean {
    private int id;
    private String startTime;
    private String endTime;
    private String weekday;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    @Override
    public String toString() {
        return "SlotBean{" +
                "id=" + id +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", weekday='" + weekday + '\'' +
                '}';
    }
}
