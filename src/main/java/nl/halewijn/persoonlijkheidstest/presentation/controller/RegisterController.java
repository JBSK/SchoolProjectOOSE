package nl.halewijn.persoonlijkheidstest.presentation.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RegisterController {
	/**
	 * If the file path relative to the base was "/register", return the "register" web page.
	 */
	@RequestMapping(value="/register", method= RequestMethod.GET)
    public String register(Model model, HttpServletRequest req) {
		String regEmail;
		regEmail = req.getParameter("regEmail");
		String regPassword;
		regPassword = req.getParameter("regPassword");
		String regPassword2;
		regPassword2 = req.getParameter("regPassword2");
		model.addAttribute("regEmail", regEmail);
		model.addAttribute("regPassword", regPassword);
		model.addAttribute("regPassword2", regPassword2);
		
       return "register";
    }
}