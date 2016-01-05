package nl.halewijn.persoonlijkheidstest.presentation.controller;

import nl.halewijn.persoonlijkheidstest.domain.Result;
import nl.halewijn.persoonlijkheidstest.services.local.LocalResultService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import nl.halewijn.persoonlijkheidstest.Application;
import nl.halewijn.persoonlijkheidstest.domain.User;
import nl.halewijn.persoonlijkheidstest.services.Constants;
import nl.halewijn.persoonlijkheidstest.services.PasswordHash;
import nl.halewijn.persoonlijkheidstest.services.local.LocalUserService;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@ActiveProfiles("test")
public class RegisterControllerTest {
	
	@Autowired
	private RegisterController registerController;
	
	@Autowired
	private LocalUserService localUserService;

    @Autowired
    private LocalResultService localResultService;
	
	@Test
	public void registerTest() {
		Model model = mock(Model.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		assertEquals("register", registerController.register(model, httpServletRequest));
	}
	
	@Test
	public void registerDBTest() {
		Model model = mock(Model.class);
		HttpSession httpSession = mock(HttpSession.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		when(httpServletRequest.getParameter("regEmail")).thenReturn("testMail@mail.nl");
		when(httpServletRequest.getParameter("regPassword")).thenReturn("password");
		when(httpServletRequest.getParameter("regPassword2")).thenReturn("password");
		
		assertNull(localUserService.findByEmailAddress("testMail@mail.nl"));
		
		when(httpServletRequest.getParameter("regPassword2")).thenReturn("password2");
		assertEquals(Constants.redirect + "register?attempt=mismatch", registerController.registerDB(model, httpSession, httpServletRequest));

		when(httpServletRequest.getParameter("regPassword")).thenReturn("1");
		when(httpServletRequest.getParameter("regPassword2")).thenReturn("1");
		assertEquals(Constants.redirect + "register?attempt=length", registerController.registerDB(model, httpSession, httpServletRequest));

		when(httpServletRequest.getParameter("regPassword")).thenReturn("password");
		when(httpServletRequest.getParameter("regPassword2")).thenReturn("password");
		assertEquals(Constants.redirect + "register?attempt=captcha", registerController.registerDB(model, httpSession, httpServletRequest));

		User user = new User("testMail@mail.nl", false);
		PasswordHash passwordHash = new PasswordHash();
		user.setPasswordHash(passwordHash.hashPassword("password"));
		localUserService.save(user);
		assertEquals(Constants.redirect + "register?attempt=captcha", registerController.registerDB(model,  httpSession, httpServletRequest));
	
		when(httpServletRequest.getParameter("g-recaptcha-response")).thenReturn("test");
		assertEquals(Constants.redirect + "register?attempt=captcha", registerController.registerDB(model,  httpSession, httpServletRequest));
	}	
	
	@Test
	public void getUserInfoTest() {
        HttpSession httpSession = mock(HttpSession.class);
        String testMail = "testmail@mail.com";
        String testPassword = "test";
		assertEquals(Constants.redirect, registerController.getUserInfo(httpSession, testMail, testPassword));
	}

	@Test
	public void linkTestResultInSessionToUserTest() {
        HttpSession httpSession = mock(HttpSession.class);

        Result result1 = new Result();
        result1 = localResultService.saveResult(result1);
        assertNull(result1.getUser());

        User user = new User("test@test.com", false);
        user.setPasswordHash("test");
        localUserService.save(user);

        when(httpSession.getAttribute(Constants.resultId)).thenReturn(result1.getId());
        registerController.linkTestResultInSessionToUser(httpSession, user);
        result1 = localResultService.getByResultId(result1.getId());
        assertEquals(user, result1.getUser());
	}

	@Test
	public void verifyCaptchaResponseTest() {
        String captchaFormData = "";
        String clientIp = "127.0.0.1";

        boolean verificationResult = registerController.verifyCaptchaResponse(captchaFormData, clientIp, Constants.utf8);
        assertEquals(false, verificationResult);  
	}
	
	@Test
	public void verifyCaptchaResponseExceptionTest() {
        String captchaFormData = "asda&sd";
        String clientIp = "asda&ds";

        boolean verificationResult = registerController.verifyCaptchaResponse(captchaFormData, clientIp, "--");
        assertEquals(false, verificationResult);  
	}

}