package service.login;

import bean.UserBean;
import dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * ? ?
 * ?@author?luo tianyue?
 * ?@Date?2019/12/3 ?
 * ?@Time?20:56 ?
 */
@WebServlet(name = "LoginService", value = "/LoginService/login")
public class LoginService extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String identity = request.getParameter("identity");

        HttpSession session = request.getSession();
        UserBean user = null;
        String error = "";
        UserDao userDao = new UserDao();
        if ("root".equals(identity)) {
            // TODO root
            session.setAttribute("role", "root");
        } else if ("instructor".equals(identity)) {
            if(userDao.searchInstructor(username).getId() != null) {
                session.setAttribute("role", "instructor");
                user = userDao.searchInstructor(username);
            } else {
                error = "instructor not found";
            }
        } else if ("student".equals(identity)) {
            if(userDao.searchStudent(username).getId() != null) {
                session.setAttribute("role", "student");
                user = userDao.searchStudent(username);
            } else {
                error = "student not found";
            }
        } else {
            error = "incorrect username";
        }

        if (!error.equals("")) {
            request.setAttribute("error", error);
            request.getRequestDispatcher("/acceptError.jsp").forward(request, response);
        }else {
            session.setAttribute("user", user);
            response.sendRedirect("/acceptInfo.jsp");
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
