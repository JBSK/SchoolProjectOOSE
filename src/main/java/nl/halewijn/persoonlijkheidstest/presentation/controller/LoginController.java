package nl.halewijn.persoonlijkheidstest.presentation.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import nl.halewijn.persoonlijkheidstest.services.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import nl.halewijn.persoonlijkheidstest.domain.User;
import nl.halewijn.persoonlijkheidstest.services.PasswordHash;
import nl.halewijn.persoonlijkheidstest.services.local.LocalUserService;

@Controller
public class LoginController {
	
	@Autowired
	private LocalUserService localUserService;
	
	private PasswordHash passwordHash = new PasswordHash();
	
	/**
	 * If the file path relative to the base was "/login", return the "login" web page.
	 */
	@RequestMapping(value="/login", method=RequestMethod.GET)
    public String login(Model model, HttpServletRequest req) {
		String attempt;
		attempt = req.getParameter("attempt");
		model.addAttribute("attempt", attempt);
       return "login";
    }
	
	/**
	 * If someone presses the login button, check in the database whether this user exists.
	 * 
	 * If the user exists, check whether or not the password is correct.
	 * If the password is correct, add the user's email to the session, and redirect to the home page.
	 * 
	 * If the user doesn't exist, or the password is incorrect, return to the login page.
	 */
	@RequestMapping(value="/loginCheck", method=RequestMethod.POST)
	public String loginCheck(Model model, HttpSession session, HttpServletRequest req) {
		String email = req.getParameter(Constants.email);
		User user = localUserService.findByName(email);

		if (user != null) {
			boolean correctPassword = passwordHash.verifyPassword(req.getParameter("password"), user.getPasswordHash());
			
			if(user.getEmailAddress() != null && correctPassword) {
				session.setAttribute(Constants.email, user.getEmailAddress());
				session.setAttribute("admin", user.isAdmin());
			} else {
			
				return Constants.redirect + "login?attempt=wrong";
			}
		} else {

			return Constants.redirect + "login?attempt=empty";
		}
		return Constants.redirect;
	}
	
	/**
	 * If a logged in user presses the logout button, remove the user's email from the session.
	 */
	@RequestMapping(value="/logOut", method=RequestMethod.GET)
	public String logOut(Model model, HttpSession session) {
        session.invalidate();
        return Constants.redirect;
	}
	
	
}