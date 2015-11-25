package nl.halewijn.persoonlijkheidstest.presentation.controller;

import nl.halewijn.persoonlijkheidstest.domain.*;
import nl.halewijn.persoonlijkheidstest.services.local.LocalPersonalityTypeService;
import nl.halewijn.persoonlijkheidstest.services.local.LocalQuestionService;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QuestionnaireController {

	@Autowired
    private LocalQuestionService localQuestionService;

    @Autowired
    private LocalPersonalityTypeService localPersonalityTypeService;
	
	
	@RequestMapping(value="/session", method=RequestMethod.GET)
    public String session(Model model, HttpSession session) {
		
		session.setAttribute("questionnaire", 1);
		model.addAttribute("name", "World");
		return "greeting";
	}
	
    @RequestMapping(value="/questionnaire", method=RequestMethod.GET)
    public String questionnaire(Model model, HttpSession session) {
    	
    	if(session.getAttribute("questionnaire") == null) {
    		return "questionnaire";
    	} else {
    		return "redirect:/showQuestion";
    	}
    }

    @Transactional
    @RequestMapping(value="/showQuestion", method=RequestMethod.POST)
    public String showQuestionPOST(Model model, HttpSession session, HttpServletRequest httpServletRequest) {
    	
    	Questionnaire questionnaire = null;
    	if(session.getAttribute("questionnaire") == null) {
			questionnaire = new Questionnaire();
			questionnaire.startNewTest(model, session, localQuestionService);
			
		} else {
			
			if(session.getAttribute("questionnaire") instanceof Questionnaire) {
				questionnaire = (Questionnaire) session.getAttribute("questionnaire");
			}
			
			List<Question> answeredQuestions = questionnaire.getAnsweredQuestions();
			Question previousQuestion = answeredQuestions.get(answeredQuestions.size()-1);
			
			localQuestionService.setQuestionAnswer(httpServletRequest, previousQuestion);		
			Question nextQuestion = localQuestionService.getNextQuestion(previousQuestion);
			
			if(nextQuestion != null) {
				questionnaire.addQuestion(nextQuestion);
				model.addAttribute("currentQuestion", nextQuestion);
			}
			else {
				/*
				double[] resultArray = questionnaire.calculateResults();
				String personalityTypes[] = { "Perfectionist", "Helper", "Winnaar", "Artistiekeling", "Waarnemer", "Loyalist", "Optimist", "Baas", "Bemiddelaar" };

				model.addAttribute("personalityTypes", personalityTypes);
				model.addAttribute("scores", resultArray);
				
				session.removeAttribute("questionnaire");
				return "result";
				*/

				double[] resultArray = questionnaire.calculateResults();
				String personalityTypes[] = { "Perfectionist", "Helper", "Winnaar", "Artistiekeling", "Waarnemer", "Loyalist", "Optimist", "Baas", "Bemiddelaar" };

				model.addAttribute("personalityTypes", personalityTypes);
				model.addAttribute("scores", resultArray);

                double[] resultArrayCopy = resultArray;
                int primaryPersonalityTypeID = getIndexOfHighestNumber(resultArrayCopy) + 1;
                resultArrayCopy[primaryPersonalityTypeID - 1] = 0;
                int secondaryPersonalityTypeID = getIndexOfHighestNumber(resultArrayCopy) + 1;

                PersonalityType primaryPersonalityType = localPersonalityTypeService.getById(primaryPersonalityTypeID);
                PersonalityType secondaryPersonalityType = localPersonalityTypeService.getById(secondaryPersonalityTypeID);

                /*

                Error:

                    Whitelabel Error Page

                    This application has no explicit mapping for /error, so you are seeing this as a fallback.

                    Tue Nov 24 16:10:19 CET 2015
                    There was an unexpected error (type=Internal Server Error, status=500).
                    could not initialize proxy - no Session

                 */

                System.out.println(primaryPersonalityType.getName() + " " + secondaryPersonalityType.getName());
                for (int i = 0; i < 9; i++) {
                    System.out.println(personalityTypes[i] + ": " + resultArrayCopy[i]);
                }
                model.addAttribute("primaryPersonalityType", primaryPersonalityType);
                model.addAttribute("secondaryPersonalityType", secondaryPersonalityType);

                session.removeAttribute("questionnaire");
				return "result";
			}
		}
		return "questionnaire";
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

		/*
			}				
		}
		return "questionnaire";
    }
*/

	@RequestMapping(value="/showQuestion", method=RequestMethod.GET)
    public String showQuestionGET(Model model, HttpSession session) {
		
    	Questionnaire questionnaire = null;

		if(session.getAttribute("questionnaire") == null) {
			return "questionnaire";

		} else {
			if(session.getAttribute("questionnaire") instanceof Questionnaire) {
				questionnaire = (Questionnaire) session.getAttribute("questionnaire");
			}
			
			List<Question> answeredQuestions = questionnaire.getAnsweredQuestions();
			Question currentQuestion = answeredQuestions.get(answeredQuestions.size()-1);
			model.addAttribute("currentQuestion", currentQuestion);
		}
		
		return "questionnaire";
	}

}