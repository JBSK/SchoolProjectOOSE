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
	
	/**
	 * Sets up the first question for the test.
	 * 
	 * Requests the first question from the database.
	 * 
	 * If something goes wrong, returns ...
	 * 
	 * If nothing goes wrong, however, the first question is displayed.
	 */
	public String startNewTest(Model model, HttpSession session, LocalQuestionService localQuestionService) {
		Question firstQuestion = localQuestionService.getFirstQuestion(this);
		
		if(firstQuestion == null) {
			return "questionnaire";
		}
		
		session.setAttribute("questionnaire", this);	
		model.addAttribute("currentQuestion", firstQuestion);
		return "";
	}
	
	/**
	 * First requests the question which was just answered.
	 * Then sets the answer that was given to this question.
	 * Lastly requests the next question of the questionnaire.
	 * 
	 * If a next question exists, this question is shown.
	 * If no next question exists, the results of the questionnaire are shown.
	 */
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
	
	/**
	 * Firstly adds the next question to the list of questions.
	 * Secondly adds this question to the "model" object.
	 * Lastly returns the kind of webpage that has to be shown.
	 */
	private String showNextQuestion(Model model, Question nextQuestion) {
		this.addQuestion(nextQuestion);
		model.addAttribute("currentQuestion", nextQuestion);
		return "questionnaire";
	}
	
	public Question getPreviousQuestion() {
		return answeredQuestions.get(answeredQuestions.size()-1);
	}
	
	/**
	 * This function determines the final results of the test.
	 * 
	 * Requests the calculated results and puts this into an array.
	 * Requests the personality types from the database and puts these into an array.
	 * 
	 * Adds the arrays to their respective "model" values.
	 * These values will later be used to generate the graphics in JavaScript.
	 * 
	 * Afterwards, determine which is the primary and secondary personality type.
	 * These values are then added to their respective "model" values.
	 * Finally, these values will be displayed on the result page.
	 */
	private String showResults(Model model, HttpSession session, LocalPersonalityTypeService localPersonalityTypeService) {
        double[] resultArray = this.calculateResults();
        //String personalityTypes[] = {"Perfectionist", "Helper", "Winnaar", "Artistiekeling", "Waarnemer", "Loyalist", "Optimist", "Baas", "Bemiddelaar"};
        int numberOfTypes = localPersonalityTypeService.getAll().size();
        String personalityTypes[] = new String[numberOfTypes];
        for (int i = 0; i < numberOfTypes; i++){
        	personalityTypes[i] = localPersonalityTypeService.getById(i+1).getName();
        }

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

        //session.removeAttribute("questionnaire"); // Disabled for debug
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
	
    /**
     * Firstly requests all the questions which have been asked so far.
     * Then it assigns the last question to the variable "currentQuestion".
     * Lastly this variable is added to the "model".
     */
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

	/**
	 * Calculates the results of the questionnaire.
	 * 
	 * The steps that are executed are as follows:
	 * 1. Calculates the total points that were scored for each personality type. 
	 * 2. Calculates the total amount of points that were scored during the questionnaire.
	 * 3. Calculates the percentages for each type based on the data from the first two steps.
	 * 4. Returns the data that was calculated in step 3.
	 */
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
	
	/**
	 * Calculates the percentages for each personality type.
	 * 
	 * For every personality type the following is done:
	 * 1. The points that were scored on a type are divided by the total amount of points scored.
	 * 2. The old result value (the total points scored on that type) is overwritten by the new value (the percentage).
	 */
	private void calculateTypePercentages(double[] resultArray, double totalPoints) {
		for(int i = 0; i < resultArray.length; i++) {
			double typePercentage = resultArray[i]/totalPoints;
			resultArray[i] = (double) Math.round(typePercentage * 100) / 100;
		}
	}
	
	/**
	 * Calculates the total points amount of points that were scored during the questionnaire, and returns this value.
	 */
	private double calculateTotalPoints(double[] resultArray) {
		double totalPoints = 0;
		for (double resultPoints : resultArray) {
			totalPoints += resultPoints;
		}
		return totalPoints;
	}
	
	/**
	 * Calculates the total points that were scored on a specific question.
	 * 
	 * First requests the answer that was given, the first and second theorem,
	 * and the respective theorem personality types.
	 * 
	 * Based on the answer that was given the points are calculated as follows:
	 * 1. Get the default points which are allocated. These are requested from the database.
	 * 2. Multiply the value from step 1 with the theorem-specific weight. This weight is also requested from the database.
	 * 
	 * The results of this calculation are added to the "resultArray".
	 */
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