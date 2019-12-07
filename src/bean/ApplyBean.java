package bean;

/**
 * ? ?
 * ?@author?luo tianyue?
 * ?@Date?2019/12/7 ?
 * ?@Time?11:17 ?
 */
public class ApplyBean {
    private StudentBean student;
    int courseId;
    int sectionId;
    String courseName;
    String reason;
    String status;

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public StudentBean getStudent() {
        return student;
    }

    public void setStudent(StudentBean student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "ApplyBean{" +
                "student=" + student +
                ", courseId=" + courseId +
                ", sectionId=" + sectionId +
                ", courseName='" + courseName + '\'' +
                ", reason='" + reason + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

