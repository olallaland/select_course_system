package bean;

import java.util.ArrayList;

/**
 * ? ?
 * ?@author?luo tianyue?
 * ?@Date?2019/12/3 ?
 * ?@Time?12:57 ?
 */
public class SectionBean {
    private int sectionId;
    private String year;
    private String semester;
    private CourseBean course;
    private int maxNum;
    private int currentNum;
    private EvaluationBean evaluation;
    private ArrayList<InstructorBean> instructors;
    private ArrayList<OccupyBean> occupy;

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public CourseBean getCourse() {
        return course;
    }

    public void setCourse(CourseBean course) {
        this.course = course;
    }

    public int getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(int currentNum) {
        this.currentNum = currentNum;
    }

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    public String getOccupy() {
        String result = "";
        for(int i = 0; i < occupy.size(); i++) {
            result += occupy.get(i);
            if(i != occupy.size() - 1) {
                result += "/";
            }
        }
        return result;
    }

    public void setOccupy(ArrayList<OccupyBean> occupy) {
        this.occupy = occupy;
    }

    public String getInstructors() {
        String result = "";
        for(int i = 0; i < instructors.size(); i++) {
            result += instructors.get(i).getName();
            if(i != instructors.size() - 1) {
                result += "/";
            }
        }
        return result;
    }

    public void setInstructors(ArrayList<InstructorBean> instructors) {
        this.instructors = instructors;
    }

    public EvaluationBean getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(EvaluationBean evaluation) {
        this.evaluation = evaluation;
    }

    @Override
    public String toString() {
        return "SectionBean{" +
                "sectionId=" + sectionId +
                ", year='" + year + '\'' +
                ", semester='" + semester + '\'' +
                ", course=" + course +
                ", maxNum=" + maxNum +
                ", currentNum=" + currentNum +
                ", evaluation=" + evaluation +
                ", instructors=" + getInstructors() +
                ", occupy=" + getOccupy() +
                '}';
    }
}
