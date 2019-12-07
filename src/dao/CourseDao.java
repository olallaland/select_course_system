package dao;

import bean.*;
import dao.daoInterface.ICourseDao;
import sun.swing.SwingUtilities2;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * ? ?
 * ?@author?luo tianyue?
 * ?@Date?2019/12/4 ?
 * ?@Time?16:05 ?
 */
public class CourseDao extends ConnectionDao implements ICourseDao {
    @Override
    public ArrayList<SectionBean> showAllCourse() {
        Connection connection = connection();
        UserDao userDao = new UserDao();
        String sql = "select * from course NATURAL JOIN course_section";
        Statement st = null;
        ArrayList<SectionBean> arrayList = new ArrayList<SectionBean>();
        try {
            st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                SectionBean section = new SectionBean();
                CourseBean course = new CourseBean();
                course.setId(rs.getInt(1));
                course.setName(rs.getString(2));
                course.setAssignment(rs.getString(3));
                course.setCredit(rs.getInt(4));
                course.setOutline(rs.getString(5));
                course.setSchool(rs.getString(6));
                section.setSectionId(rs.getInt(7));
                section.setYear(rs.getString(8).substring(0, 4));
                section.setSemester(rs.getString(9));
                section.setMaxNum(rs.getInt(10));
                section.setCourse(course);

                arrayList.add(section);
            }
            connectionClose(connection, st);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return arrayList;
    }

    @Override
    public ArrayList<SectionBean> searchCourse(int searchCourseId, int searchSectionId, String searchCourseName) throws SQLException {
        Connection connection = connection();
        ResultSet rs = null;
        PreparedStatement pst = null;
        ArrayList<SectionBean> sections = new ArrayList<>();
        if(searchCourseId != -1 && searchCourseName == null) {

            if(searchSectionId != -1) {
                String sql = "select * from course natural join course_section where course_id = ? and sec_id = ?";
                pst = connection.prepareStatement(sql);
                pst.setInt(1, searchCourseId);
                pst.setInt(2, searchSectionId);

            } else {
                String sql = "select * from course natural join course_section where course_id = ?";
                pst = connection.prepareStatement(sql);
                pst.setInt(1, searchCourseId);
            }
            rs = pst.executeQuery();
        } else if(searchCourseName != null && searchCourseId == -1) {

            if(searchSectionId != -1) {
                String sql = "select * from course natural join course_section where course_name = ? and sec_id = ?";
                pst = connection.prepareStatement(sql);
                pst.setString(1, searchCourseName);
                pst.setInt(2, searchSectionId);
            } else {
                String sql = "select * from course natural join course_section where course_name = ?";
                pst = connection.prepareStatement(sql);
                pst.setString(1, searchCourseName);
            }
        } else if(searchCourseId != -1 && searchCourseName != null){
            if(searchSectionId != -1) {
                String sql = "select * from course natural join course_section where course_name = ? and sec_id = ? and course_id = ?";
                pst = connection.prepareStatement(sql);
                pst.setString(1, searchCourseName);
                pst.setInt(2, searchSectionId);
                pst.setInt(3, searchCourseId);
            } else {
                String sql = "select * from course natural join course_section where course_name = ? and course_id = ?";
                pst = connection.prepareStatement(sql);
                pst.setString(1, searchCourseName);
                pst.setInt(2, searchCourseId);
            }
        }

        while(rs.next()) {
            CourseBean course = new CourseBean();
            SectionBean section = new SectionBean();
            int courseId = rs.getInt(1);
            int sectionId = rs.getInt(7);
            course.setId(rs.getInt(1));
            course.setName(rs.getString(2));
            course.setAssignment(rs.getString(3));
            course.setCredit(rs.getInt(4));
            course.setOutline(rs.getString(5));
            course.setSchool(rs.getString(6));
            section.setCourse(course);

            section.setSectionId(rs.getInt(7));
            section.setYear(rs.getString(8).substring(0, 4));
            section.setSemester(rs.getString(9));
            section.setMaxNum(rs.getInt(10));
            section.setCurrentNum(getCurrentCount(courseId, sectionId));

            OccupyDao occupyDao = new OccupyDao();
            ArrayList<OccupyBean> occupy = occupyDao.getSectionOccupy(courseId, sectionId);
            section.setOccupy(occupy);

            InstructorDao instructorDao = new InstructorDao();
            ArrayList<InstructorBean> instructorList = instructorDao.getInstructor(courseId, sectionId);
            section.setInstructors(instructorList);

            EvaluationDao evaluationDao = new EvaluationDao();
            EvaluationBean evaluation = evaluationDao.getSectionEvaluation(courseId, sectionId);
            section.setEvaluation(evaluation);

            sections.add(section);
        }
        connectionClose(connection, pst);
        return sections;
    }

