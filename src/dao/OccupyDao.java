package dao;

import bean.ClassroomBean;
import bean.OccupyBean;
import bean.SlotBean;
import dao.daoInterface.IOccupyDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * ? ?
 * ?@author?luo tianyue?
 * ?@Date?2019/12/6 ?
 * ?@Time?17:53 ?
 */
public class OccupyDao extends ConnectionDao implements IOccupyDao {
    @Override
    public ArrayList<OccupyBean> getSectionOccupy(int courseId, int sectionId) {
        ArrayList<OccupyBean> occupyList = new ArrayList<>();
        Connection connection = connection();
        String sql = "select building, room_no, capacity from occupy natural join classroom where course_id = ? and sec_id = ?";
        PreparedStatement pst;
        ResultSet rs = null;

        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, courseId);
            pst.setInt(2, sectionId);
            rs = pst.executeQuery();
            while(rs.next()) {
                OccupyBean occupy = new OccupyBean();
                ClassroomBean classroom = new ClassroomBean();
                SlotBean slot = new SlotBean();
                String building = rs.getString(1);
                String roomNumber = rs.getString(2);
                classroom.setBuilding(building);
                classroom.setRoomNumber(roomNumber);
                classroom.setCapacity(rs.getInt(3));
                occupy.setClassroom(classroom);

                ArrayList<SlotBean> slots = getSlotList(building, roomNumber, courseId, sectionId);
                occupy.setSlots(slots);

                occupyList.add(occupy);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return occupyList;
    }

    @Override
    public ArrayList<SlotBean> getSlotList(String building, String roomNumber, int courseId, int sectionId) {
        ArrayList<SlotBean> slots = new ArrayList<>();
        Connection connection = connection();
        String sql = "select slot_id, weekday, start_time, end_time from sec_slot natural join slot where building = ? and room_no = ? and course_id = ? and sec_id = ?";
        PreparedStatement pst;
        ResultSet rs = null;

        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, building);
            pst.setString(2, roomNumber);
            pst.setInt(3, courseId);
            pst.setInt(4, sectionId);
            rs = pst.executeQuery();

            while(rs.next()) {
                SlotBean slot = new SlotBean();
                slot.setId(rs.getInt(1));
                slot.setWeekday(rs.getString(2));
                slot.setStartTime(rs.getString(3));
                slot.setEndTime(rs.getString(4));
                slots.add(slot);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return slots;
    }
}
