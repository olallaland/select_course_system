package dao.daoInterface;

import bean.EssayBean;
import bean.EvaluationBean;
import bean.ExamBean;
import bean.ScoreBean;

import java.util.ArrayList;

/**
 * ? ?
 * ?@author?luo tianyue?
 * ?@Date?2019/12/7 ?
 * ?@Time?10:28 ?
 */
public interface IEvaluationDao {

    /**
     * get the transcript of the student
     * @param studentId
     * @return
     */
    public ArrayList<ScoreBean> showTranscript(String studentId);

    /**
     * get the all evaluation information of the student
     * @param studentId
     * @return
     */
    public ArrayList<EvaluationBean> showAllEvaluation(String studentId);

    /**
     * get essay information
     * @param evaluationId
     * @return
     */
    public EssayBean getEssayBean(int evaluationId);

    /**
     * get exam information
     * @param evaluationId
     * @return
     */
    public ExamBean getExamBean(int evaluationId);

    /**
     * get the evaluation bean of the section
     * @param courseId
     * @param sectionId
     * @return
     */
    public EvaluationBean getSectionEvaluation(int courseId, int sectionId);

}
