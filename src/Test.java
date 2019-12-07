import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ? ?
 * ?@author?luo tianyue?
 * ?@Date?2019/12/3 ?
 * ?@Time?13:14 ?
 */
public class Test {
    public static void main(String args[]) {

       // Date dNow = new Date("2019");
        Date year = new Date();
//        year.setYear(2019);
//        year.setMonth(9);
//        year.setDate(10);
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-mm-dd");

        System.out.println("当前时间为: " + ft.format(year));
        //System.out.println(ft.format(year));
    }
}
