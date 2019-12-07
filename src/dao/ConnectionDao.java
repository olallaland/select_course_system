package dao;

import java.sql.*;

/**
 * ? ?
 * ?@author?luo tianyue?
 * ?@Date?2019/12/3 ?
 * ?@Time?21:00 ?
 */
public class ConnectionDao {
    public static Connection connection(){
        String URL = "jdbc:mysql://localhost:3306/select_course_system?useSSL=false&serverTimezone=GMT";
        String NAME = "root";
        String PASSWORD = "589985";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("δ�ܳɹ������������������Ƿ�����������");

            e.printStackTrace();
        }
        java.sql.Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, NAME, PASSWORD);
            //       System.out.println("��ȡ���ݿ����ӳɹ���");
        } catch (SQLException e) {
            System.out.println("��ȡ���ݿ�����ʧ�ܣ�");
            //���һ��println���������ʧ�ܣ���������ַ������ߵ�¼���Լ������Ƿ����
            e.printStackTrace();
        }
        return  connection;
    }


    public static void connectionClose(Connection connection, Statement statement){
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.print("�ر�ʧ��");
        }

    }

    public static int connectionCount(Connection connection, String sql, String dateBase)  {
        Statement statement =null;
        ResultSet rs = null;
        int count =0;

        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(sql);
            connectionExe(rs,dateBase);
            while (rs.next()) {
                count = Integer.parseInt(rs.getString(1) );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        connectionClose(connection, statement);
        return count;
    }

    public static void connectionExe(ResultSet rs, String dateBase)  {
        int rows;
        try {
            rs.last();
            rows = rs.getRow();
            if(rows != 1 && rows != 0){
                throw  new Exception(dateBase + "���ݿ⣺���������ظ�����");
            }
            rs.beforeFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
