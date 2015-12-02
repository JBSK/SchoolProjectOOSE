package nl.halewijn.persoonlijkheidstest.presentation.controller;

import nl.halewijn.persoonlijkheidstest.domain.OpenQuestion;
import nl.halewijn.persoonlijkheidstest.domain.PersonalityType;
import nl.halewijn.persoonlijkheidstest.domain.Question;
import nl.halewijn.persoonlijkheidstest.domain.Theorem;
import nl.halewijn.persoonlijkheidstest.domain.TheoremBattle;
import nl.halewijn.persoonlijkheidstest.services.PasswordHash;
import nl.halewijn.persoonlijkheidstest.services.local.LocalPersonalityTypeService;
import nl.halewijn.persoonlijkheidstest.services.local.LocalQuestionService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import nl.halewijn.persoonlijkheidstest.services.local.LocalTheoremService;

@Controller
public class HelloController {

	@Autowired
	private LocalPersonalityTypeService localPersonalityTypeService;

    @Autowired
    private LocalTheoremService localTheoremService;
    
    @Autowired
    private LocalQuestionService localQuestionService;

	@RequestMapping("/")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "index";
    }
    
    @RequestMapping(value="/save")
    @ResponseBody
    public String create(@RequestParam(value="theoremtext", required=true, defaultValue="Het glas is altijd half leeg.") String argTheoremText, @RequestParam(value="weight", required=true, defaultValue="1.0") double argTheoremWeight) {
        try {
            PersonalityType optimist  = new PersonalityType("Optimist", "Je bent mega blij en positief en je ziet het goede in alle mensen.", "Het glas is altijd half vol.");
            localPersonalityTypeService.save(optimist);
            Theorem theorem = new Theorem(optimist, argTheoremText, argTheoremWeight, 0, 1, 0);
            localTheoremService.save(theorem);
            
            Theorem theorem2 = new Theorem(optimist, argTheoremText, argTheoremWeight, 1, 0, 0);
            localTheoremService.save(theorem);
            
            OpenQuestion openQ = new OpenQuestion("Is dit een open vraag? 1111111");
            localQuestionService.save(openQ);
            
            TheoremBattle theoremBattle = new TheoremBattle("Stelling Battle test!!!!", theorem, theorem2);
            localQuestionService.save(theoremBattle);
            
        } catch(Exception ex) {
            return ex.getMessage();
        }
      return "Success";
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
}