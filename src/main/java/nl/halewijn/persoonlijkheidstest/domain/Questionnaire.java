package nl.halewijn.persoonlijkheidstest.domain;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import nl.halewijn.persoonlijkheidstest.services.local.LocalPersonalityTypeService;
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
        double[] pTypeResultArray = this.calculatePersonalityTypeResults(answeredQuestions);
        double[] subTypeResultArray = this.calculateSubTypeResults(answeredQuestions);

        int numberOfTypes = localPersonalityTypeService.getAll().size();
        String personalityTypes[] = new String[numberOfTypes];
        for (int i = 0; i < numberOfTypes; i++){
        	System.out.println(localPersonalityTypeService.getById(i+1));
        	personalityTypes[i] = localPersonalityTypeService.getById(i+1).getName();
        }

        model.addAttribute("personalityTypes", personalityTypes);
        model.addAttribute("scores", pTypeResultArray);

        double[] resultArrayCopy = this.calculatePersonalityTypeResults(answeredQuestions);
        int primaryPersonalityTypeID = getIndexOfHighestNumber(resultArrayCopy) + 1;
        resultArrayCopy[primaryPersonalityTypeID - 1] = 0;
        int secondaryPersonalityTypeID = getIndexOfHighestNumber(resultArrayCopy) + 1;

        PersonalityType primaryPersonalityType = localPersonalityTypeService.getById(primaryPersonalityTypeID);
        PersonalityType secondaryPersonalityType = localPersonalityTypeService.getById(secondaryPersonalityTypeID);

        System.out.println(primaryPersonalityType.getName() + " " + secondaryPersonalityType.getName());

        model.addAttribute("primaryPersonalityType", primaryPersonalityType);
        model.addAttribute("secondaryPersonalityType", secondaryPersonalityType);

        model.addAttribute("subTypeScores", subTypeResultArray);

        //session.removeAttribute("questionnaire"); // Disabled for debug. TODO: Remove for production
        return "result";
    }

    /**
     * Returns the index of the item that has the highest value in the array
     */
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
     * Calculates the personality type results of the questionnaire.
     *
     * The steps that are executed are as follows:
     * 1. Calculates the total points that were scored for each personality type.
     * 2. Calculates the total amount of points that were scored during the questionnaire.
     * 3. Calculates the percentages for each type based on the data from the first two steps.
     * 4. Returns the data that was calculated in step 3.
     */
	public double[] calculatePersonalityTypeResults(List<Question> answeredQuestions) {
		double[] pTypePoints = new double[9];
        double[] pTypePercentages = new double[9];

		for (Question question : answeredQuestions) {
			if (question instanceof TheoremBattle) {
				calculateQuestionPoints(pTypePoints, question);
			}
		}

        double totalQuestionPoints = calculateTotalFromNumbersArray(pTypePoints);
		for (int i = 0; i < pTypePoints.length; i++) {
            pTypePercentages[i] = calculatePercentage(pTypePoints[i], totalQuestionPoints);
        }

		return pTypePercentages;
	}

    /**
     * Calculates the subtype results of the questionnaire.
     *
     * The steps that are executed are as follows:
     * 1. Calculates the total points that were scored for each subtype.
     * 2. Calculates the total amount of points that were scored during the questionnaire.
     * 3. Calculates the percentages for each type based on the data from the first two steps.
     * 4. Returns the data that was calculated in step 3.
     */
	public double[] calculateSubTypeResults(List<Question> answeredQuestions) {
		double[] subTypePoints = new double[3];
		double[] subTypePercentages = new double[3];

		for (Question question : answeredQuestions) {
			if (question instanceof TheoremBattle) {
				calculateSubWeightPoints(subTypePoints, question);
			}
		}

		double totalSubTypePoints = calculateTotalFromNumbersArray(subTypePoints);
		for (int i = 0; i < subTypePoints.length; i++) {
			subTypePercentages[i] = calculatePercentage(subTypePoints[i], totalSubTypePoints) * 100;
		}

		return subTypePercentages;
	}

    /**
     * Calculates a percentage by dividing the divident by the divisor and returning the quotient after rounding.
     */
	public double calculatePercentage(double number, double total) {
        double percentage = number / total;
        return (double) Math.round(percentage * 100) / 100;
	}

    /**
     * Calculates the total sum of an array of numbers.
     */
	public double calculateTotalFromNumbersArray(double[] numbers) {
		double total = 0;
		for (double number : numbers) {
            total += number;
		}
		return total;
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

    /**
     * Calculates the total points for a subweight that were scored on a specific question.
     *
     * First requests the answer that was given, the first and second theorem,
     * and the respective theorem personality types.
     *
     * Based on the answer that was given the points are calculated as follows:
     * 1. Get the default points which are allocated. These are requested from the database.
     * 2. Multiply the value from step 1 with the theorem-specific subweight. This subweight is also requested from the database.
     *
     * The results of this calculation are added to the "subWeightArray".
     */
    private void calculateSubWeightPoints(double[] subWeightArray, Question question) {

        char questionAnswer = ((TheoremBattle) question).getAnswer();

        Theorem firstTheorem = ((TheoremBattle) question).getFirstTheorem();
        Theorem secondTheorem = ((TheoremBattle) question).getSecondTheorem();

        if (questionAnswer == 'C') {
            subWeightArray[0] += firstTheorem.getSubWeight1();
            subWeightArray[1] += firstTheorem.getSubWeight2();
            subWeightArray[2] += firstTheorem.getSubWeight3();

            subWeightArray[0] += secondTheorem.getSubWeight1();
            subWeightArray[1] += secondTheorem.getSubWeight2();
            subWeightArray[2] += secondTheorem.getSubWeight3();
        } else if (questionAnswer == 'A' || questionAnswer == 'B') {
            subWeightArray[0] += firstTheorem.getSubWeight1();
            subWeightArray[1] += firstTheorem.getSubWeight2();
            subWeightArray[2] += firstTheorem.getSubWeight3();
        } else if (questionAnswer == 'D' || questionAnswer == 'E') {
            subWeightArray[0] += secondTheorem.getSubWeight1();
            subWeightArray[1] += secondTheorem.getSubWeight2();
            subWeightArray[2] += secondTheorem.getSubWeight3();
        }
    }

}