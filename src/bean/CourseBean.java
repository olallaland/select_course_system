package bean;

/**
 * ? ?
 * ?@author?luo tianyue?
 * ?@Date?2019/12/3 ?
 * ?@Time?12:53 ?
 */
public class CourseBean {
    private int id;
    private String name;
    private String assignment;
    private int credit;
    private String outline;
    private String school;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssignment() {
        return assignment;
    }

    public void setAssignment(String assignment) {
        this.assignment = assignment;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getOutline() {
        return outline;
    }

    public void setOutline(String outline) {
        this.outline = outline;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    @Override
    public String toString() {
        return "CourseBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", assignment='" + assignment + '\'' +
                ", credit=" + credit +
                ", outline='" + outline + '\'' +
                ", school='" + school + '\'' +
                '}';
    }
}
