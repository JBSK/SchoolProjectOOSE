package nl.halewijn.persoonlijkheidstest.services.local;


import nl.halewijn.persoonlijkheidstest.datasource.dao.QuestionDao;
import nl.halewijn.persoonlijkheidstest.datasource.dao.TheoremDao;
import nl.halewijn.persoonlijkheidstest.domain.OpenQuestion;
import nl.halewijn.persoonlijkheidstest.domain.Question;
import nl.halewijn.persoonlijkheidstest.domain.Questionnaire;
import nl.halewijn.persoonlijkheidstest.domain.Theorem;
import nl.halewijn.persoonlijkheidstest.domain.TheoremBattle;
import nl.halewijn.persoonlijkheidstest.services.IQuestionService;
import nl.halewijn.persoonlijkheidstest.services.ITheoremService;

import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocalQuestionService implements IQuestionService {

	private Logger logger = Logger.getLogger(getClass().getName());
	
	@Autowired
	private QuestionDao questionDao;

	@Override
	public void save(Question question) {
		questionDao.save(question);
	}

	@Override
	public void delete(Question question) {
		questionDao.delete(question);
	}

	@Override
	public List<Question> getAll() {
		return questionDao.getAll();
	}

	@Override
	public Question getQuestionById(int id) {
		return questionDao.getById(id);
	}
	
	@Override
	public String getTypeById(int id) {
		return questionDao.getTypeById(id);
	}
	
	@Override
	public Question getById(int id) {
		return questionDao.getById(id);
	}

	@Override
	public void update(Question question) {
		questionDao.update(question);
	}

	@Override
	
	// TODO check naar type
	public Question getFirstQuestion() {
		
		return questionDao.getById(1);
	}
	
	/**
	 * Checks whether a next question exists or not.
	 * 
	 * If a next question exists, this question is requested from the database, and then returned.
	 * 
	 * If no next question exists, a null value is returned.
	 */
	@Override
	public Question getNextQuestion(Question previousQuestion) {
		// TODO Routing rules invoeren.
		
		Question next = null;

        if(getQuestionById(previousQuestion.getID()+1) != null) {
            next = getQuestionById(previousQuestion.getID() + 1);
        }

		return next;
	}
	
	/**
	 * Checks in the database whether a question with ID 1 exists.
	 * 
	 * If it exists, question ID 1 is requested from the database, and returned.
	 * 
	 * If it doesn't exist, a null value is returned.
	 */
	@Override
	public Question getFirstQuestion(Questionnaire questionnaire) {
		Question firstQuestion = null;

        if(getQuestionById(1) != null) {
            firstQuestion = getQuestionById(1);
        }

		questionnaire.addQuestion(firstQuestion);
		return firstQuestion;
	}
	
	/**
	 * This sets the answer for question X at Y.
	 * 
	 * If the question was a theorem battle, the answer is set between zero and four.
	 * 
	 * If the question was an open question, the answer is requested from the browser session.
	 * The requested text will be set as the answer.
	 */
	@Override
	public void setQuestionAnswer(HttpServletRequest req, Question previousQuestion) {
		String answer = req.getParameter("answer");
		if(previousQuestion instanceof TheoremBattle) {
			((TheoremBattle) previousQuestion).setAnswer(answer.charAt(0));
		} else if(previousQuestion instanceof OpenQuestion) {
			((OpenQuestion) previousQuestion).setAnswer(answer);
		}
	}
}