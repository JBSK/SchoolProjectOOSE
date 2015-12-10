package nl.halewijn.persoonlijkheidstest.presentation.controller;

import nl.halewijn.persoonlijkheidstest.domain.PersonalityType;
import nl.halewijn.persoonlijkheidstest.domain.Theorem;
import nl.halewijn.persoonlijkheidstest.domain.User;

import nl.halewijn.persoonlijkheidstest.services.Constants;
import nl.halewijn.persoonlijkheidstest.services.local.LocalPersonalityTypeService;
import nl.halewijn.persoonlijkheidstest.services.local.LocalResultService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import nl.halewijn.persoonlijkheidstest.services.local.LocalTheoremService;
import nl.halewijn.persoonlijkheidstest.services.local.LocalUserService;

@Controller
public class AdminController {

	@Autowired
	private LocalUserService localUserService;
	
	@Autowired
	private LocalResultService localResultService;
	
	@Autowired
	private LocalTheoremService localTheoremService;
	
	@Autowired
	private LocalPersonalityTypeService localPersonalityTypeService;

	String manageTheorems = "manageTheorems";

	/**
	 * Check whether or not the user is an admin.
	 * 
	 * If the user is an admin, display the administration panel.
	 * 
	 * If the user is not an admin, or is not logged in, redirect to the previous page.
	 */
    @RequestMapping(value="/adminPanel")
    public String adminDashboard(Model model, HttpSession session) {
    	boolean isAdmin = checkIfAdmin(session);
		
    	if (isAdmin) {
    		Long totalTests = localResultService.count();
    		Long userTests = localResultService.countUserTests();
    		Long anonymousTests = localResultService.countAnonymousTests();
    		
    		model.addAttribute("users", localUserService.count());
			model.addAttribute("tests", totalTests);
			model.addAttribute("registered_users_tests", userTests);
			model.addAttribute("anonymous_tests", anonymousTests);
			return "adminDashboard";
		} else {
			return Constants.redirect;
		}
	}
    //Requirement buiten de sprint
    @RequestMapping(value="/manageQuestions", method=RequestMethod.GET)
	public String manageQuestions(Model model, HttpSession session, HttpServletRequest req) {
		boolean isAdmin = checkIfAdmin(session);
		
		if (isAdmin) {
			return "managequestions";
		} else {
			return Constants.redirect;
		}
	}

    /**
     * Return all the theorems from the database. These will be displayed on a web page.
     */
	@RequestMapping(value="/manageTheorems", method=RequestMethod.GET)
	public String manageTheorems(Model model, HttpSession session, HttpServletRequest req) {
		boolean isAdmin = checkIfAdmin(session);
		
		if (isAdmin) {
			List<Theorem> theorems = localTheoremService.getAll();
			model.addAttribute("theorems", theorems);
			return "managetheorems";
		} else {
			return Constants.redirect;
		}
	}

	/**
	 * Make sure that, when adding a new theorem, as little as possible guesswork is needed.
	 */
	@RequestMapping(value="/addTheorem")
	public String addTheorem(Model model, HttpSession session, HttpServletRequest req) {
		boolean isAdmin = checkIfAdmin(session);

		if (isAdmin) {
			List<PersonalityType> personalityTypes = localPersonalityTypeService.getAll();
			model.addAttribute("personalityTypes", personalityTypes);

			return "addTheorem";
		} else {
			return Constants.redirect;
		}
	}

	/**
	 * After the web page fields were filled in correctly, add the new theorem to the database.
	 */
	@RequestMapping(value="/addTheoremToDB", method=RequestMethod.POST)
	public String addTheoremToDB(Model model, HttpSession session, HttpServletRequest req) {
		boolean isAdmin = checkIfAdmin(session);

		if (isAdmin) {
			Theorem theorem = new Theorem();

			getTheoremValues(req, theorem);
			localTheoremService.save(theorem);

			return Constants.redirect + manageTheorems;
		} else {
			return "redirect:/";
		}
	}

	private void getTheoremValues(HttpServletRequest req, Theorem theorem) {
		String theoremPers = req.getParameter("personality");
		int theoremPersNumber = Integer.parseInt(theoremPers);
		PersonalityType personality = localPersonalityTypeService.getById(theoremPersNumber);

		String denialValue = req.getParameter("denial");
		String recognitionValue = req.getParameter("recognition");
		String developmentValue = req.getParameter("development");
		double denial = Double.parseDouble(denialValue);
		double recognition = Double.parseDouble(recognitionValue);
		double development = Double.parseDouble(developmentValue);

		String text = req.getParameter("text");

		String theoremWeight = req.getParameter("weight");
		double weight = Double.parseDouble(theoremWeight);

		addToTheorem(theorem, personality, denial, recognition, development, text, weight);
	}

