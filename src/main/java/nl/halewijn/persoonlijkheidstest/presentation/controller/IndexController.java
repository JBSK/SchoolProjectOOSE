package nl.halewijn.persoonlijkheidstest.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import nl.halewijn.persoonlijkheidstest.domain.Button;
import nl.halewijn.persoonlijkheidstest.domain.Image;
import nl.halewijn.persoonlijkheidstest.domain.WebsiteContentText;
import nl.halewijn.persoonlijkheidstest.services.Constants;
import nl.halewijn.persoonlijkheidstest.services.local.LocalButtonService;
import nl.halewijn.persoonlijkheidstest.services.local.LocalImageService;
import nl.halewijn.persoonlijkheidstest.services.local.LocalWebsiteContentTextService;

@Controller
public class IndexController {
	
	@Autowired
	private LocalWebsiteContentTextService localWebsiteContentTextService;
	
	@Autowired
	private LocalButtonService localButtonService;
	
	@Autowired
	private LocalImageService localImageService;
	
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
		
		Constants.menuItemsFromDatabase(model, localButtonService, localImageService);
		
		Button button8 = localButtonService.getByButtonId(8);
		model.addAttribute("EightButtonText", button8);
		
		Image image3 = localImageService.getByImageId(3);
		model.addAttribute("ThirdImage", image3);
		Image image4 = localImageService.getByImageId(4);
		model.addAttribute("FourthImage", image4);
		
        return "index";
    }
}