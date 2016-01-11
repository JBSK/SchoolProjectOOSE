package nl.halewijn.persoonlijkheidstest.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import nl.halewijn.persoonlijkheidstest.application.domain.Image;
import nl.halewijn.persoonlijkheidstest.application.domain.WebsiteContentText;
import nl.halewijn.persoonlijkheidstest.application.services.Constants;
import nl.halewijn.persoonlijkheidstest.application.services.local.LocalButtonService;
import nl.halewijn.persoonlijkheidstest.application.services.local.LocalImageService;
import nl.halewijn.persoonlijkheidstest.application.services.local.LocalWebsiteContentTextService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class CustomErrorController implements ErrorController {

    private static final String PATH = "/error";

    @Value("${errorPageShowStackTrace}")
    private boolean errorPageShowStackTrace;

    @Autowired
    private ErrorAttributes errorAttributes;
    
    @Autowired
    private LocalWebsiteContentTextService localWebsiteContentTextService;
    
    @Autowired
    private LocalImageService localImageService;
    
    @Autowired
    private LocalButtonService localButtonService;

	/**
	 * A controller method that displays the error page. The error information is retrieved from the HttpServletRequest
	 * and placed on the model so that it can be displayed on the error page.
	 */
    @RequestMapping(value = PATH)
    public String error(HttpServletRequest request, HttpServletResponse response, Model model) {
		WebsiteContentText text10 = localWebsiteContentTextService.getByContentId(10);
		model.addAttribute("TenthContentBox", text10);
		
		Image image6 = localImageService.getByImageId(6);
		model.addAttribute("SixthImage", image6);
		
		Constants.menuItemsFromDatabase(model, localButtonService, localImageService);
		
    	model.addAttribute("status", response.getStatus());
    	
    	Map<String, Object> errorAttributesMap = getErrorAttributes(request, errorPageShowStackTrace);
        model.addAttribute("error", (String) errorAttributesMap.get("error"));
        model.addAttribute("message", (String) errorAttributesMap.get("message"));
        model.addAttribute("timeStamp", errorAttributesMap.get("timestamp").toString());
        model.addAttribute("trace", (String) errorAttributesMap.get("trace"));
        return "errorPage";
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
    }
}
