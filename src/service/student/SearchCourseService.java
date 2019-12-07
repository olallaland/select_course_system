package service.student;

import bean.CourseBean;
import bean.SectionBean;
import dao.CourseDao;
import dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * ? ?
 * ?@author?luo tianyue?
 * ?@Date?2019/12/5 ?
 * ?@Time?14:42 ?
 */
@WebServlet(name = "SearchCourseService", value = "/student/search")
public class SearchCourseService extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchCourseId = request.getParameter("courseId");
        String searchSectionId = request.getParameter("sectionId");
        String searchCourseName = request.getParameter("courseName");
        int courseId;
        int sectionId;
        String error = "";

        // 至少需要输入课程名称或者课程id中的一项
        if(searchCourseId == null && searchCourseName == null) {
            error = "please input course name or course id";
        }

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


        System.out.println(courseId); //-1
        System.out.println(sectionId); // -1
        System.out.println(searchCourseName); // null

        CourseDao courseDao = new CourseDao();
        ArrayList<SectionBean> searchedCourse = new ArrayList<>();
        try {
            searchedCourse = courseDao.searchCourse(courseId, sectionId, searchCourseName);
        } catch(SQLException e) {
            error = e.getMessage();
            System.out.println(e.getMessage());
        }

        if (!error.equals("")) {
            request.setAttribute("error", error);
            request.getRequestDispatcher("/student.jsp").forward(request, response);
        }else {
            request.setAttribute("courseList", searchedCourse);
            request.getRequestDispatcher("/student.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
