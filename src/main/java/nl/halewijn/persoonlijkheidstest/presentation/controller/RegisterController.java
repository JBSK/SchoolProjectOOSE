package nl.halewijn.persoonlijkheidstest.presentation.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import nl.halewijn.persoonlijkheidstest.domain.User;
import nl.halewijn.persoonlijkheidstest.services.PasswordHash;
import nl.halewijn.persoonlijkheidstest.services.local.LocalUserService;

@Controller
public class RegisterController {
	
	@Autowired
	private LocalUserService localUserService;
	
	/**
	 * If the file path relative to the base was "/register", return the "register" web page.
	 */
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public String register(Model model, HttpServletRequest req) {
		return "register";
	}
	
	/**
	 * Check whether or not the email exists, and if the passwords match.
	 * If both of these are apply, add the new user to the database.
	 * Else, display an error message.
	 */
	@RequestMapping(value="/registerDB", method=RequestMethod.POST)
    public String registerDB(Model model, HttpServletRequest req) {
		String regEmail = req.getParameter("regEmail");
		String regPassword = req.getParameter("regPassword");
		String regPassword2 = req.getParameter("regPassword2");
		
		User doesUserExist = localUserService.findByName(regEmail);
		
		if (doesUserExist == null && regPassword.equals(regPassword2)) {
			User user = new User(regEmail, false);
			final PasswordHash passwordHash = new PasswordHash();
			user.setPasswordHash(passwordHash.hashPassword(regPassword));
			localUserService.save(user);
		}
		
		return "redirect:/";
    }
}