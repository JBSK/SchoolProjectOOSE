package nl.halewijn.persoonlijkheidstest.presentation.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import nl.halewijn.persoonlijkheidstest.domain.*;
import nl.halewijn.persoonlijkheidstest.services.PasswordHash;
import nl.halewijn.persoonlijkheidstest.services.Constants;
import nl.halewijn.persoonlijkheidstest.services.local.LocalButtonService;
import nl.halewijn.persoonlijkheidstest.services.local.LocalImageService;
import nl.halewijn.persoonlijkheidstest.services.local.LocalPersonalityTypeService;
import nl.halewijn.persoonlijkheidstest.services.local.LocalResultService;
import nl.halewijn.persoonlijkheidstest.services.local.LocalUserService;
import nl.halewijn.persoonlijkheidstest.services.local.LocalWebsiteContentTextService;

@Controller
public class UserController {
	
	@Autowired
	private LocalUserService localUserService;
	
	@Autowired
	private LocalResultService localResultService;
	
	@Autowired
	private LocalPersonalityTypeService localPersonalityTypeService;
	
	@Autowired
    private LocalWebsiteContentTextService localWebsiteContentTextService;
	
	@Autowired
	private LocalButtonService localButtonService;
	
	@Autowired
	private LocalImageService localImageService;
	
	private PasswordHash passwordHash = new PasswordHash();
	private static final int minimumPasswordLength = 7;

	/**
	 * Check whether there is an email address in the browser session.
	 * If not, redirect to the home page.
	 * 
	 * If yes, find the user is associated with this email.
	 * Then, find the finished questionnaires by this user.
	 * Return a web page where these are all displayed.
	 */
	@RequestMapping(value="/myresults")
	public String myResults(Model model, HttpSession session, HttpServletRequest request) {
		if (session.getAttribute(Constants.email) != null) {
            String email = session.getAttribute(Constants.email).toString();
            if (!"".equals(email)) {
                int id = localUserService.findByEmailAddress(email).getId();
                List<Result> userResults = localResultService.getByUserId(id);          
                setResultPrimaryType(userResults);
                model.addAttribute("userResults", userResults);
                
                WebsiteContentText text9 = localWebsiteContentTextService.getByContentId(9);
        		model.addAttribute("NinthContentBox", text9);

        		Constants.menuItemsFromDatabase(model, localButtonService, localImageService);
        		Button button12 = localButtonService.getByButtonId(12);
        		model.addAttribute("TwelfthButtonText", button12);
        		
                return Constants.myresults;
            }
		}
		return Constants.redirect;
	}

	public void setResultPrimaryType(List<Result> userResults) {
		for(Result result : userResults) {
			List<ResultTypePercentage> typePercentages = localResultService.findResultTypePercentageByResult(result);
			ResultTypePercentage primaryTypePercentage = null;
			for (ResultTypePercentage typePercentage : typePercentages) {
				if (primaryTypePercentage == null) {
					primaryTypePercentage = typePercentage;
				} else if(typePercentage.getPercentage() > primaryTypePercentage.getPercentage()) {
                    primaryTypePercentage = typePercentage;
				}
			}
			result.setPrimaryType(primaryTypePercentage.getPersonalityType());
		}
	}

    @RequestMapping(value="/showResult", method=RequestMethod.GET)
    public String showResultGET(Model model, HttpSession session, HttpServletRequest request) {
        return Constants.redirect;
    }

	/**
	 * Currently makes use of the same result page that is shown right after finishing a questionnaire.
	 * 
	 * Loads the result percentages et cetera, from the database, based on the ID of the result that was selected.
	 * These values are then displayed on a web page.
	 */
	@RequestMapping(value="/showResult", method=RequestMethod.POST)
	public String showResult(Model model, HttpSession session, HttpServletRequest request) {
        int resultIdParam = Integer.parseInt(request.getParameter("number"));
		Result result = localResultService.getByResultId(resultIdParam);
        if (result != null && result.getUser() != null) {
            User user = result.getUser();
            String emailInSession = (String) session.getAttribute(Constants.email);
            if (user != null && user.getEmailAddress().equalsIgnoreCase(emailInSession)) {
                getResultPageData(model, result);
                loadContentFromDatabase(model);
                return Constants.result;
            }
        } 
        return Constants.redirect;
	}

