package dao;

import bean.ApplyBean;
import bean.StudentBean;
import com.sun.jndi.ldap.Connection;
import dao.daoInterface.ICourseDao;
import dao.daoInterface.ICourseSelectDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * ? ?
 * ?@author?luo tianyue?
 * ?@Date?2019/12/7 ?
 * ?@Time?11:11 ?
 */
public class CourseSelectDao extends ConnectionDao implements ICourseSelectDao {
    @Override
    public void selectCourse(int courseId, int sectionId, String studentId) {
        // 检查时间是否冲突
        // 再检查人数是否已满
        // 先添加，如果时间冲突再drop
        HashMap<String, Integer> occupiedSlot = new HashMap<>();
        java.sql.Connection connection = connection();
        PreparedStatement pst = null;
        ResultSet rs;
        CourseDao course = new CourseDao();

        String insertSql = "insert into take(student_id, course_id, sec_id) value(?, ?, ?)";


        // 检查时间冲突
        if(!course.checkTimeConflict(studentId, courseId, sectionId)) {
            connectionClose(connection, pst);
            throw new RuntimeException("time conflict!");
        }

        //检查人数是否已满
        if(course.checkReachMax(courseId, sectionId)) {
            connectionClose(connection, pst);
            throw new RuntimeException("The number of students has reached the maximum number!");
        }


        // 符合上述条件，将记录添加到take表
        try {
            pst = connection.prepareStatement(insertSql);
            pst.setString(1, studentId);
            pst.setInt(2, courseId);
            pst.setInt(3, sectionId);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        connectionClose(connection, pst);
    }

    @Override
    public void dropCourse(int courseId, int sectionId, String studentId) {
        java.sql.Connection connection = connection();
        PreparedStatement pst = null;
        String deleteSql = "delete from take where student_id = ? and course_id = ? and sec_id = ?";
        String addSql = "insert into drop_record(student_id, course_id, sec_id) value(?, ?, ?)";
        try {
            pst = connection.prepareStatement(deleteSql);
            pst.setString(1, studentId);
            pst.setInt(2, courseId);
            pst.setInt(3, sectionId);
            pst.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            pst = connection.prepareStatement(addSql);
            pst.setString(1, studentId);
            pst.setInt(2, courseId);
            pst.setInt(3, sectionId);
            pst.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        connectionClose(connection, pst);
    }

    @Override
    public void applyCourse(int courseId, int sectionId, String studentId, String reason) {
        //1. 若选课人数未满，不允许申请，提示还可以继续选
        //2. 若时间冲突，不允许申请
        //3. 若选课人数已满，但学生曾将退过该门课，则不允许申请
        java.sql.Connection connection = connection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        CourseDao course = new CourseDao();

        String checkDropSql = "select course_id, sec_id from student_drop where student_id = ?";
        String insertSql = "insert into apply(student_id, course_id, sec_id, reason) value(?, ?, ?, ?)";

        // 检查该门课程选课人数是否已满
        if(!course.checkReachMax(courseId, sectionId)) {
            connectionClose(connection, pst);
            throw new RuntimeException("The number of student isn't max, so you cannot apply for it!");
        }

        //检查学生要选的这门课的时间段并检查是否冲突
        if(!course.checkTimeConflict(studentId, courseId, sectionId)) {
            connectionClose(connection, pst);
            throw new RuntimeException("time conflict!");
        }

        // 检查是否退过该门课
        try {
            pst = connection.prepareStatement(checkDropSql);
            pst.setString(1, studentId);
            rs = pst.executeQuery();
            while(rs.next()) {

                int dropedCourseId = rs.getInt(1);
                int dropedSectionId = rs.getInt(2);

                // TODO 检查section id和course id 还是只检查course_id
                if(dropedCourseId == courseId && dropedSectionId == sectionId) {
                    throw new RuntimeException("You have dropped the course before, so you cannot apply it now!");
                }
            }
        } catch (SQLException e) {
            connectionClose(connection, pst);
            throw new RuntimeException(e.getMessage());
        }

        try {
            pst = connection.prepareStatement(insertSql);
            pst.setString(1, studentId);
            pst.setInt(2, courseId);
            pst.setInt(3, sectionId);
            pst.setString(4, reason);
            pst.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        connectionClose(connection, pst);
    }

    @Override
    public ArrayList<StudentBean> studentRoster(int courseId, int sectionId, String courseName, String instructorId) {
        ArrayList<StudentBean> studentList = new ArrayList<>();
        java.sql.Connection connection = connection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "";

        if(courseId == -1) {
            // input course name and section id
            sql = "select student_id, student_name, student_department from instructor_student " +
                    "where instructor_id = ? and course_name = ? and sec_id = ?";
            try {
                pst = connection.prepareStatement(sql);
                pst.setString(1, instructorId);
                pst.setString(2, courseName);
                pst.setInt(3, sectionId);
            } catch(SQLException e) {
                System.out.println(e.getMessage());
            }

        } else if(courseName == null) {
            //input course id and section id
            sql = "select student_id, student_name, student_department from instructor_student " +
                    "where instructor_id = ? and course_id = ? and sec_id = ?";
            try {
                pst = connection.prepareStatement(sql);
                pst.setString(1, instructorId);
                pst.setInt(2, courseId);
                pst.setInt(3, sectionId);
            } catch(SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            //input course id, course name, and section id
            sql = "select student_id, student_name, student_department from instructor_student " +
                    "where instructor_id = ? and course_id = ? and course_name = ? and sec_id = ?";
            try {
                pst = connection.prepareStatement(sql);
                pst.setString(1, instructorId);
                pst.setInt(2, courseId);
                pst.setString(3, courseName);
                pst.setInt(4, sectionId);
            } catch(SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        try {
            rs = pst.executeQuery();
            while(rs.next()) {
                StudentBean student = new StudentBean();
                student.setStudentId(rs.getString(1));
                student.setName(rs.getString(2));
                student.setDepartment(rs.getString(3));

                studentList.add(student);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return studentList;
    }

    @Override
    public ArrayList<ApplyBean> applicationList(int courseId, int sectionId, String courseName, String instructorId) {
        ArrayList<ApplyBean> applicationList = new ArrayList<>();
        java.sql.Connection connection = connection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "";

        if(courseId == -1) {
            // input course name and section id
            sql = "select student_id, student_name, student_department, reason, status" +
                    " from student_application natural join student natural join teach " +
                    "where course_name = ? and sec_id = ? and instructor_id = ? and status = '待批准'";
            try {
                pst = connection.prepareStatement(sql);
                pst.setString(1, courseName);
                pst.setInt(2, sectionId);
                pst.setString(3, instructorId);
            } catch(SQLException e) {
                System.out.println(e.getMessage());
            }

        } else if(courseName == null) {
            //input course id and section id
            sql = "select student_id, student_name, student_department, reason, status" +
                    " from student_application natural join student natural join teach " +
                    "where course_id = ? and sec_id = ? and instructor_id = ? and status = '待批准'";
            try {
                pst = connection.prepareStatement(sql);
                pst.setInt(1, courseId);
                pst.setInt(2, sectionId);
                pst.setString(3, instructorId);
            } catch(SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            //input course id, course name, and section id
            sql = "select student_id, student_name, student_department, reason, status" +
                    " from student_application natural join student natural join teach " +
                    "where course_id = ? and course_name = ? and sec_id = ? and instructor_id = ? and status = '待批准'";
            try {
                pst = connection.prepareStatement(sql);
                pst.setInt(1, courseId);
                pst.setString(2, courseName);
                pst.setInt(3, sectionId);
                pst.setString(4, instructorId);
            } catch(SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        try {
            rs = pst.executeQuery();
            while(rs.next()) {
                ApplyBean apply = new ApplyBean();
                StudentBean student = new StudentBean();
                student.setStudentId(rs.getString(1));
                student.setName(rs.getString(2));
                student.setDepartment(rs.getString(3));
                apply.setStudent(student);

                apply.setReason(rs.getString(4));
                apply.setStatus(rs.getString(5));

                applicationList.add(apply);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return applicationList;
    }

    public static void main(String args[]) {
        CourseSelectDao csd = new CourseSelectDao();
        ArrayList<ApplyBean> list2 = csd.applicationList(12121, 1, null, "T0001");
        ArrayList<StudentBean> list = csd.studentRoster(100002, 2, null, "T0021");
        for(int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

        for(int i = 0; i < list2.size(); i++) {
            System.out.println(list2.get(i));
        }
    }
}
