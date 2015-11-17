package nl.halewijn.persoonlijkheidstest.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import nl.halewijn.persoonlijkheidstest.datasource.dao.StellingDao;
import nl.halewijn.persoonlijkheidstest.domain.Stelling;
import nl.halewijn.persoonlijkheidstest.services.IStellingService;
import nl.halewijn.persoonlijkheidstest.services.local.LocalStellingService;

@Controller
public class HelloController {

	@Autowired
	private LocalStellingService localStellingService;
	
    @RequestMapping("/")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }
    
    @RequestMapping(value="/save")
    @ResponseBody
    public String create(@RequestParam(value="stelling", required=false, defaultValue="Nieuwe stelling") String stelling, @RequestParam(value="weging", required=false, defaultValue="1.0") double weging) {
      try {
         Stelling stel = new Stelling(stelling, weging);
         localStellingService.save(stel);
      }
      catch(Exception ex) {
        return ex.getMessage();
      }
      return "Success";
    }

}