	private void getResultPageData(Model model, Result result) {
		Questionnaire questionnaire = new Questionnaire();

		double[] pTypeResultArray = findAllResultTypePercentages(result);
		model.addAttribute("scores", pTypeResultArray);

		double[] subTypeResultArray = findAllSubTypePercentages(result);
		model.addAttribute("subTypeScores", subTypeResultArray);

		String[] personalityTypes = questionnaire.getPersonalityTypesFromDb(localPersonalityTypeService);
		model.addAttribute("personalityTypes", personalityTypes);
		
		double[] pTypeResultArrayCopy = Arrays.copyOf(pTypeResultArray, pTypeResultArray.length);
		int primaryPersonalityTypeID = questionnaire.addPrimaryPersonalityTypeToModel(model, localPersonalityTypeService, pTypeResultArrayCopy);
		pTypeResultArrayCopy[primaryPersonalityTypeID - 1] = 0;
		questionnaire.addSecondaryPersonalityTypeToModel(model, localPersonalityTypeService, pTypeResultArrayCopy);
	}

	private void loadContentFromDatabase(Model model) {
		WebsiteContentText text5 = localWebsiteContentTextService.getByContentId(5);
		model.addAttribute("FifthContentBox", text5);
		
		Constants.menuItemsFromDatabase(model, localButtonService, localImageService);
		
		Button button10 = localButtonService.getByButtonId(10);
		model.addAttribute("TenthButtonText", button10);
		
		Button button11 = localButtonService.getByButtonId(11);
		model.addAttribute("EleventhButtonText", button11);
		
		Image image2 = localImageService.getByImageId(2);
		model.addAttribute("SecondImage", image2);
	}

	private double[] findAllSubTypePercentages(Result result) {
		double[] subTypeResultArray = new double[3];
		subTypeResultArray[0] = localResultService.getByResultId(result.getId()).getScoreDenial();
		subTypeResultArray[1] = localResultService.getByResultId(result.getId()).getScoreRecognition();
		subTypeResultArray[2] = localResultService.getByResultId(result.getId()).getScoreDevelopment();
		
        for (int i = 0; i < subTypeResultArray.length; i++) {
            subTypeResultArray[i] = subTypeResultArray[i] / 100;
        }
		return subTypeResultArray;
	}

	private double[] findAllResultTypePercentages(Result result) {
		List<ResultTypePercentage> findAllResultTypePercentages = localResultService.findResultTypePercentageByResult(result);
		double[] pTypeResultArray = new double[findAllResultTypePercentages.size()];
		for (int i = 0; i < pTypeResultArray.length; i++) {
		    if (result.getId() == findAllResultTypePercentages.get(i).getResult().getId()) {
		        pTypeResultArray[i] = findAllResultTypePercentages.get(i).getPercentage();
		    }
		}
		return pTypeResultArray;
	}
	
	/**
	 * A controller method that redirects to the contact page.
	 */
	@RequestMapping("/contact")
    public String contact(Model model) {
		WebsiteContentText text7 = localWebsiteContentTextService.getByContentId(7);
		model.addAttribute("SeventhContentBox", text7);
		
		Constants.menuItemsFromDatabase(model, localButtonService, localImageService);
		
        return "contact";
    }
	
	/**
	 * A controller method that redirects to the about us page.
	 */
	@RequestMapping("/aboutUs")
    public String aboutUs(Model model) {
		WebsiteContentText text8 = localWebsiteContentTextService.getByContentId(8);
		model.addAttribute("EigthContentBox", text8);
		
		Constants.menuItemsFromDatabase(model, localButtonService, localImageService);
		
		return "aboutUs";
    }

	/**
	 * Display the 'change password' web page.
	 */
    @RequestMapping(value="/changePassword", method=RequestMethod.GET)
	public String changePassword(Model model, HttpSession session, HttpServletRequest req) {
        if (session.getAttribute(Constants.email) != null) {
            Constants.menuItemsFromDatabase(model, localButtonService, localImageService);
            String attempt = req.getParameter(Constants.attempt);
            model.addAttribute(Constants.attempt, attempt);
            model.addAttribute(Constants.minimumPasswordLength, minimumPasswordLength);
            return "changePassword";
        } else {
            return Constants.redirect;
        }
	}
	
