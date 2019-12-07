package dao;

import bean.UserBean;
import dao.daoInterface.IUserDao;

import java.sql.*;
import java.util.ArrayList;

/**
 * ? ?
 * ?@author?luo tianyue?
 * ?@Date?2019/12/3 ?
 * ?@Time?20:59 ?
 */
public class UserDao extends ConnectionDao implements IUserDao {
    @Override
    public UserBean searchUser(int idUser) {
//        Connection connection = connection();
//        String sql = "select * from user where id =" + idUser;
//        Statement st = null;
//        UserBean user = new UserBean();
//        try {
//            st = connection.createStatement();
//            ResultSet rs = st.executeQuery(sql);
//            connectionExe(rs, "user");
//            while (rs.next()) {
//                user.setId(rs.getInt(2));
//                user.setPassword(rs.getString(3));
//            }
//            connectionClose(connection, st);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        return null;
    }

    @Override
    public UserBean searchInstructor(String username) {
        boolean result = true;
        Connection connection = connection();
        String sql = "select count(*) from instructor where id =" + username;
        Statement st = null;
        UserBean user = new UserBean();
        try {
            st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            connectionExe(rs, "instructor");

            if(!rs.next()) {
                //
            } else {
                user.setId(rs.getString(1));
                user.setName(rs.getString(2));
                user.setDepartment(rs.getString(3));
            }

            connectionClose(connection, st);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public UserBean searchStudent(String username) {
        boolean result = true;
        Connection connection = connection();
        String sql = "select count(*) from student where id =" + username;
        Statement st = null;
        UserBean user = new UserBean();
        try {
            st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            connectionExe(rs, "student");

            if(!rs.next()) {
                result = false;
            } else {
                user.setId(rs.getString(1));
                user.setName(rs.getString(2));
                user.setDepartment(rs.getString(3));
            }

            connectionClose(connection, st);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean exists(String tableName, String username) throws SQLException {
        Connection connection = connection();
        int count = 0;
        String sql = "select count(*) from ? where course_id = ?";
        PreparedStatement st = connection.prepareStatement(sql);
        st.setString(1, tableName);
        st.setString(2, username);
        ResultSet rs = st.executeQuery();
        while(rs.next()){
            //System.out.println(rs.getInt(1));
            count = rs.getInt(1);
        }
        return count != 0;
    }


    @Override
    public int idUser(String name) {
        Connection connection = connection();
        String sql = "select id,userName from user where username ='" + name + "'";
        return connectionCount(connection, sql, "user");
    }

    public ArrayList<Integer> idUserStr(String nameStr) {
        Connection connection = connection();
        String sql = "select id,userName from user ";
        Statement statement = null;
        ResultSet rs = null;
        ArrayList<Integer> count = new ArrayList<Integer>();
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                if (rs.getString(2).contains(nameStr)) {
                    count.add(Integer.parseInt(rs.getString(1)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        connectionClose(connection, statement);
        return count;
    }

    public static void main(String args[]) throws SQLException {
        UserDao user = new UserDao();
        System.out.println(user.exists("course", "12121"));
    }
}
