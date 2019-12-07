package bean;

/**
 * ? ?
 * ?@author?luo tianyue?
 * ?@Date?2019/12/7 ?
 * ?@Time?13:06 ?
 */
public class EssayBean {
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "EssayBean{" +
                "date='" + date + '\'' +
                '}';
    }
}
