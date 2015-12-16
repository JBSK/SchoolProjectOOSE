package nl.halewijn.persoonlijkheidstest.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import nl.halewijn.persoonlijkheidstest.services.local.*;
import org.springframework.ui.Model;

import nl.halewijn.persoonlijkheidstest.services.Constants;

public class Questionnaire {
	
	private List<Question> answeredQuestions = new ArrayList<>();
    private ArrayList<String> errors = new ArrayList<>();
	private boolean testFinished = false;

    LocalScoreConstantService localScoreConstantService;

	public Questionnaire() {
        /*
         * ThymeLeaf requires us to have default constructors, further explanation can be found here:
         * http://javarevisited.blogspot.in/2014/01/why-default-or-no-argument-constructor-java-class.html
         */
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
			return Constants.questionnaire;
		}
		
		session.setAttribute(Constants.questionnaire, this);
		model.addAttribute(Constants.currentQuestion, firstQuestion);
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
	public String submitAnswer(HttpServletRequest httpServletRequest, LocalQuestionService localQuestionService, LocalPersonalityTypeService localPersonalityTypeService, Model model, HttpSession session, LocalResultService localResultService, LocalUserService localUserService) {
        Question previousQuestion = getPreviousQuestion();
        String answerString = httpServletRequest.getParameter("answer");
        if (previousQuestion instanceof TheoremBattle) {
        	List<Character> validAnswers = Arrays.asList('A', 'B', 'C', 'D', 'E');
            char answer = answerString.charAt(0);
            if(!validAnswers.contains(answer)) {
                return showInvalidAnswerError(model, previousQuestion);
            }
        }
		localQuestionService.setQuestionAnswer(httpServletRequest, previousQuestion);		
		Question nextQuestion = localQuestionService.getNextQuestion(previousQuestion, answerString);
		if(nextQuestion != null) {
			return showNextQuestion(model, nextQuestion);
		} else {
			this.testFinished = true;
			saveResults(session, localResultService, localUserService, localPersonalityTypeService);
			return showResults(model, session, localPersonalityTypeService);
		}	
	}

	public boolean isTestFinished() {
		return testFinished;
	}

	/**
     * Reloads the question page with an error message saying that the submitted answer is invalid.
     */
	private String showInvalidAnswerError(Model model, Question previousQuestion) {
		errors.add("Het ingevulde antwoord is ongeldig, probeer het alstublieft opnieuw.");
		model.addAttribute("error", getErrorsInLines());
		return showNextQuestion(model, previousQuestion);
	}

