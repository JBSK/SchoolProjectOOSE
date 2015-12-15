package nl.halewijn.persoonlijkheidstest.presentation.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import nl.halewijn.persoonlijkheidstest.domain.Result;
import nl.halewijn.persoonlijkheidstest.services.Constants;
import nl.halewijn.persoonlijkheidstest.services.local.LocalResultService;
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

    @Autowired
    private LocalResultService localResultService;
	
	/**
	 * If the file path relative to the base was "/register", return the "register" web page.
	 */
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public String register(Model model, HttpServletRequest req) {
		String attempt = req.getParameter("attempt");
		model.addAttribute("attempt", attempt);
		return "register";
	}
	
	/**
	 * Check whether or not the email exists, and if the passwords match.
	 * If both of these are apply, add the new user to the database.
	 * Else, display an error message.
	 */
	@RequestMapping(value="/registerDB", method=RequestMethod.POST)
    public String registerDB(Model model, HttpSession session, HttpServletRequest req) {
        String regEmail = req.getParameter("regEmail");
		String regPassword = req.getParameter("regPassword");
		String regPassword2 = req.getParameter("regPassword2");

		User doesUserExist = localUserService.findByEmailAddress(regEmail);

		if (doesUserExist == null) {
			if (regPassword.equals(regPassword2)) {
				return getUserInfo(session, regEmail, regPassword);
			} else {
                return Constants.redirect + "register?attempt=mismatch";
			}
		} else {
            return Constants.redirect + "register?attempt=fail";
        }
    }

	/**
	 * Gets the user information and adds it to the session
     */
	private String getUserInfo(HttpSession session, String regEmail, String regPassword) {
		User user = new User(regEmail, false);
		final PasswordHash passwordHash = new PasswordHash();
		user.setPasswordHash(passwordHash.hashPassword(regPassword));
		user = localUserService.save(user);
		session.setAttribute(Constants.email, user.getEmailAddress());
		session.setAttribute("admin", user.isAdmin());
        linkTestResultInSessionToUser(session, user);
		return Constants.redirect;
	}

    /*
     * Check if we have just finished a test, if yes:
     * - Retrieve the result ID,
     * - Lookup the corresponding Result,
     * - Change the user value to the currently logged-in user,
     * - Update the result in the database,
     * - And clear the stored result ID in the session.
     */
    private void linkTestResultInSessionToUser(HttpSession session, User user) {
         // TODO: write a jUnit test
         if (session.getAttribute(Constants.resultId) != null) {
             int resultId = (int) session.getAttribute(Constants.resultId);
             Result result = localResultService.getByResultId(resultId);
             result.setUser(user);
             localResultService.saveResult(result);
             session.setAttribute(Constants.resultId, null);
         }
    }
}