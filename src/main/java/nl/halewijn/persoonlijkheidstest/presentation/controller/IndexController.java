package nl.halewijn.persoonlijkheidstest.presentation.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController implements ErrorController {

	private static final String PATH = "/error";

	@RequestMapping("/")
    public String index() {
        return "index";
    }
	
	@RequestMapping(value = PATH)
    public String error() {
        return "error";
    }

	@Override
	public String getErrorPath() {
		return PATH;
	}
}