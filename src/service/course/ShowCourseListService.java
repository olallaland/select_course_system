package service.course;

import bean.CourseBean;
import bean.SectionBean;
import dao.CourseDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * ? ?
 * ?@author?luo tianyue?
 * ?@Date?2019/12/4 ?
 * ?@Time?16:02 ?
 */
@WebServlet(name = "ShowCourseListService")
public class ShowCourseListService extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CourseDao courseDao =new CourseDao();
        ArrayList<SectionBean> allCourseList = courseDao.showAllCourse();
        request.setAttribute("allCourse",allCourseList);
        request.getRequestDispatcher("/homepage.jsp").forward(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
