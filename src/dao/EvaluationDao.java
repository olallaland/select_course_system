package dao;

import bean.*;
import dao.daoInterface.IEvaluationDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * ? ?
 * ?@author?luo tianyue?
 * ?@Date?2019/12/7 ?
 * ?@Time?10:29 ?
 */
public class EvaluationDao extends ConnectionDao implements IEvaluationDao {
    @Override
    public ArrayList<ScoreBean> showTranscript(String studentId) {
        ArrayList<ScoreBean> transcript = new ArrayList<>();
        Connection connection = connection();
        ResultSet rs = null;
        PreparedStatement pst = null;
        String sql = "select * from student_score_record where student_id = ?";

        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, studentId);
            rs = pst.executeQuery();
            while(rs.next()) {
                ScoreBean score = new ScoreBean();
                score.setStudentId(studentId);
                score.setCourseId(rs.getInt(2));
                score.setSectionId(rs.getInt(3));
                score.setCourseName(rs.getString(4));
                score.setCredit(rs.getInt(5));
                score.setEvaluationId(rs.getInt(1));
                score.setScore(rs.getInt(8));
                transcript.add(score);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        connectionClose(connection, pst);
        return transcript;
    }

    @Override
    public ArrayList<EvaluationBean> showAllEvaluation(String studentId) {
        ArrayList<EvaluationBean> examList = new ArrayList<>();
        Connection connection = connection();
        ResultSet rs = null;
        PreparedStatement pst = null;
        String sql = "select * from student_evaluation where student_id = ?";

        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, studentId);
            rs = pst.executeQuery();
            while(rs.next()) {
                EvaluationBean evaluation = new EvaluationBean();
                ClassroomBean classroom = new ClassroomBean();
                int evaluationId = rs.getInt(5);
                System.out.println(evaluationId);
                evaluation.setCourseId(rs.getInt(2));
                evaluation.setSectionId(rs.getInt(3));
                evaluation.setCourseName(rs.getString(4));
                String type = rs.getString(6);
                evaluation.setType(type);
                if(type.equals("essay")) {
                    classroom.setBuilding("");
                    classroom.setRoomNumber("");
                    classroom.setCapacity(0);
                    evaluation.setClassroom(classroom);
                    EssayBean essay = getEssayBean(evaluationId);
                    evaluation.setDate(essay.getDate());
                    evaluation.setStartTime("");
                    evaluation.setSpan(0);
                } else if(type.equals("exam")) {
                    ExamBean exam = getExamBean(evaluationId);
                    classroom = exam.getClassroom();
                    evaluation.setStartTime(exam.getStartTime());
                    evaluation.setSpan(exam.getSpan());
                    evaluation.setDate(exam.getDate());
                    evaluation.setClassroom(classroom);
                } else {
                    throw new RuntimeException("Undefined evaluation type!");
                }

                examList.add(evaluation);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        connectionClose(connection, pst);
        return examList;
    }

    @Override
    public EssayBean getEssayBean(int evaluationId) {
        EssayBean essay = new EssayBean();
        Connection connection = connection();
        ResultSet rs = null;
        PreparedStatement pst = null;
        String sql = "select * from essay_evaluation where essay_id = ?";

        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, evaluationId);
            rs = pst.executeQuery();

            while(rs.next()) {
                System.out.println(rs.getString(2));
                essay.setDate(rs.getString(2));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        connectionClose(connection, pst);

        return essay;
    }

    @Override
    public ExamBean getExamBean(int evaluationId) {
        ExamBean exam = new ExamBean();
        Connection connection = connection();
        ResultSet rs = null;
        PreparedStatement pst = null;
        String sql = "select * from examination natural join exam_occupy natural join classroom where examination_id = ?";

        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, evaluationId);
            rs = pst.executeQuery();

            while(rs.next()) {
                ClassroomBean classroom = new ClassroomBean();
                classroom.setBuilding(rs.getString(1));
                classroom.setRoomNumber(rs.getString(2));
                classroom.setCapacity(rs.getInt(7));
                exam.setStartTime(rs.getString(4));
                exam.setSpan(rs.getInt(5));
                exam.setDate(rs.getString(6));
                exam.setClassroom(classroom);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        connectionClose(connection, pst);

        return exam;
    }

    @Override
    public EvaluationBean getSectionEvaluation(int courseId, int sectionId) {
        EvaluationBean evaluation = new EvaluationBean();
        Connection connection = connection();
        ResultSet rs = null;
        PreparedStatement pst = null;
        String sql = "select course_id, sec_id, course_name, type, evaluation_id" +
                " from arrange natural join course_section natural join course where course_id = ? and sec_id = ?";

        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, courseId);
            pst.setInt(2, sectionId);

            rs = pst.executeQuery();
            while(rs.next()) {

                ClassroomBean classroom = new ClassroomBean();
                int evaluationId = rs.getInt(5);
                evaluation.setCourseId(rs.getInt(1));
                evaluation.setSectionId(rs.getInt(2));
                evaluation.setCourseName(rs.getString(3));
                String type = rs.getString(4);
                evaluation.setType(type);
                if(type.equals("essay")) {
                    classroom.setBuilding("");
                    classroom.setRoomNumber("");
                    classroom.setCapacity(0);
                    evaluation.setClassroom(classroom);
                    EssayBean essay = getEssayBean(evaluationId);
                    evaluation.setDate(essay.getDate());
                    evaluation.setStartTime("");
                    evaluation.setSpan(0);
                } else if(type.equals("exam")) {
                    ExamBean exam = getExamBean(evaluationId);
                    classroom = exam.getClassroom();
                    evaluation.setStartTime(exam.getStartTime());
                    evaluation.setSpan(exam.getSpan());
                    evaluation.setDate(exam.getDate());
                    evaluation.setClassroom(classroom);
                } else {
                    throw new RuntimeException("Undefined evaluation type!");
                }

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        connectionClose(connection, pst);
        return evaluation;
    }

    public static void main(String args[]) {
        EvaluationDao e = new EvaluationDao();
        ArrayList<EvaluationBean> s = new ArrayList<>();

        s = e.showAllEvaluation("S001");
        for(int i = 0; i < s.size(); i++) {
            System.out.println(s.get(i));
        }
    }
}
