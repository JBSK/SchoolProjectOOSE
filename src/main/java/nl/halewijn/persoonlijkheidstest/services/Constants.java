package nl.halewijn.persoonlijkheidstest.services;

import org.springframework.ui.Model;

import nl.halewijn.persoonlijkheidstest.domain.Button;
import nl.halewijn.persoonlijkheidstest.domain.Image;
import nl.halewijn.persoonlijkheidstest.services.local.LocalButtonService;
import nl.halewijn.persoonlijkheidstest.services.local.LocalImageService;

public class Constants {

    /*
     * Serves to resolve some 'major' issues in Sonar.
     */
    public static final String currentQuestion = "currentQuestion";
    public static final String admin = "admin";
    public static final String email = "email";
    public static final String linkpage = "linkpage";
    public static final String login = "login";
    public static final String minimumPasswordLength = "minimumPasswordLength";
    public static final String myresults = "myresults";
    public static final String password = "password";
    public static final String questionnaire = "questionnaire";
    public static final String redirect = "redirect:/"; // redirects to index
    public static final String result = "result";
    public static final String resultId = "result_id";

	private Constants() {

    }
	
	public static void menuItemsFromDatabase(Model model, LocalButtonService localButtonService, LocalImageService localImageService) {
		Button button1 = localButtonService.getByButtonId(1);
		model.addAttribute("FirstButtonText", button1);
		
		Button button2 = localButtonService.getByButtonId(2);
		model.addAttribute("SecondButtonText", button2);
		
		Button button3 = localButtonService.getByButtonId(3);
		model.addAttribute("ThirdButtonText", button3);
		
		Button button4 = localButtonService.getByButtonId(4);
		model.addAttribute("FourthButtonText", button4);
		
		Button button5 = localButtonService.getByButtonId(5);
		model.addAttribute("FifthButtonText", button5);
		
		Button button6 = localButtonService.getByButtonId(6);
		model.addAttribute("SixthButtonText", button6);
		
		Button button7 = localButtonService.getByButtonId(7);
		model.addAttribute("SeventhButtonText", button7);
		
		Image image1 = localImageService.getByImageId(1);
		model.addAttribute("FirstImage", image1);
	}
}
