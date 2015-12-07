package nl.halewijn.persoonlijkheidstest.presentation.controller;

import nl.halewijn.persoonlijkheidstest.domain.Theorem;
import nl.halewijn.persoonlijkheidstest.domain.User;
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
	public String managetheorems(Model model, HttpSession session, HttpServletRequest req) {
		boolean isAdmin = checkIfAdmin(session);
		
		if (isAdmin) {
			List<Theorem> theorems = localTheoremService.getAll();
			model.addAttribute("theorems", theorems);
			return "managetheorems";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value="/editTheorem", method=RequestMethod.POST)
	public String editTheorem(Model model, HttpSession session, HttpServletRequest req) {
		boolean isAdmin = checkIfAdmin(session);
		
		if (isAdmin) {
			String theoremNumber = req.getParameter("aanpassen");
			 
			model.addAttribute("theorem", theoremNumber);
			return "editTheorem";
		} else {
			return "redirect:/";
		}
	}
	
	@RequestMapping(value="/updateTheorem", method=RequestMethod.POST)
	public String updateTheorem(Model model, HttpSession session, HttpServletRequest req) {
		boolean isAdmin = checkIfAdmin(session);
		
		if (isAdmin) {
			
			return "updateTheorem";

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
		String email = (String) session.getAttribute("email");
		String admin = "duncan@email.eu";
		System.out.println(email);
		System.out.println(admin);
		if (email.equals(admin)) {
			System.out.println("ADMIN! :D");
			return true;
		} else {
			System.out.println("FAIL");
			return false;
		}
	}
}