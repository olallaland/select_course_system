package service.instructor;

import bean.StudentBean;
import bean.UserBean;
import dao.CourseSelectDao;
import dao.StudentDao;

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
 * ?@Time?15:42 ?
 */
@WebServlet(name = "ShowCourseRosterService", value = "/instructor/showCourseRoster")
public class ShowCourseRosterService extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchCourseId = request.getParameter("courseId");
        String searchSectionId = request.getParameter("sectionId");
        String searchCourseName = request.getParameter("courseName");
        int courseId = 0;
        int sectionId = 0;
        HttpSession session = request.getSession();
        UserBean user = (UserBean)session.getAttribute("user");
        String instructorId = user.getId();
        String error = "";

        // 至少需要输入课程名称或者课程id中的一项
        if(searchSectionId == null) {
            error = "wrong input";
            request.setAttribute("error", error);
            request.getRequestDispatcher("/instructor.jsp").forward(request, response);
        } else {
            if(searchCourseId != null) {

                courseId = Integer.parseInt(searchCourseId);
            } else {
                courseId = -1;
            }

            if(searchSectionId != null) {
                sectionId = Integer.parseInt(searchSectionId);
            } else {
                sectionId = -1;
            }
        }

        if(courseId == -1 && sectionId == -1) {
            error = "please input at least course id or section id";
            request.setAttribute("error", error);
            request.getRequestDispatcher("/instructor.jsp").forward(request, response);
        }

        CourseSelectDao courseSelect = new CourseSelectDao();
        ArrayList<StudentBean> studentList = new ArrayList<>();
        try {
            studentList = courseSelect.studentRoster(courseId, sectionId, searchCourseName, instructorId);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            error = e.getMessage();
        }

        if (!error.equals("")) {
            request.setAttribute("error", error);
            request.getRequestDispatcher("/instructor.jsp").forward(request, response);
        }else {
            request.setAttribute("studentList", studentList);
            request.getRequestDispatcher("/instructor.jsp").forward(request, response);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
