package dao.daoInterface;

import bean.ClassroomBean;
import bean.OccupyBean;
import bean.SlotBean;

import java.util.ArrayList;

/**
 * ? ?
 * ?@author?luo tianyue?
 * ?@Date?2019/12/6 ?
 * ?@Time?17:54 ?
 */
public interface IOccupyDao {
    public ArrayList<OccupyBean> getSectionOccupy(int courseId, int sectionId);
    public ArrayList<SlotBean> getSlotList(String building, String roomNumber, int courseId, int sectionId);
}
