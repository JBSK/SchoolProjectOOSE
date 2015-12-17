package nl.halewijn.persoonlijkheidstest.presentation.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import nl.halewijn.persoonlijkheidstest.domain.Result;
import nl.halewijn.persoonlijkheidstest.services.Constants;
import nl.halewijn.persoonlijkheidstest.services.local.LocalResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import nl.halewijn.persoonlijkheidstest.domain.User;
import nl.halewijn.persoonlijkheidstest.services.PasswordHash;
import nl.halewijn.persoonlijkheidstest.services.local.LocalUserService;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

@Controller
public class RegisterController {
	
	@Autowired
	private LocalUserService localUserService;

    @Autowired
    private LocalResultService localResultService;

    private static final String captchaApiSecretKey = "6LfACBMTAAAAAPIsxV8pRHsjQQVRA353jEK8OILp";
	private static final int minimumPasswordLength = 7;

	/**
	 * If the file path relative to the base was "/register", return the "register" web page.
	 */
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public String register(Model model, HttpServletRequest req) {
		String attempt = req.getParameter("attempt");
		model.addAttribute("attempt", attempt);
		model.addAttribute(Constants.minimumPasswordLength, minimumPasswordLength);
		return "register";
	}
	
	/**
	 * Check whether or not the email exists, and if the passwords match.
	 * If both of these are apply, then attempt to check with Google whether
     * the form was filled by a program (spam) or a real human. If human,
     * add the new user to the database, else display an error message.
	 */
	@RequestMapping(value="/registerDB", method=RequestMethod.POST)
    public String registerDB(Model model, HttpSession session, HttpServletRequest req) {
        String regEmail = req.getParameter("regEmail");
		String regPassword = req.getParameter("regPassword");
		String regPassword2 = req.getParameter("regPassword2");

		if (regPassword.equals(regPassword2)) {
			if (regPassword.length() >= minimumPasswordLength) {
                String captchaData = req.getParameter("g-recaptcha-response");
                if (captchaData != null && !"".equals(captchaData)) {
                    boolean captchaSuccess = verifyCaptchaResponse(captchaData, req.getRemoteAddr());
                    if (captchaSuccess) {
                        User doesUserExist = localUserService.findByEmailAddress(regEmail);
                        if (doesUserExist == null) {
                            return getUserInfo(session, regEmail, regPassword);
                        } else {
                            return Constants.redirect + "register?attempt=fail";
                        }
                    } else {
                        return Constants.redirect + "register?attempt=captcha";
                    }
                } else {
                    return Constants.redirect + "register?attempt=captcha";
                }
			} else {
				return Constants.redirect + "register?attempt=length";
			}
		} else {
			return Constants.redirect + "register?attempt=mismatch";
		}
    }

    /**
     * Submit the form data for the CAPTCHA to Google, they will verify whether
     * the form was filled by a spammer/robot/program or a human. We'll interpret
     * the JSON response from their API and return true if human, else false.
     */
    public boolean verifyCaptchaResponse(String captchaFormData, String clientIp) {
        try {
            captchaFormData = URLEncoder.encode(captchaFormData, "UTF-8");
            String encodedClientIp = URLEncoder.encode(clientIp, "UTF-8");
            String apiUrl = "https://www.google.com/recaptcha/api/siteverify?secret=" + captchaApiSecretKey + "&response=" + captchaFormData + "&remoteip=" + encodedClientIp;

            InputStream response = new URL(apiUrl).openStream();
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(response, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null) {
                responseStrBuilder.append(inputStr);
            }

            JSONObject json = JSONObject.fromObject(responseStrBuilder.toString());
            return json.getBoolean("success");
        } catch (Exception e) {
            return false;
        }
    }

	/**
	 * Gets the user information and adds it to the session
     */
	public String getUserInfo(HttpSession session, String regEmail, String regPassword) {
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
    public void linkTestResultInSessionToUser(HttpSession session, User user) {
         if (session.getAttribute(Constants.resultId) != null) {
             int resultId = (int) session.getAttribute(Constants.resultId);
             Result result = localResultService.getByResultId(resultId);
             result.setUser(user);
             localResultService.saveResult(result);
             session.setAttribute(Constants.resultId, null);
         }
    }
}