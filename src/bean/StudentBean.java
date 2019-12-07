package bean;

/**
 * ? ?
 * ?@author?luo tianyue?
 * ?@Date?2019/12/7 ?
 * ?@Time?16:03 ?
 */
public class StudentBean {
    private String studentId;
    private String name;
    private String department;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
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
        return "StudentBean{" +
                "studentId='" + studentId + '\'' +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}
