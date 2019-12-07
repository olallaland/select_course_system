package servlet.instructor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ? ?
 * ?@author?luo tianyue?
 * ?@Date?2019/12/4 ?
 * ?@Time?15:55 ?
 */
@WebServlet(name = "ManageApplicationServlet", value = "/manageApplicationServlet")
public class ManageApplicationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/instructor/manageApplication").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
