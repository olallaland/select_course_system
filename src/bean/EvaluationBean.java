package bean;

/**
 * ? ?
 * ?@author?luo tianyue?
 * ?@Date?2019/12/3 ?
 * ?@Time?13:21 ?
 */
public class EvaluationBean {
    private int courseId;
    private String courseName;
    private int sectionId;
    private String type;
    private ClassroomBean classroom;
    private String date;
    private String startTime; //
    private int span;//start_time + span

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ClassroomBean getClassroom() {
        return classroom;
    }

    public void setClassroom(ClassroomBean classroom) {
        this.classroom = classroom;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getSpan() {
        return span;
    }

    public void setSpan(int span) {
        this.span = span;
    }

    @Override
    public String toString() {
        return "EvaluationBean{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", sectionId=" + sectionId +
                ", type='" + type + '\'' +
                ", classroom=" + classroom +
                ", date='" + date + '\'' +
                ", startTime='" + startTime + '\'' +
                ", span=" + span +
                '}';
    }
}
