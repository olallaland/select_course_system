package service.instructor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ? ?
 * ?@author?luo tianyue?
 * ?@Date?2019/12/7 ?
 * ?@Time?18:10 ?
 */
@WebServlet(name = "ManageApplicationService", value = "/instructor/manageApplication")
public class ManageApplicationService extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String courseId = request.getParameter("courseId");
        String sectionId = request.getParameter("sectionId");
        String studentId = request.getParameter("studentId");
        String status = request.getParameter("status");

        if("pass".equals(status)) {
            //1. insert the record into 'take' table
            //2. change the status of the application to "通过"
            //3. check whether the total number of students have over the capacity of the classroom
            // step 3 can be done by trigger
        } else if("reject".equals(status)) {
            //1. change the status of the application to  "未通过"
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
