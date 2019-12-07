package bean;

/**
 * ? ?
 * ?@author?luo tianyue?
 * ?@Date?2019/12/4 ?
 * ?@Time?22:27 ?
 */
public class ScoreBean {
    private String studentId;
    private int evaluationId;
    private int courseId;
    private int sectionId;
    private String courseName;
    private int credit;
    private int score;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public int getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(int evaluationId) {
        this.evaluationId = evaluationId;
    }

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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    @Override
    public String toString() {
        return "ScoreBean{" +
                "studentId='" + studentId + '\'' +
                ", evaluationId=" + evaluationId +
                ", courseId=" + courseId +
                ", sectionId=" + sectionId +
                ", courseName='" + courseName + '\'' +
                ", credit=" + credit +
                ", score=" + score +
                '}';
    }
}
