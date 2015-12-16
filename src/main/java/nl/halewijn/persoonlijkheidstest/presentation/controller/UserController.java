package nl.halewijn.persoonlijkheidstest.presentation.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import nl.halewijn.persoonlijkheidstest.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import nl.halewijn.persoonlijkheidstest.services.Constants;
import nl.halewijn.persoonlijkheidstest.services.local.LocalPersonalityTypeService;
import nl.halewijn.persoonlijkheidstest.services.local.LocalResultService;
import nl.halewijn.persoonlijkheidstest.services.local.LocalUserService;
import nl.halewijn.persoonlijkheidstest.services.local.LocalWebsiteContentTextService;

@Controller
public class UserController {
	
	@Autowired
	private LocalUserService localUserService;
	
	@Autowired
	private LocalResultService localResultService;
	
	@Autowired
	private LocalPersonalityTypeService localPersonalityTypeService;
	
	@Autowired
    private LocalWebsiteContentTextService localWebsiteContentTextService;

	/**
	 * Check whether there is an email address in the browser session.
	 * If not, redirect to the home page.
	 * 
	 * If yes, find the user is associated with this email.
	 * Then, find the finished questionnaires by this user.
	 * Return a web page where these are all displayed.
	 */
	@RequestMapping(value="/myresults")
	public String myResults(Model model, HttpSession session, HttpServletRequest request) {
		if (session.getAttribute("email") != null) {
            String email = session.getAttribute("email").toString();
            if (!"".equals(email)) {
                int id = localUserService.findByEmailAddress(email).getId();
                List<Result> userResults = localResultService.getByUserId(id);
                model.addAttribute("userResults", userResults);
                return Constants.myresults;
            } else {
                return Constants.redirect;
            }
		} else {
			return Constants.redirect;
		}
	}

    @RequestMapping(value="/showResult", method=RequestMethod.GET)
    public String showResultGET(Model model, HttpSession session, HttpServletRequest request) {
        return Constants.redirect;
    }

	/**
	 * Currently makes use of the same result page that is shown right after finishing a questionnaire.
	 * 
	 * Loads the result percentages et cetera, from the database, based on the ID of the result that was selected.
	 * These values are then displayed on a web page.
	 */
	@RequestMapping(value="/showResult", method=RequestMethod.POST)
	public String showResult(Model model, HttpSession session, HttpServletRequest request) {
        int resultIdParam = Integer.parseInt(request.getParameter("number"));
		Result result = localResultService.getByResultId(resultIdParam);
        if (result != null) {
            if (result.getUser() != null) {
                User user = localUserService.getById(result.getUser().getId());
                if (user != null) {
                    if (user.getEmailAddress().equalsIgnoreCase((String) session.getAttribute(Constants.email))) {
                        Questionnaire questionnaire = new Questionnaire();

                        List<ResultTypePercentage> findAllResultTypePercentages = localResultService.findResultTypePercentageByResult(result);
                        double[] pTypeResultArray = new double[findAllResultTypePercentages.size()];
                        for (int i = 0; i < pTypeResultArray.length; i++) {
                            if (result.getId() == findAllResultTypePercentages.get(i).getResult().getId()) {
                                pTypeResultArray[i] = findAllResultTypePercentages.get(i).getPercentage();
                            }
                        }
                        model.addAttribute("scores", pTypeResultArray);

                        double[] subTypeResultArray = new double[3];
                        subTypeResultArray[0] = localResultService.getByResultId(result.getId()).getScoreDenial();
                        subTypeResultArray[1] = localResultService.getByResultId(result.getId()).getScoreRecognition();
                        subTypeResultArray[2] = localResultService.getByResultId(result.getId()).getScoreDevelopment();

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
                        
                        WebsiteContentText text5 = localWebsiteContentTextService.getByContentId(5);
                		model.addAttribute("FifthContentBox", text5);

                        return Constants.result;
                    } else {
                        return Constants.redirect;
                    }
                } else {
                    return Constants.redirect;
                }
            } else {
            return Constants.redirect;
        }
        } else {
            return Constants.redirect;
        }
	}
}