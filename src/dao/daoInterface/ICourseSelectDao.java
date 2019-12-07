package dao.daoInterface;

import bean.ApplyBean;
import bean.StudentBean;

import java.util.ArrayList;

/**
 * ? ?
 * ?@author?luo tianyue?
 * ?@Date?2019/12/7 ?
 * ?@Time?11:11 ?
 */
public interface ICourseSelectDao {
    /**
     * insert the select course record into the database
     * @param courseId
     * @param sectionId
     * @param studentId
     */
    public void selectCourse(int courseId, int sectionId, String studentId);

    /**
     * delete the select record in the table 'take'
     * and insert a record into the table 'drop_record'
     * @param courseId
     * @param sectionId
     * @param studentId
     */
    public void dropCourse(int courseId, int sectionId, String studentId);

    /**
     * check whether the application conflicts with other courses of the student
     * and insert a record into table 'apply'
     * @param courseId
     * @param sectionId
     * @param studentId
     * @param reason
     */
    public void applyCourse(int courseId, int sectionId, String studentId, String reason);

    /**
     * get the roster of the section taught by the instructor
     * @param courseId
     * @param sectionId
     * @param instructorId
     * @return
     */
    public ArrayList<StudentBean> studentRoster(int courseId, int sectionId, String courseName, String instructorId);

    /**
     * get the application list of the section taught by the teacher
     * @param courseId
     * @param sectionId
     * @param courseName
     * @param instructorId
     * @return
     */
    public ArrayList<ApplyBean> applicationList(int courseId, int sectionId, String courseName, String instructorId);
}