    @Override
    public int getCurrentCount(int courseId, int sectionId) {
        Connection connection = connection();
        int currentCount = 0;
        String sql = "select current_count from section_current_count where course_id = " + courseId +
                " and sec_id = " + sectionId;
        Statement st = null;

        try {
            st = connection.createStatement();

            ResultSet rs = st.executeQuery(sql);

            while(rs.next()) {
                currentCount = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        connectionClose(connection, st);
        return currentCount;
    }

    @Override
    public boolean checkTimeConflict(String studentId, int courseId, int sectionId) {
        HashMap<String, Integer> occupiedSlot = new HashMap<>();
        Connection connection = connection();
        PreparedStatement pst = null;
        ResultSet rs;
        boolean result = true;
        String querySql = "select slot_id, weekday from student_schedule where student_id = '"+ studentId +"'";
        String getSlotSql = "select slot_id, weekday from section_time where course_id = ? and sec_id = ?";

        // 获得学生已选课的时间段
        try {
            pst = connection.prepareStatement(querySql);
            rs = pst.executeQuery();
            while(rs.next()) {
                int slot_id = rs.getInt(1);
                String weekday = rs.getString(2);
                occupiedSlot.put(weekday + "" + slot_id, 1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        //获得学生要选的这门课的时间段并检查是否冲突
        try {
            pst = connection.prepareStatement(getSlotSql);
            pst.setInt(1, courseId);
            pst.setInt(2, sectionId);
            rs = pst.executeQuery();
            while(rs.next()) {

                int slot_id = rs.getInt(1);
                String weekday = rs.getString(2);
                if(occupiedSlot.containsKey(weekday + "" + slot_id)) {
                    result = false;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        connectionClose(connection, pst);
        return result;
    }

    @Override
    public boolean checkReachMax(int courseId, int sectionId) {
        HashMap<String, Integer> occupiedSlot = new HashMap<>();
        Connection connection = connection();
        PreparedStatement pst = null;
        ResultSet rs;
        boolean result = true;
        String numSql = "select current_count, max_num from section_current_count where course_id = ? and sec_id = ?";
        // 检查该门课程选课人数是否已满
        try {
            pst = connection.prepareStatement(numSql);
            pst.setInt(1, courseId);
            pst.setInt(2, sectionId);
            rs = pst.executeQuery();
            while(rs.next()) {

                int currentCount = rs.getInt(1);
                int maxNum = rs.getInt(2);

                if(currentCount < maxNum) {
                    result = false;
                } else {
                    result = true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        connectionClose(connection, pst);

        return result;
    }

    @Override
    public ArrayList<SectionBean> showSchedule(String studentId) {
        Connection connection = connection();
        ResultSet rs = null;
        PreparedStatement pst = null;
        ArrayList<SectionBean> courseLists = new ArrayList<>();
        String sql = "select * from student_course_list where student_id = ?";

        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, studentId);
            rs = pst.executeQuery();

            while(rs.next()) {
                CourseBean course = new CourseBean();
                SectionBean section = new SectionBean();
                int courseId = rs.getInt(1);
                int sectionId = rs.getInt(2);
                course.setId(rs.getInt(1));
                course.setName(rs.getString(5));
                course.setAssignment(rs.getString(6));
                course.setCredit(rs.getInt(7));
                course.setOutline(rs.getString(8));
                course.setSchool(rs.getString(9));
                section.setCourse(course);

                section.setSectionId(rs.getInt(2));
                section.setYear(rs.getString(3).substring(0, 4));
                section.setSemester(rs.getString(4));
                section.setMaxNum(rs.getInt(10));

                section.setCurrentNum(getCurrentCount(courseId, sectionId));
                OccupyDao occupyDao = new OccupyDao();
                ArrayList<OccupyBean> occupy = occupyDao.getSectionOccupy(courseId, sectionId);

                section.setOccupy(occupy);
                courseLists.add(section);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        connectionClose(connection, pst);

        return courseLists;
    }

    @Override
    public ArrayList<ApplyBean> showApplication(String studentId) {
        Connection connection = connection();
        ResultSet rs = null;
        PreparedStatement pst = null;
        ArrayList<ApplyBean> applicationList = new ArrayList<>();
        String sql = "select * from student_application where student_id = ?";

        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, studentId);
            rs = pst.executeQuery();

            while(rs.next()) {
                ApplyBean application = new ApplyBean();
                application.setCourseId(rs.getInt(2));
                application.setSectionId(rs.getInt(3));
                application.setCourseName(rs.getString(4));
                application.setReason(rs.getString(5));
                application.setStatus(rs.getString(6));
                applicationList.add(application);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        connectionClose(connection, pst);

        return applicationList;
    }


    public static void main(String args[]) throws SQLException {
        CourseDao courseDao = new CourseDao();
        ArrayList<ApplyBean> ss = courseDao.showApplication("S001");
//        CourseBean c = s.get(0).getCourse();
//        System.out.println(c.toString());
//        System.out.println(s.get(0));
        ArrayList<SectionBean> s = courseDao.searchCourse(100001, -1, null);
        //SectionBean lalala = courseDao.searchCourse(100001, 1, null).get(0);
       // System.out.println(lalala);
       // courseDao.dropCourse(100005, 1, "S001");
//        try {
//            //courseDao.selectCourse(100005, 1, "S005");
//            s = courseDao.showApplication("S001");
//        } catch (RuntimeException e) {
//            System.out.println(e.getMessage());
//        }

        for(int i = 0; i < s.size(); i++) {
            System.out.println(s.get(i));
        }
    }
}
