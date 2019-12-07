package bean;

/**
 * ? ?
 * ?@author?luo tianyue?
 * ?@Date?2019/12/3 ?
 * ?@Time?13:11 ?
 */
public class ClassroomBean {
    private String building;
    private String roomNumber;
    private int capacity;

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "[" + building + ", " + roomNumber + "]";
    }
}
