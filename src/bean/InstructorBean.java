package bean;

/**
 * ? ?
 * ?@author?luo tianyue?
 * ?@Date?2019/12/7 ?
 * ?@Time?14:15 ?
 */
public class InstructorBean {
    private String instructorId;
    private String name;
    private String department;

    public String getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(String instructorId) {
        this.instructorId = instructorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "InstructorBean{" +
                "instructorId='" + instructorId + '\'' +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}
