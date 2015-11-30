package nl.halewijn.persoonlijkheidstest.domain;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import nl.halewijn.persoonlijkheidstest.services.local.LocalPersonalityTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import nl.halewijn.persoonlijkheidstest.services.local.LocalQuestionService;

public class Questionnaire {
	
	private List<Question> answeredQuestions = new ArrayList<>();

	private static final double ANSWER_A = 5.0;
	private static final double ANSWER_B = 3.0;
	private static final double ANSWER_C = 1.0;
	private static final double ANSWER_D = ANSWER_B;
	private static final double ANSWER_E = ANSWER_A;
	
	public Questionnaire() {

	}
	
	public String startNewTest(Model model, HttpSession session, LocalQuestionService localQuestionService) {
		Question firstQuestion = localQuestionService.getFirstQuestion(this);
		
		if(firstQuestion == null) {
			return "questionnaire";
		}
		
		session.setAttribute("questionnaire", this);	
		model.addAttribute("currentQuestion", firstQuestion);
		return "";
	}
	
	public String submitAnswer(HttpServletRequest httpServletRequest, LocalQuestionService localQuestionService, LocalPersonalityTypeService localPersonalityTypeService, Model model, HttpSession session) {
		Question previousQuestion = getPreviousQuestion();
		
		localQuestionService.setQuestionAnswer(httpServletRequest, previousQuestion);		
		Question nextQuestion = localQuestionService.getNextQuestion(previousQuestion);
		
		if(nextQuestion != null) {
			return showNextQuestion(model, nextQuestion);
		}
		else {
			return showResults(model, session, localPersonalityTypeService);
		}	
	}

	private String showNextQuestion(Model model, Question nextQuestion) {
		this.addQuestion(nextQuestion);
		model.addAttribute("currentQuestion", nextQuestion);
		return "questionnaire";
	}
	
	public Question getPreviousQuestion() {
		return answeredQuestions.get(answeredQuestions.size()-1);
	}
	
	public String showResults(Model model, HttpSession session, LocalPersonalityTypeService localPersonalityTypeService) {
        double[] resultArray = this.calculateResults();
        String personalityTypes[] = {"Perfectionist", "Helper", "Winnaar", "Artistiekeling", "Waarnemer", "Loyalist", "Optimist", "Baas", "Bemiddelaar"};

        model.addAttribute("personalityTypes", personalityTypes);
        model.addAttribute("scores", resultArray);

        double[] resultArrayCopy = this.calculateResults();
        int primaryPersonalityTypeID = getIndexOfHighestNumber(resultArrayCopy) + 1;
        resultArrayCopy[primaryPersonalityTypeID - 1] = 0;
        int secondaryPersonalityTypeID = getIndexOfHighestNumber(resultArrayCopy) + 1;

        PersonalityType primaryPersonalityType = localPersonalityTypeService.getById(primaryPersonalityTypeID);
        PersonalityType secondaryPersonalityType = localPersonalityTypeService.getById(secondaryPersonalityTypeID);

        System.out.println(primaryPersonalityType.getName() + " " + secondaryPersonalityType.getName());


        model.addAttribute("primaryPersonalityType", primaryPersonalityType);
        model.addAttribute("secondaryPersonalityType", secondaryPersonalityType);

        session.removeAttribute("questionnaire");
        return "result";
    }

    private int getIndexOfHighestNumber(double[] numbers) {
        double highestNumber = 0.0;
        int indexOfHighestNumber = 0;
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] > highestNumber) {
                highestNumber = numbers[i];
                indexOfHighestNumber = i;
            }
        }
        return indexOfHighestNumber;
    }
	
	public void getCurrentQuestion(Model model) {
		List<Question> answeredQuestions = this.getAnsweredQuestions();
		Question currentQuestion = answeredQuestions.get(answeredQuestions.size()-1);
		model.addAttribute("currentQuestion", currentQuestion);
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

		double[] resultArray = new double[9];
		
		for(Question question : answeredQuestions) {
			if(question instanceof TheoremBattle) {
				calculateQuestionPoints(resultArray, question);
			}
		}
		
		double totalPoints = calculateTotalPoints(resultArray);
		calculateTypePercentages(resultArray, totalPoints);
		
		return resultArray;
	}

	private void calculateTypePercentages(double[] resultArray, double totalPoints) {
		for(int i = 0; i < resultArray.length; i++) {
			double typePercentage = resultArray[i]/totalPoints;
			resultArray[i] = (double) Math.round(typePercentage * 100) / 100;
		}
	}

	private double calculateTotalPoints(double[] resultArray) {
		double totalPoints = 0;
		for (double resultPoints : resultArray) {
			totalPoints += resultPoints;
		}
		return totalPoints;
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