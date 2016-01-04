package nl.halewijn.persoonlijkheidstest.services;

import org.springframework.ui.Model;

import nl.halewijn.persoonlijkheidstest.domain.Button;
import nl.halewijn.persoonlijkheidstest.domain.Image;
import nl.halewijn.persoonlijkheidstest.services.local.LocalButtonService;
import nl.halewijn.persoonlijkheidstest.services.local.LocalImageService;

import java.util.HashMap;
import java.util.Map;

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
    public static final String utf8 = "UTF-8";

	private Constants() {

    }
	
	public static void menuItemsFromDatabase(Model model, LocalButtonService localButtonService, LocalImageService localImageService) {
		HashMap<String, Integer> buttons = new HashMap<>();
		HashMap<String, Integer> images = new HashMap<>();

		buttons.put("FirstButtonText", 1);
		buttons.put("SecondButtonText", 2);
		buttons.put("ThirdButtonText", 3);
		buttons.put("FourthButtonText", 4);
		buttons.put("FifthButtonText", 5);
		buttons.put("SixthButtonText", 6);
		buttons.put("SeventhButtonText", 7);
		images.put("FirstImage", 1);
		// TODO: add all other database entries

		for (Map.Entry<String, Integer> e : buttons.entrySet()) {
			Button button = localButtonService.getByButtonId(e.getValue());
			model.addAttribute(e.getKey(), button);
		}

		for (Map.Entry<String, Integer> e : images.entrySet()) {
			Image image = localImageService.getByImageId(e.getValue());
			model.addAttribute(e.getKey(), image);
		}

		// TODO: write a test for this method.
	}
}
