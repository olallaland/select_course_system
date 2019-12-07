package service.student;

import bean.UserBean;
import dao.CourseDao;
import dao.CourseSelectDao;

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
 * ?@Date?2019/12/6 ?
 * ?@Time?19:49 ?
 */
@WebServlet(name = "SelectCourseService", value = "/student/select")
public class SelectCourseService extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String selectCourseId = request.getParameter("courseId");
        String selectSectionId = request.getParameter("sectionId");
        int courseId;
        int sectionId;
        String error = "";

        if(selectCourseId != null) {
            courseId = Integer.parseInt(selectCourseId);
        } else {
            courseId = -1;
        }

        if(selectSectionId != null) {
            sectionId = Integer.parseInt(selectSectionId);
        } else {
            sectionId = -1;
        }

        // get student Id
        HttpSession session = request.getSession();
        UserBean user = (UserBean)session.getAttribute("user");
        String studentId = user.getId();
        CourseSelectDao courseSelect = new CourseSelectDao();
        try {
            courseSelect.selectCourse(courseId, sectionId, studentId);
        } catch (RuntimeException e) {
            error = e.getMessage();
        }

        if (!error.equals("")) {
            request.setAttribute("error", error);
            request.getRequestDispatcher("/student.jsp").forward(request, response);
        }else {

            request.getRequestDispatcher("/student.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
