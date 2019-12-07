package dao;

import bean.InstructorBean;
import dao.daoInterface.IInstructorDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * ? ?
 * ?@author?luo tianyue?
 * ?@Date?2019/12/3 ?
 * ?@Time?22:32 ?
 */
public class InstructorDao extends ConnectionDao implements IInstructorDao {

    @Override
    public ArrayList<InstructorBean> getInstructor(int courseId, int sectionId) {
        ArrayList<InstructorBean> instructorList = new ArrayList<>();
        Connection connection = connection();
        PreparedStatement pst = null;
        ResultSet rs;
        String numSql = "select instructor_id, instructor_name, instructor_department from teach " +
                "natural join instructor where course_id = ? and sec_id = ?";
        // 检查该门课程选课人数是否已满
        try {
            pst = connection.prepareStatement(numSql);
            pst.setInt(1, courseId);
            pst.setInt(2, sectionId);
            rs = pst.executeQuery();
            while(rs.next()) {
                InstructorBean instructor = new InstructorBean();
                instructor.setInstructorId(rs.getString(1));
                instructor.setName(rs.getString(2));
                instructor.setDepartment(rs.getString(3));
                instructorList.add(instructor);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        connectionClose(connection, pst);

        return instructorList;
    }
}
