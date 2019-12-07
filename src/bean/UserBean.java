package bean;

/**
 * ? ?
 * ?@author?luo tianyue?
 * ?@Date?2019/12/3 ?
 * ?@Time?12:49 ?
 */
public class UserBean {
    private String name;
    private String id;
    private String identity;
    private String department;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", identity='" + identity + '\'' +
                ", department='" + department + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
