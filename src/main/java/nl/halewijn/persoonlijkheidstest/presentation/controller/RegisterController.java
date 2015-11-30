package nl.halewijn.persoonlijkheidstest.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RegisterController {

	@RequestMapping(value="/register", method= RequestMethod.POST)
    public String register(Model model) {
       return "register";
    }

}