package nl.halewijn.persoonlijkheidstest.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
	/**
	 * If the file path relative to the base was "/login", return the "login" web page.
	 */
	@RequestMapping(value="/login", method=RequestMethod.GET)
    public String login(Model model) {
       return "login";
    }
}