    /**
     * Concatenates all the errors that were encountered into one big String,
     * formatted with HTML break lines so we can easily add this to the model.
     */
    private String getErrorsInLines() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String error : errors) {
        	stringBuilder.append(error);
        }
        return stringBuilder.toString();
    }


	/**
	 * Creates a new Result object that contains a list of newly created Answer objects based on
	 * the questionlist in the Questionnaire object. Both the Result and Answer objects get saved
	 * in the database.
	 * 
	 * If there is a logged in user, that user will be linked to the new result. Otherwise the user
	 * column will remain null.
	 */
	private void saveResults(HttpSession session, LocalResultService localResultService, LocalUserService localUserService, LocalPersonalityTypeService localPersonalityTypeService) {
		double[] pTypeResultArray = this.calculatePersonalityTypeResults(answeredQuestions);
		double[] subTypeResultArray = this.calculateSubTypeResults(answeredQuestions);
		Result result;
		String userName = (String) session.getAttribute(Constants.email);
		if(userName != null) {
			User user = localUserService.findByEmailAddress(userName);
			result = new Result(user);
		} else {
			result = new Result(null);
		}
		result = localResultService.saveResult(result);
		setSubScores(subTypeResultArray, result);
		saveResultTypePercentagesInDb(localResultService, localPersonalityTypeService, pTypeResultArray, result);
		saveQuestionAnswersInDb(localResultService, result, this);
		result = localResultService.saveResult(result);
		session.setAttribute(Constants.resultId, result.getId());
	}

	/**
	 * Sets the scores for the subtypes Denial, Recognition and Development
	  */
	private void setSubScores(double[] subTypeResultArray, Result result) {
		result.setScoreDenial(subTypeResultArray[0]);
		result.setScoreRecognition(subTypeResultArray[1]);
		result.setScoreDevelopment(subTypeResultArray[2]);
	}

	/**
	 * This method saves the result in percentages into the database.
	 */

	private void saveResultTypePercentagesInDb(LocalResultService localResultService,
			LocalPersonalityTypeService localPersonalityTypeService, double[] pTypeResultArray, Result result) {
		for(int i = 0; i < pTypeResultArray.length; i++) {		
			PersonalityType type = localPersonalityTypeService.getById(i+1);
			ResultTypePercentage resultTypePercentage = new ResultTypePercentage(result, type, pTypeResultArray[i]);
			localResultService.saveResultTypePercentage(resultTypePercentage);
		}
	}

	/**
	 * This method retrieves all the questions from the Questionnaire and loops through them.
	 * Each Question gets it's own Answer object that contains the answer as well as 
	 * a link to the original Question.
	 * 
	 * The new Answer object gets added to the Result.
	 */
	private void saveQuestionAnswersInDb(LocalResultService localResultService, Result result, Questionnaire questionnaire) {
		List<Question> questions = questionnaire.getAnsweredQuestions();
		for(Question question : questions) {			
			Answer answer = null;
			if(question instanceof TheoremBattle) {
				answer = new Answer(question, String.valueOf(((TheoremBattle) question).getAnswer()), question.getDateAnswered());
			}	
			else if (question instanceof OpenQuestion) {
				answer = new Answer(question, ((OpenQuestion) question).getAnswer(), question.getDateAnswered());
			}
			result.addTestResultAnswer(answer);
			localResultService.saveAnswer(answer);		
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

    /**
     * Retrieves the last question that was answered.
     */
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
	 * Afterwards, determine which is the primary and secondary personality type and calculate the subtype scores.
	 * These values are then added to their respective "model" values.
	 * Finally, these values will be displayed on the result page.
	 */
	private String showResults(Model model, HttpSession session, LocalPersonalityTypeService localPersonalityTypeService) {
        double[] pTypeResultArray = this.calculatePersonalityTypeResults(answeredQuestions);
        model.addAttribute("scores", pTypeResultArray);
        double[] subTypeResultArray = this.calculateSubTypeResults(answeredQuestions);
        for (int i = 0; i < subTypeResultArray.length; i++) {
            subTypeResultArray[i] = subTypeResultArray[i] / 100;
        }
        model.addAttribute("subTypeScores", subTypeResultArray);
        String[] personalityTypes = getPersonalityTypesFromDb(localPersonalityTypeService);
        model.addAttribute("personalityTypes", personalityTypes);
        double[] pTypeResultArrayCopy = Arrays.copyOf(pTypeResultArray, pTypeResultArray.length);
        int primaryPersonalityTypeID = addPrimaryPersonalityTypeToModel(model, localPersonalityTypeService, pTypeResultArrayCopy);
        pTypeResultArrayCopy[primaryPersonalityTypeID - 1] = 0;
        addSecondaryPersonalityTypeToModel(model, localPersonalityTypeService, pTypeResultArrayCopy);

		String tweetText = "Mijn persoonlijkheidstype is " + personalityTypes[0] + "! Test jezelf hier!";
		model.addAttribute("tweetText", tweetText);

        return Constants.result;
    }

	public void addSecondaryPersonalityTypeToModel(Model model,
			LocalPersonalityTypeService localPersonalityTypeService, double[] pTypeResultArrayCopy) {
		int secondaryPersonalityTypeID = getIndexOfHighestNumber(pTypeResultArrayCopy) + 1;
        PersonalityType secondaryPersonalityType = localPersonalityTypeService.getById(secondaryPersonalityTypeID);
        model.addAttribute("secondaryPersonalityType", secondaryPersonalityType);
	}

	public int addPrimaryPersonalityTypeToModel(Model model, LocalPersonalityTypeService localPersonalityTypeService,
			double[] pTypeResultArrayCopy) {
		int primaryPersonalityTypeID = getIndexOfHighestNumber(pTypeResultArrayCopy) + 1;
        PersonalityType primaryPersonalityType = localPersonalityTypeService.getById(primaryPersonalityTypeID);
        model.addAttribute("primaryPersonalityType", primaryPersonalityType);
		return primaryPersonalityTypeID;
	}

	/**
     * Returns the list of all personality types from the database in a String array.
     */
	public String[] getPersonalityTypesFromDb(LocalPersonalityTypeService localPersonalityTypeService) {
		List<PersonalityType> typeList = localPersonalityTypeService.getAll();
        String[] personalityTypes = new String[typeList.size()];

        for (int i = 0; i < typeList.size(); i++) {
            personalityTypes[i] = typeList.get(i).getName();
        }

		return personalityTypes;
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
		Question currentQuestion = answeredQuestions.get(answeredQuestions.size()-1);
		model.addAttribute(Constants.currentQuestion, currentQuestion);
	}
	
	public void addQuestion(Question question) {
		answeredQuestions.add(question);
	}

    /**
     * Set the list of answered questions.
     */
	public void setAnsweredQuestions(List<Question> answeredQuestions) {
		this.answeredQuestions = answeredQuestions;
	}

    /**
     * Retrieves the list of answered questions.
     */
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
			subTypePercentages[i] = calculatePercentage(subTypePoints[i], totalSubTypePoints);
		}

		return subTypePercentages;
	}

    /**
     * Calculates a percentage by dividing the divident by the divisor and returning the quotient after rounding.
     */
	public double calculatePercentage(double number, double total) {
        double percentage = (number / total) * 100.0;
        return roundDouble(percentage);
	}
	
	/**
     * Rounds the given double.
     */
    private double roundDouble(double value) {
        int intValue = (int) (value * 10.0);
        intValue = Math.round(intValue);
        return intValue / 10.0;
    }

    /**
     * Calculates the total sum of an array of numbers.
     */
	public double calculateTotalFromNumbersArray(double[] numbers) {
		double total = 0.0;
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
		
		double firstTheoremPoints = 0, secondTheoremPoints = 0;
		
		List<Double> theoremPoints;
		theoremPoints = determinePointsScoredPerTheorem(questionAnswer, firstTheorem, secondTheorem, firstTheoremPoints, secondTheoremPoints);
		firstTheoremPoints = theoremPoints.get(0);
		secondTheoremPoints = theoremPoints.get(1);
		
		resultArray[firstTheoremPersonalityTypeID - 1] += firstTheoremPoints;
		resultArray[secondTheoremPersonalityTypeID - 1] += secondTheoremPoints;
	}
	
	private List<Double> determinePointsScoredPerTheorem(char questionAnswer, Theorem firstTheorem, Theorem secondTheorem, double firstTheoremPoints, double secondTheoremPoints) {
		List<Double> theoremPoints = new ArrayList<>();

        ScoreConstant scoreConstant;
        try {
            scoreConstant = this.localScoreConstantService.findByAnswer(questionAnswer);
        } catch (NullPointerException npe) {
            scoreConstant = this.localScoreConstantService.findByAnswer('C');
        }

		switch(questionAnswer) {
            case 'A':
				firstTheoremPoints = scoreConstant.getScore() * firstTheorem.getWeight();
				break;
            case 'B':
				firstTheoremPoints = scoreConstant.getScore() * firstTheorem.getWeight();
				break;
            case 'C':
				firstTheoremPoints = scoreConstant.getScore() * firstTheorem.getWeight();
				secondTheoremPoints = scoreConstant.getScore() * secondTheorem.getWeight();
				break;
            case 'D':
				secondTheoremPoints = scoreConstant.getScore() * secondTheorem.getWeight();
				break;
            case 'E':
				secondTheoremPoints = scoreConstant.getScore() * secondTheorem.getWeight();
				break;
            default:
				scoreConstant = this.localScoreConstantService.findByAnswer('C');
				firstTheoremPoints = scoreConstant.getScore() * firstTheorem.getWeight();
				secondTheoremPoints = scoreConstant.getScore() * secondTheorem.getWeight();
				break;
		}
		theoremPoints.add(firstTheoremPoints);
		theoremPoints.add(secondTheoremPoints);
		return theoremPoints;
	}
	
    /**
     * Calculates the total points for a sub weight that were scored on a specific question.
     *
     * First requests the answer that was given, the first and second theorem,
     * and the respective theorem personality types.
     *
     * Based on the answer that was given the points are calculated as follows:
     * 1. Get the default points which are allocated. These are requested from the database.
     * 2. Multiply the value from step 1 with the theorem-specific sub weight. This sub weight is also requested from the database.
     *
     * The results of this calculation are added to the "subWeightArray".
     */
    private void calculateSubWeightPoints(double[] subWeightArray, Question question) {
        char questionAnswer = ((TheoremBattle) question).getAnswer();
        Theorem firstTheorem = ((TheoremBattle) question).getFirstTheorem();
        Theorem secondTheorem = ((TheoremBattle) question).getSecondTheorem();
        
        if (questionAnswer == 'C') {
            addSubweightsToArray(subWeightArray, firstTheorem);
            addSubweightsToArray(subWeightArray, secondTheorem);
        } else if (questionAnswer == 'A' || questionAnswer == 'B') {
            addSubweightsToArray(subWeightArray, firstTheorem);
        } else if (questionAnswer == 'D' || questionAnswer == 'E') {
            addSubweightsToArray(subWeightArray, secondTheorem);
        }
    }

	private void addSubweightsToArray(double[] subWeightArray, Theorem theorem) {
		subWeightArray[0] += theorem.getDenial();
		subWeightArray[1] += theorem.getRecognition();
		subWeightArray[2] += theorem.getDevelopment();
	}

    public void setLocalScoreConstantService(LocalScoreConstantService localScoreConstantService) {
        this.localScoreConstantService = localScoreConstantService;
    }
}