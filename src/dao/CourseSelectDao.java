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
        // ���ʱ���Ƿ��ͻ
        // �ټ�������Ƿ�����
        // ����ӣ����ʱ���ͻ��drop
        HashMap<String, Integer> occupiedSlot = new HashMap<>();
        java.sql.Connection connection = connection();
        PreparedStatement pst = null;
        ResultSet rs;
        CourseDao course = new CourseDao();

        String insertSql = "insert into take(student_id, course_id, sec_id) value(?, ?, ?)";


        // ���ʱ���ͻ
        if(!course.checkTimeConflict(studentId, courseId, sectionId)) {
            connectionClose(connection, pst);
            throw new RuntimeException("time conflict!");
        }

        //��������Ƿ�����
        if(course.checkReachMax(courseId, sectionId)) {
            connectionClose(connection, pst);
            throw new RuntimeException("The number of students has reached the maximum number!");
        }


        // ������������������¼��ӵ�take��
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
        //1. ��ѡ������δ�������������룬��ʾ�����Լ���ѡ
        //2. ��ʱ���ͻ������������
        //3. ��ѡ��������������ѧ�������˹����ſΣ�����������
        java.sql.Connection connection = connection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        CourseDao course = new CourseDao();

        String checkDropSql = "select course_id, sec_id from student_drop where student_id = ?";
        String insertSql = "insert into apply(student_id, course_id, sec_id, reason) value(?, ?, ?, ?)";

        // �����ſγ�ѡ�������Ƿ�����
        if(!course.checkReachMax(courseId, sectionId)) {
            connectionClose(connection, pst);
            throw new RuntimeException("The number of student isn't max, so you cannot apply for it!");
        }

        //���ѧ��Ҫѡ�����ſε�ʱ��β�����Ƿ��ͻ
        if(!course.checkTimeConflict(studentId, courseId, sectionId)) {
            connectionClose(connection, pst);
            throw new RuntimeException("time conflict!");
        }

        // ����Ƿ��˹����ſ�
        try {
            pst = connection.prepareStatement(checkDropSql);
            pst.setString(1, studentId);
            rs = pst.executeQuery();
            while(rs.next()) {

                int dropedCourseId = rs.getInt(1);
                int dropedSectionId = rs.getInt(2);

                // TODO ���section id��course id ����ֻ���course_id
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
                    "where course_name = ? and sec_id = ? and instructor_id = ? and status = '����׼'";
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
                    "where course_id = ? and sec_id = ? and instructor_id = ? and status = '����׼'";
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
                    "where course_id = ? and course_name = ? and sec_id = ? and instructor_id = ? and status = '����׼'";
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
