package bean;

import java.util.ArrayList;

/**
 * ? ?
 * ?@author?luo tianyue?
 * ?@Date?2019/12/6 ?
 * ?@Time?17:55 ?
 */
public class OccupyBean {
    private ClassroomBean classroom;
    private ArrayList<SlotBean> slots;

    public ClassroomBean getClassroom() {
        return classroom;
    }

    public void setClassroom(ClassroomBean classroom) {
        this.classroom = classroom;
    }

    public ArrayList<SlotBean> getSlots() {
        return slots;
    }

    public void setSlots(ArrayList<SlotBean> slots) {
        this.slots = slots;
    }

    @Override
    public String toString() {

        String weekday = slots.get(0).getWeekday();
        String startTime = slots.get(0).getStartTime();
        String endTime = slots.get(slots.size() - 1).getEndTime();

        return classroom + " - " + "[" + weekday +", " + startTime + "-" + endTime + "]";
    }
}
