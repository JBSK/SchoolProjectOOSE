package nl.halewijn.persoonlijkheidstest.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import nl.halewijn.persoonlijkheidstest.services.local.LocalQuestionService;

public class Questionnaire {
	
	@Autowired
	LocalQuestionService localQuestionService;
	
	private List<Question> answeredQuestions = new ArrayList<Question>();
	
	public Questionnaire() {

	}
	
	public void startNewTest() {
		Question firstQuestion = getFirstQuestion();
	}
	
	public Question getFirstQuestion() {
		
		return null;
	}


	public void showQuestion(Question question) {
		
	}
	
	public void submitAnswer(char answer) {
		
	}
	
	public void submitAnswer(String answer) {
		
	}
	
	public void showResults() {
		
	}
	
	public void addQuestion(Question question) {
		answeredQuestions.add(question);
	}

	public void setAnsweredQuestions(List<Question> answeredQuestions) {
		this.answeredQuestions = answeredQuestions;
	}

	public List<Question> getAnsweredQuestions() {
		return answeredQuestions;
	}
	
	
	
	
}
