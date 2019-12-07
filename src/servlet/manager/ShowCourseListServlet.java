package servlet.manager;

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
 * ?@Time?15:50 ?
 */
@WebServlet(name = "ShowCourseListServlet")
public class ShowCourseListServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/HomeService/home").forward(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
