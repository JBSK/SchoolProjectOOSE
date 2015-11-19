package nl.halewijn.persoonlijkheidstest.presentation.controller;

import nl.halewijn.persoonlijkheidstest.datasource.dao.PersonalityTypeDao;
import nl.halewijn.persoonlijkheidstest.domain.PersonalityType;
import nl.halewijn.persoonlijkheidstest.domain.Theorem;
import nl.halewijn.persoonlijkheidstest.services.local.LocalPersonalityTypeService;
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
	
    @RequestMapping("/")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }
    
    @RequestMapping(value="/save")
    @ResponseBody
    public String create(@RequestParam(value="theoremtext", required=true, defaultValue="Het glas is altijd half leeg.") String argTheoremText, @RequestParam(value="weight", required=true, defaultValue="1.0") double argTheoremWeight) {
        try {
            PersonalityType optimist  = new PersonalityType("Optimist", "Je bent mega blij en positief en je ziet het goede in alle mensen.", "Het glas is altijd half vol.");
            localPersonalityTypeService.save(optimist);
            Theorem theorem = new Theorem(optimist, argTheoremText, argTheoremWeight);
            localTheoremService.save(theorem);
        } catch(Exception ex) {
            return ex.getMessage();
        }
      return "Success";
    }
    
    @RequestMapping(value="/stellingen")
    public String showStellingen(Model model) {
        model.addAttribute("stellingen", localTheoremService.getAll());

      return "stellingen";
    }

}