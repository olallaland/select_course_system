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
 * ?@Date?2019/12/7 ?
 * ?@Time?8:11 ?
 */
@WebServlet(name = "ApplyCourseService", value = "/student/apply")
public class ApplyCourseService extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String courseId = request.getParameter("courseId");
        String sectionId = request.getParameter("sectionId");
        String applyReason = request.getParameter("reason");
        String error = "";
        int applyCourseId = 0;
        int applySectionId = 0;

        if(courseId == null) {
            error = "course id is required!";
        } else {
            applyCourseId = Integer.parseInt(courseId);
        }

        if(sectionId == null) {
            error = "section id is required!";
        } else {
            applySectionId = Integer.parseInt(sectionId);
        }

        if(applyReason == null) {
            error = "apply reason is required!";
        }

        // get student id
        HttpSession session = request.getSession();
        UserBean user = (UserBean)session.getAttribute("user");
        String studentId = user.getId();
        CourseSelectDao courseSelect = new CourseSelectDao();
        try {
            courseSelect.applyCourse(applyCourseId, applySectionId, studentId, applyReason);
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
