package nl.halewijn.persoonlijkheidstest.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import nl.halewijn.persoonlijkheidstest.domain.OpenQuestion;
import nl.halewijn.persoonlijkheidstest.domain.Question;
import nl.halewijn.persoonlijkheidstest.domain.Questionnaire;
import nl.halewijn.persoonlijkheidstest.domain.Theorem;
import nl.halewijn.persoonlijkheidstest.domain.TheoremBattle;

public interface IQuestionService {

	public void save(Question question);
	public void delete(Question question);
	public List<Question> getAll();
	public Question getById(int id);
	public void update(Question question);
	
	public Question getFirstQuestion();
	public Question getQuestionById(int id);
	public String getTypeById(int id);
	public Question getNextQuestion(Question previousQuestion);
	public OpenQuestion getOpenQuestionById(int i);
	public TheoremBattle getTheoremBattleById(int i);
	public Question getFirstQuestion(Questionnaire questionnaire);
	public void setQuestionAnswer(HttpServletRequest req, Question previousQuestion);
}
