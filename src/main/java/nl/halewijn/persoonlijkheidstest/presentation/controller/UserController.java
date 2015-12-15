package nl.halewijn.persoonlijkheidstest.presentation.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import nl.halewijn.persoonlijkheidstest.domain.Answer;
import nl.halewijn.persoonlijkheidstest.domain.Question;
import nl.halewijn.persoonlijkheidstest.domain.Questionnaire;
import nl.halewijn.persoonlijkheidstest.domain.Result;
import nl.halewijn.persoonlijkheidstest.domain.ResultTypePercentage;
import nl.halewijn.persoonlijkheidstest.services.Constants;
import nl.halewijn.persoonlijkheidstest.services.local.LocalPersonalityTypeService;
import nl.halewijn.persoonlijkheidstest.services.local.LocalResultService;
import nl.halewijn.persoonlijkheidstest.services.local.LocalUserService;

@Controller
public class UserController {
	
	@Autowired
	private LocalUserService localUserService;
	
	@Autowired
	private LocalResultService localResultService;
	
	@Autowired
	private LocalPersonalityTypeService localPersonalityTypeService;
	
	private Questionnaire questionnaire = new Questionnaire();
	
	/**
	 * Check whether there is an email address in the browser session.
	 * If not, redirect to the home page.
	 * 
	 * If yes, find the user is associated with this email.
	 * Then, find the finished questionnaires by this user.
	 * Return a web page where these are all displayed.
	 */
	@RequestMapping(value="/myResults")
	public String myResults(Model model, HttpSession session, HttpServletRequest request) {
		String email;
		if (session.getAttribute("email") != null) {
			email = session.getAttribute("email").toString();
//		}
//		
//		if (email != null) {
			//List<Result> userResults = localResultService.findAll();
			int id = localUserService.findByEmailAddress(email).getId();
			List<Result> userResults = localResultService.getByUserId(id);
			model.addAttribute("userResults", userResults);
			return "myResults";
		} else {
			return Constants.redirect;
		}
	}
	
	/**
	 * Currently makes use of the same result page that is shown right after finishing a questionnaire.
	 * 
	 * Loads the result percentages, etcetera, from the database, based on the ID of the result that was selected.
	 * These values are then displayed on a web page.
	 */
	@RequestMapping(value="/showResult", method=RequestMethod.POST)
	public String showResult(Model model, HttpSession session, HttpServletRequest request) {
		List<Question> answeredQuestions = new ArrayList<>();
		List<Answer> answers = new ArrayList<>();
		Result result = localResultService.getByResultId(Integer.parseInt(request.getParameter("number")));
		for (int i = 0; i < 12; i ++) {
			answers.add(result.getTestResultAnswers().get(i));
		}
		
		for (int i = 0; i < answers.size(); i ++) {
			if (result.getId() == 1) {
				for (int j = answers.get(0).getId(); j < answers.get(0).getId() + answers.size(); j ++) {
					Answer answer = localResultService.findAnswer(j);
					if (answers.get(i).getId() == answer.getId()) {
						answeredQuestions.add(i, answer.getQuestion());
						System.out.println("IT WORKS!");
					}
					System.out.println("TEST2");
				}
			}
		}
		
		double[] pTypeResultArray = new double[9];
		for (int i = 0; i < pTypeResultArray.length; i ++) {
			List<ResultTypePercentage> findAllResultTypePercentages = localResultService.findResultTypePercentageByResult(result);
			if (result.getId() == findAllResultTypePercentages.get(i).getResult().getId()) {
				pTypeResultArray[i] = findAllResultTypePercentages.get(i).getPercentage();
			}
		}
		model.addAttribute("scores", pTypeResultArray);
		
		double[] subTypeResultArray = new double[3];
		subTypeResultArray[0] = localResultService.getByResultId(result.getId()).getScoreDenial();
		subTypeResultArray[2] = localResultService.getByResultId(result.getId()).getScoreDevelopment();
		subTypeResultArray[1] = localResultService.getByResultId(result.getId()).getScoreRecognition();
        for (int i = 0; i < subTypeResultArray.length; i++) {
            subTypeResultArray[i] = subTypeResultArray[i] / 100;
        }
        model.addAttribute("subTypeScores", subTypeResultArray);
        
        String[] personalityTypes = questionnaire.getPersonalityTypesFromDb(localPersonalityTypeService);
        model.addAttribute("personalityTypes", personalityTypes);
        double[] pTypeResultArrayCopy = Arrays.copyOf(pTypeResultArray, pTypeResultArray.length);
        int primaryPersonalityTypeID = questionnaire.addPrimaryPersonalityTypeToModel(model, localPersonalityTypeService, pTypeResultArrayCopy);
        pTypeResultArrayCopy[primaryPersonalityTypeID - 1] = 0;
        questionnaire.addSecondaryPersonalityTypeToModel(model, localPersonalityTypeService, pTypeResultArrayCopy);
		
		return Constants.result;
	}
}