	/**
	 * Return one specific theorem, which was selected on the web page /managetheorems.
	 * It also loads all personality types, which will be displayed in a dropdown box.
	 */
	@RequestMapping(value="/editTheorem", method=RequestMethod.POST)
	public String editTheorem(Model model, HttpSession session, HttpServletRequest req) {
		boolean isAdmin = checkIfAdmin(session);
		
		if (isAdmin) {
			String theoremNumberString = req.getParameter("number");
			int theoremNumber = Integer.parseInt(theoremNumberString);
			Theorem theorem = localTheoremService.getById(theoremNumber);
			model.addAttribute("theorem", theorem);

			List<PersonalityType> personalityTypes = localPersonalityTypeService.getAll();
			model.addAttribute("personalityTypes", personalityTypes);

			return "editTheorem";
		} else {
			return Constants.redirect;
		}
	}
	
	/**
	 * After a theorem was selected for editing, this function processes the changes made,
	 * provided that the "save" button was pressed.
	 * Contains a lot of code, so code specific comments have been added.
	 */
	@RequestMapping(value="/updateTheorem", method=RequestMethod.POST)
	public String updateTheorem(Model model, HttpSession session, HttpServletRequest req) {
		boolean isAdmin = checkIfAdmin(session);
		
		if (isAdmin) {
			String theoremNumberString = req.getParameter("number");
			int theoremNumber = Integer.parseInt(theoremNumberString);
			Theorem theorem = localTheoremService.getById(theoremNumber);

			getTheoremValues(req, theorem);

			localTheoremService.update(theorem);

			return Constants.redirect + manageTheorems;
		} else {
			return Constants.redirect;
		}
	}

	private void addToTheorem(Theorem theorem, PersonalityType personality, double denial, double recognition, double development,
			String text, double weight) {
		theorem.setPersonalityType(personality);
		theorem.setDenial(denial);
		theorem.setRecognition(recognition);
		theorem.setDevelopment(development);
		theorem.setText(text);
		theorem.setWeight(weight);
	}

	/**
	 * Delete a theorem from the database.
	 */
	@RequestMapping(value="/deleteTheorem", method=RequestMethod.POST)
	public String deleteTheorem(Model model, HttpSession session, HttpServletRequest req) {
		boolean isAdmin = checkIfAdmin(session);

		if (isAdmin) {
			String theoremNumberString = req.getParameter("number");
			int theoremNumber = Integer.parseInt(theoremNumberString);
			Theorem theorem = localTheoremService.getById(theoremNumber);
			localTheoremService.delete(theorem);

			return Constants.redirect + manageTheorems;
		} else {
			return Constants.redirect;
		}
	}
	/**
	 * Sends message to users
	 */
    //Requirement buiten de sprint
	@RequestMapping(value="/sendMessages", method=RequestMethod.GET)
	public String sendMessages(Model model, HttpSession session, HttpServletRequest req) {
		boolean isAdmin = checkIfAdmin(session);
		
		if (isAdmin) {
			return "sendmessages";
		} else {
			return Constants.redirect;
		}
	}
	
    //Requirement buiten de sprint
	@RequestMapping(value="/userOverview", method=RequestMethod.GET)
	public String userOverview(Model model, HttpSession session, HttpServletRequest req) {
		boolean isAdmin = checkIfAdmin(session);
		
		if (isAdmin) {
			List<User> users = localUserService.getAll();
			model.addAttribute("users", users);
			return "useroverview";
		} else {
			return Constants.redirect;
		}
	}
	
    //Requirement buiten de sprint	
	@RequestMapping(value="/editResultTexts", method=RequestMethod.GET)
	public String editResultTexts(Model model, HttpSession session, HttpServletRequest req) {
		boolean isAdmin = checkIfAdmin(session);
		
		if (isAdmin) {
			return "editresulttexts";
		} else {
			return Constants.redirect;
		}
	}
	
    //Requirement buiten de sprint
	@RequestMapping(value="/editAnswerValues", method=RequestMethod.GET)
	public String editAnswerValues(Model model, HttpSession session, HttpServletRequest req) {
		boolean isAdmin = checkIfAdmin(session);
		
		if (isAdmin) {
			return "editanswervalues";
		} else {
			return Constants.redirect;
		}
	}
	
	/**
	 * Check whether someone is an admin, a regular user, or not logged in at all.
	 */
	public boolean checkIfAdmin(HttpSession session) {
		if(session.getAttribute(Constants.admin) != null) {
			return (boolean) session.getAttribute(Constants.admin);
		} else {
			String email = (String) session.getAttribute(Constants.email);
			User user = localUserService.findByName(email);
			if (user != null) {
				session.setAttribute(Constants.admin, user.isAdmin());
				return user.isAdmin();
			} else {
                return false;
            }
		}
	}
}