package nl.halewijn.persoonlijkheidstest.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import nl.halewijn.persoonlijkheidstest.services.local.LocalQuestionService;

public class Questionnaire {
	
	@Autowired
	LocalQuestionService localQuestionService;
	
	private List<Question> answeredQuestions = new ArrayList<Question>();
	
	private static final double ANSWER_A = 5.0;
	private static final double ANSWER_B = 3.0;
	private static final double ANSWER_C = 1.0;
	private static final double ANSWER_D = ANSWER_B;
	private static final double ANSWER_E = ANSWER_A;
	
	public Questionnaire() {

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

	public double[] calculateResults() {

		double[] resultArray = { 0,0,0,0,0,0,0,0,0 };
		
		for(Question question : answeredQuestions) {
			if(question instanceof TheoremBattle) {
				calculateQuestionPoints(resultArray, question);
			}
		}
		
		double totalPoints = 0;
		for(int i = 0; i < 9; i++) {
			totalPoints += resultArray[i];
		}
		
		for(int i = 0; i < 9; i++) {
			double typePercentage = resultArray[i]/totalPoints;
			resultArray[i] = (double) Math.round(typePercentage * 100) / 100; //Math.round(typePercentage * 100, 2);
		}
		
		return resultArray;
	}
	
	private void calculateQuestionPoints(double[] resultArray, Question question) {
		
		char questionAnswer = ((TheoremBattle) question).getAnswer();
		
		Theorem firstTheorem = ((TheoremBattle) question).getFirstTheorem();
		Theorem secondTheorem = ((TheoremBattle) question).getSecondTheorem();
		
		int firstTheoremPersonalityTypeID = firstTheorem.getPersonalityType().getTypeID();
		int secondTheoremPersonalityTypeID = secondTheorem.getPersonalityType().getTypeID();				
		
		double firstTheoremPoints = 0;
		double secondTheoremPoints = 0;
		
		switch(questionAnswer) {
			case 'A': 
				firstTheoremPoints = ANSWER_A * firstTheorem.getWeight();				
				break;
				
			case 'B': 
				firstTheoremPoints = ANSWER_B * firstTheorem.getWeight();
				break;
				
			case 'C': 
				firstTheoremPoints = ANSWER_C * firstTheorem.getWeight();					
				secondTheoremPoints = ANSWER_C * secondTheorem.getWeight();
				break;
				
			case 'D': 
				secondTheoremPoints = ANSWER_D * secondTheorem.getWeight();
				break;
				
			case 'E': 
				secondTheoremPoints = ANSWER_E * secondTheorem.getWeight();
				break;
				
			default: 
				firstTheoremPoints = ANSWER_C * firstTheorem.getWeight();
				secondTheoremPoints = ANSWER_C * secondTheorem.getWeight();
				break;
		}
		
		resultArray[firstTheoremPersonalityTypeID - 1] += firstTheoremPoints;
		resultArray[secondTheoremPersonalityTypeID - 1] += secondTheoremPoints;
	}
	
}
