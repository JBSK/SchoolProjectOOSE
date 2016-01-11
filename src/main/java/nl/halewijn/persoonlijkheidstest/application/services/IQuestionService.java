package nl.halewijn.persoonlijkheidstest.application.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.halewijn.persoonlijkheidstest.application.domain.Question;
import nl.halewijn.persoonlijkheidstest.application.domain.Questionnaire;

public interface IQuestionService {

	Question save(Question question);
	void delete(Question question);
	List<Question> getAll();
	void update(Question question);
	Question getByQuestionId(int id);
	String getQuestionTypeById(int id);
	Question getNextQuestion(Question previousQuestion, String answer);
	Question getFirstQuestion(Questionnaire questionnaire);
	void setQuestionAnswer(HttpServletRequest req, Question previousQuestion);
	List<Question> findAll();
	List<Question> findAllByText(String text);
	List<Question> findAllByPersonalityTypeId(int typeId);
 
}
