package servlet;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;

/**
 * ? ?
 * ?@author?luo tianyue?
 * ?@Date?2019/12/3 ?
 * ?@Time?20:31 ?
 */
@WebServlet(value = "/login", name = "login")
public class LoginPageServlet extends javax.servlet.http.HttpServlet {
    @Override
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        request.getRequestDispatcher("/LoginService/login").forward(request,response);
    }

    @Override
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doPost(request, response);
    }
}
