package nl.halewijn.persoonlijkheidstest.presentation.controller;

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
import nl.halewijn.persoonlijkheidstest.services.PasswordHash;
import nl.halewijn.persoonlijkheidstest.services.local.LocalUserService;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@ActiveProfiles("test")
public class LoginControllerTest {

	@Autowired
	private LoginController loginController;
	
	@Autowired
    private LocalUserService localUserService;
	
	@Test
	public void loginTest() {
		Model model = mock(Model.class);
		assertEquals("login", loginController.login(model));
	}
	
	@Test
	public void loginCheckTest() {
		Model model = mock(Model.class);
		HttpSession httpSession = mock(HttpSession.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		assertEquals("redirect:/login", loginController.loginCheck(model, httpSession, httpServletRequest));
		
		PasswordHash passwordHash = new PasswordHash();	
		User newUser = new User("email@mail.com");
		newUser.setPassword(passwordHash.hashPassword("aa"));
		localUserService.save(newUser);
		when(httpServletRequest.getParameter("email")).thenReturn("email@mail.com");
		when(httpServletRequest.getParameter("password")).thenReturn("test");
		
		assertEquals("email@mail.com", httpServletRequest.getParameter("email"));
		assertEquals("redirect:/login", loginController.loginCheck(model, httpSession, httpServletRequest));
		
		newUser.setPassword(passwordHash.hashPassword("test"));
		localUserService.save(newUser);
		
		loginController.loginCheck(model, httpSession, httpServletRequest);
		when(httpSession.getAttribute("email")).thenReturn("email@mail.com");
		assertEquals(newUser.getEmailAddress(), httpSession.getAttribute("email"));
		assertEquals("redirect:/", loginController.loginCheck(model, httpSession, httpServletRequest));
	}
	
	@Test
	public void logOutTest() {
		Model model = mock(Model.class);
		HttpSession httpSession = mock(HttpSession.class);

		loginController.logOut(model, httpSession);
		assertEquals(null, httpSession.getAttribute("email"));
	}
}