	/**
	 * Check whether a user is logged in. If yes, check whether the old password is correct.
	 * Then make sure the new password matches and whether it complies with the set standards.
	 * If this is the case, the new password is hashed and saved in the database and the user is logged out.
	 */
    @RequestMapping(value="/changePassword", method=RequestMethod.POST)
	public String changePasswordCheck(Model model, HttpSession session, HttpServletRequest req) {
		Constants.menuItemsFromDatabase(model, localButtonService, localImageService);

		if (session.getAttribute(Constants.email) != null) {
            String email = session.getAttribute(Constants.email).toString();
            if (!"".equals(email)) {
            	User user = localUserService.findByEmailAddress(email);
            	String oldPassword = req.getParameter("oldPassword");
            	String newPassword = req.getParameter("newPassword");
            	String newPassword2 = req.getParameter("newPassword2");

            	if (user != null) {
            		String oldPasswordDB = user.getPasswordHash();
            		boolean oldPasswordCorrect = passwordHash.verifyPassword(oldPassword, oldPasswordDB);
	            	if (oldPasswordCorrect) {
	            		if (newPassword.equals(newPassword2)) {
	            			if (newPassword.length() >= minimumPasswordLength) {
	            				user.setPasswordHash(passwordHash.hashPassword(newPassword));
	            				localUserService.save(user);
	            				return Constants.redirect + "logOut";
	            			} else {
                                return Constants.redirect + "changePassword?attempt=length";
                            }
	            		} else {
                            return Constants.redirect + "changePassword?attempt=mismatch";
                        }
        			} else {
                        return Constants.redirect + "changePassword?attempt=mismatch";
                    }
            	} else {
                    return Constants.redirect;
                }
            } else {
                return Constants.redirect;
            }
		} else {
            return Constants.redirect;
        }
	}

	/**
	 * If the file path relative to the base was "/changeEmail", return the "changeEmail" web page.
	 */
	@RequestMapping(value="/changeEmail", method=RequestMethod.GET)
    public String changeEmail(Model model, HttpSession session, HttpServletRequest req) {
		if (session.getAttribute(Constants.email) != null) {
			String attempt = req.getParameter(Constants.attempt);
			model.addAttribute(Constants.attempt, attempt);
			Constants.menuItemsFromDatabase(model, localButtonService, localImageService);
			return "changeEmail";
		} else {
			return Constants.redirect;
		}
	}

	/**
	 * If someone presses the change password button, check in the database whether this user exists.
	 *
	 * If the user exists, check whether or not the password is correct.
	 * If the password is correct, check whether or not the new emailaddress isn't empty.
     * If it isn't, then check if the new emailaddress is already registered to someone else.
	 * If it isn't, change the emailaddress on the user, save the modified user,
     * alter the user's new email in the session, and then log them out as a security precaution.
	 *
	 * If the user doesn't exist, or the password is incorrect, or the emailaddress is empty or already in use,
     * then return to the change password page and show an error message.
	 */
	@RequestMapping(value="/changeEmail", method=RequestMethod.POST)
	public String changeEmailCheck(Model model, HttpSession session, HttpServletRequest req) {
        if (session.getAttribute(Constants.email) != null) {
            String email = session.getAttribute(Constants.email).toString();
            User user = localUserService.findByEmailAddress(email);
            Constants.menuItemsFromDatabase(model, localButtonService, localImageService);
            if (user != null) {
                boolean correctPassword = passwordHash.verifyPassword(req.getParameter(Constants.password), user.getPasswordHash());
                if (correctPassword) {
                    String newEmail = req.getParameter(Constants.email);
                    if (newEmail != null && !"".equals(newEmail)) {
                        if (localUserService.findByEmailAddress(newEmail) == null) {
                            user.setEmailAddress(newEmail);
                            localUserService.save(user);
                            return Constants.redirect + "logOut";
                        } else {
                            return Constants.redirect + "changeEmail?attempt=wrong";
                        }
                    } else {
                        return Constants.redirect + "changeEmail?attempt=empty";
                    }
                } else {
                    return Constants.redirect + "changeEmail?attempt=wrong";
                }
            } else {
                return Constants.redirect + "changeEmail?attempt=wrong";
            }
        } else {
            return Constants.redirect;
        }
	}
}