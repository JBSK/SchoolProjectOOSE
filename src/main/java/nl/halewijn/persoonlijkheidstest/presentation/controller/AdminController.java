package nl.halewijn.persoonlijkheidstest.presentation.controller;

import nl.halewijn.persoonlijkheidstest.domain.OpenQuestion;
import nl.halewijn.persoonlijkheidstest.domain.PersonalityType;
import nl.halewijn.persoonlijkheidstest.domain.Question;
import nl.halewijn.persoonlijkheidstest.domain.Result;
import nl.halewijn.persoonlijkheidstest.domain.Answer;
import nl.halewijn.persoonlijkheidstest.domain.Theorem;
import nl.halewijn.persoonlijkheidstest.domain.TheoremBattle;
import nl.halewijn.persoonlijkheidstest.domain.User;
import nl.halewijn.persoonlijkheidstest.services.PasswordHash;
import nl.halewijn.persoonlijkheidstest.services.local.LocalPersonalityTypeService;
import nl.halewijn.persoonlijkheidstest.services.local.LocalQuestionService;
import nl.halewijn.persoonlijkheidstest.services.local.LocalResultService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import nl.halewijn.persoonlijkheidstest.services.local.LocalTheoremService;
import nl.halewijn.persoonlijkheidstest.services.local.LocalUserService;

@Controller
public class AdminController {
	
	@Autowired
	private LocalUserService localUserService;
	
	@Autowired
	private LocalResultService localResultService;
	
	/**
	 * Check whether or not the user is an admin.
	 * 
	 * If the user is an admin, display the administration panel.
	 * 
	 * If the user is not an admin, or is not logged in, redirect to the previous page.
	 */
    @RequestMapping(value="/adminPaneel")
    public String showAdmin(Model model, HttpSession session, HttpServletRequest req, HttpServletResponse resp) {
	boolean isAdmin = checkIfAdmin(session);
		
		//Statistics:
		List<User> users = localUserService.getAll();
		model.addAttribute("users", users.size());
//		List<Result> tests = localResultService.getAll();
//		model.addAttribute("tests", tests);
		
		if (isAdmin) {
			return "adminDashboard";
		} else {
			return "redirect:/";
		}
	}
    
    @RequestMapping(value="/managequestions", method=RequestMethod.GET)
	public String vragenbeheren(Model model, HttpSession session, HttpServletRequest req) {
		boolean isAdmin = checkIfAdmin(session);
		
		if (isAdmin) {
			return "managequestions";
		} else {
			return "redirect:/";
		}
	}
	
	@RequestMapping(value="/managetheorems", method=RequestMethod.GET)
	public String stellingenbeheren(Model model, HttpSession session, HttpServletRequest req) {
		boolean isAdmin = checkIfAdmin(session);
		
		if (isAdmin) {
			return "managetheorems";
		} else {
			return "redirect:/";
		}
	}
	
	@RequestMapping(value="/sendmessages", method=RequestMethod.GET)
	public String berichtversturen(Model model, HttpSession session, HttpServletRequest req) {
		boolean isAdmin = checkIfAdmin(session);
		
		if (isAdmin) {
			return "sendmessages";
		} else {
			return "redirect:/";
		}
	}
	
	@RequestMapping(value="/useroverview", method=RequestMethod.GET)
	public String gebruikersoverzicht(Model model, HttpSession session, HttpServletRequest req) {
		boolean isAdmin = checkIfAdmin(session);
		
		if (isAdmin) {
			return "useroverview";
		} else {
			return "redirect:/";
		}
	}
	
	@RequestMapping(value="/editresulttexts", method=RequestMethod.GET)
	public String resultaattekstenbewerken(Model model, HttpSession session, HttpServletRequest req) {
		boolean isAdmin = checkIfAdmin(session);
		
		if (isAdmin) {
			return "editresulttexts";
		} else {
			return "redirect:/";
		}
	}
	
	@RequestMapping(value="/editanswervalues", method=RequestMethod.GET)
	public String antwoordwaardenaanpassen(Model model, HttpSession session, HttpServletRequest req) {
		boolean isAdmin = checkIfAdmin(session);
		
		if (isAdmin) {
			return "editanswervalues";
		} else {
			return "redirect:/";
		}
	}
	
	public boolean checkIfAdmin(HttpSession session) {
		return true;
//		String email = (String) session.getAttribute("email");
//		System.out.println(email);
//		String admin = "duncan@email.eu";
//		
//		if (email == admin) {
//			System.out.println("TEST");
//			return true;
//		} else {
//			System.out.println("FAIL");
//			return false;
//		}
	}
}