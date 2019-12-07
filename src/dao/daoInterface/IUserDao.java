package dao.daoInterface;

import bean.UserBean;

import java.sql.SQLException;

/**
 * ? ?
 * ?@author?luo tianyue?
 * ?@Date?2019/12/3 ?
 * ?@Time?21:02 ?
 */
public interface IUserDao {
    /*
     * 对数据库的操作
     * 用户数据
     * 1.搜索
     * 2.删除
     * 3.添加
     */

    public UserBean searchUser(int idUser);
    public UserBean searchInstructor(String username);
    public UserBean searchStudent(String username);
    public boolean exists(String tableName, String username) throws SQLException;
    public int idUser(String name);
}
