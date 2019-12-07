package service.student;

import bean.ApplyBean;
import bean.SectionBean;
import bean.UserBean;
import dao.CourseDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 * ? ?
 * ?@author?luo tianyue?
 * ?@Date?2019/12/7 ?
 * ?@Time?11:04 ?
 */
@WebServlet(name = "ShowAllApplicationService", value = "/student/showApplication")
public class ShowAllApplicationService extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // get student id
        HttpSession session = request.getSession();
        UserBean user = (UserBean)session.getAttribute("user");
        String studentId = user.getId();
        CourseDao course = new CourseDao();
        ArrayList<ApplyBean> applicationList = new ArrayList<ApplyBean>();
        String error = "";

        try {
            applicationList = course.showApplication(studentId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            error = e.getMessage();
        }

        if (!error.equals("")) {
            request.setAttribute("error", error);
            request.getRequestDispatcher("/student.jsp").forward(request, response);
        }else {
            request.setAttribute("applicationList", applicationList);
            request.getRequestDispatcher("/student.jsp").forward(request, response);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
