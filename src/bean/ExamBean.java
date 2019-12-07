package bean;

/**
 * ? ?
 * ?@author?luo tianyue?
 * ?@Date?2019/12/7 ?
 * ?@Time?13:06 ?
 */
public class ExamBean {
    private String startTime;
    private String date;
    private int span;
    private ClassroomBean classroom;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSpan() {
        return span;
    }

    public void setSpan(int span) {
        this.span = span;
    }

    public ClassroomBean getClassroom() {
        return classroom;
    }

    public void setClassroom(ClassroomBean classroom) {
        this.classroom = classroom;
    }

    @Override
    public String toString() {
        return "ExamBean{" +
                "startTime='" + startTime + '\'' +
                ", date='" + date + '\'' +
                ", span=" + span +
                ", classroom=" + classroom +
                '}';
    }
}
