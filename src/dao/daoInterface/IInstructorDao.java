package dao.daoInterface;

import bean.InstructorBean;

import java.util.ArrayList;

/**
 * ? ?
 * ?@author?luo tianyue?
 * ?@Date?2019/12/3 ?
 * ?@Time?22:31 ?
 */
public interface IInstructorDao {
    public ArrayList<InstructorBean> getInstructor(int courseId, int sectionId);
}
