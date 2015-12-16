package nl.halewijn.persoonlijkheidstest.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import nl.halewijn.persoonlijkheidstest.domain.Image;
import nl.halewijn.persoonlijkheidstest.domain.WebsiteContentText;
import nl.halewijn.persoonlijkheidstest.services.local.LocalImageService;
import nl.halewijn.persoonlijkheidstest.services.local.LocalWebsiteContentTextService;

@Controller
public class IndexController {
	
	@Autowired
	private LocalWebsiteContentTextService localWebsiteContentTextService;
	
	@RequestMapping("/")
    public String index(Model model) {
		WebsiteContentText text1 = localWebsiteContentTextService.getByContentId(1);
		model.addAttribute("FirstContentBox", text1);
		
		WebsiteContentText text2 = localWebsiteContentTextService.getByContentId(2);
		model.addAttribute("SecondContentBox", text2);
		
		WebsiteContentText text3 = localWebsiteContentTextService.getByContentId(3);
		model.addAttribute("ThirdContentBox", text3);
		
		WebsiteContentText text4 = localWebsiteContentTextService.getByContentId(4);
		model.addAttribute("FourthContentBox", text4);
        return "index";
    }
}