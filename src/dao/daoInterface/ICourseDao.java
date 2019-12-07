package dao.daoInterface;

import bean.ApplyBean;
import bean.CourseBean;
import bean.SectionBean;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * ? ?
 * ?@author?luo tianyue?
 * ?@Date?2019/12/4 ?
 * ?@Time?16:04 ?
 */
public interface ICourseDao {

    /**
     * show all courses set up in this semester
     * @return
     */
    public ArrayList<SectionBean> showAllCourse();

    /**
     * search the course by some keywords
     * @param searchCourseId
     * @param searchSectionId
     * @param searchCourseName
     * @return
     * @throws SQLException
     */
    public ArrayList<SectionBean> searchCourse(int searchCourseId, int searchSectionId, String searchCourseName) throws SQLException;

    /**
     * get the current number of students who have selected this course
     * @param courseId
     * @param sectionId
     * @return
     */
    public int getCurrentCount(int courseId, int sectionId);

    /**
     * When a student select a course or apply a course, the system needs to check
     * whether there is time conflict between these courses.
     * @param studentId
     * @param courseId
     * @param sectionId
     * @return
     */
    public boolean checkTimeConflict(String studentId, int courseId, int sectionId);

    /**
     * check whether the number of the course have reached the maximum number.
     * @param courseId
     * @param sectionId
     * @return
     */
    public boolean checkReachMax(int courseId, int sectionId);

    /**
     * list all the courses the student have selected.
     * @param studentId
     * @return
     */
    public ArrayList<SectionBean> showSchedule(String studentId);

    /**
     * show all the application of the student
     * @param studentId
     * @return
     */
    public ArrayList<ApplyBean> showApplication(String studentId);
}
