package nl.halewijn.persoonlijkheidstest.presentation.controller;

import nl.halewijn.persoonlijkheidstest.domain.Question;
import nl.halewijn.persoonlijkheidstest.services.PasswordHash;
import nl.halewijn.persoonlijkheidstest.services.local.LocalQuestionService;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

	///
	@Autowired
	LocalQuestionService qService;
	///
	
	@RequestMapping("/")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        Question firstQuestion = qService.getByQuestionId(1);
		model.addAttribute("name", name);
        return "index";
    }
    
    @RequestMapping(value="/resultpagina")
    public String showTheorems(Model model) {
        return "result";
    }
    

    @RequestMapping(value="/hashpassword")
    @ResponseBody
    public String hash(@RequestParam(value="password", required=true, defaultValue="abc") String argPassword) {
        return new PasswordHash().hashPassword(argPassword);
    }

    @RequestMapping(value="/verifypassword")
    @ResponseBody
    public boolean verify(@RequestParam(value="password", required=true, defaultValue="abc") String argPassword, @RequestParam(value="hash", required=true, defaultValue="abc") String argHash) {
        return new PasswordHash().verifyPassword(argPassword, argHash);
    }
    
    @RequestMapping(value="/fakelogin")
    @ResponseBody
    public String verify(HttpSession httpSession) {
    	httpSession.setAttribute("userName", "jackbriesen@mail.com");
		return "Logged in";	
    }
}