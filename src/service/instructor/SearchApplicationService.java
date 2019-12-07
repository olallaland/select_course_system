package service.instructor;

import bean.ApplyBean;
import bean.UserBean;
import dao.CourseSelectDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * ? ?
 * ?@author?luo tianyue?
 * ?@Date?2019/12/7 ?
 * ?@Time?17:37 ?
 */
@WebServlet(name = "SearchApplicationService", value = "/instructor/searchApplication")
public class SearchApplicationService extends HttpServlet {
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
            error = "cannot only input section id";
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
        ArrayList<ApplyBean> applyList = new ArrayList<>();
        try {
            applyList = courseSelect.applicationList(courseId, sectionId, searchCourseName, instructorId);
        } catch (RuntimeException e) {
            error = e.getMessage();
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
