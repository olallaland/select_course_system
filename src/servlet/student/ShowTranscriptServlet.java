package servlet.student;

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
 * ?@Time?15:53 ?
 */
@WebServlet(name = "ShowTranscriptServlet", value = "/showTranscriptServlet")
public class ShowTranscriptServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/student/showTranscript").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
