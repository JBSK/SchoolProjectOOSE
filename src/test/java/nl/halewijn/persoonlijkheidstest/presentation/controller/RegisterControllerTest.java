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
import nl.halewijn.persoonlijkheidstest.services.Constants;
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
public class RegisterControllerTest {
	
	@Autowired
	private RegisterController registerController;
	
	@Autowired
	private LocalUserService localUserService;
	
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
		
		when(httpServletRequest.getParameter("regEmail")).thenReturn("testMail1@mail1.nl");
		when(httpServletRequest.getParameter("regPassword")).thenReturn("password");
		when(httpServletRequest.getParameter("regPassword2")).thenReturn("password");
		
		assertNull(localUserService.findByName("testMail@mail1.nl"));
		assertEquals(Constants.redirect, registerController.registerDB(model, httpSession, httpServletRequest));
		
		when(httpServletRequest.getParameter("regEmail")).thenReturn("testMail2@mail.nl");
		when(httpServletRequest.getParameter("regPassword")).thenReturn("password");
		when(httpServletRequest.getParameter("regPassword2")).thenReturn("password2");
		assertEquals(Constants.redirect + "register?attempt=mismatch", registerController.registerDB(model, httpSession, httpServletRequest));
		
		User user = new User("testMail3@mail.nl", false);
		PasswordHash passwordHash = new PasswordHash();
		user.setPasswordHash(passwordHash.hashPassword("password"));
		localUserService.save(user);
		//when(localUserService.findByName("testMail@mail.nl")).thenReturn(User doesUserExist);
		when(httpServletRequest.getParameter("regEmail")).thenReturn("testMail3@mail.nl");
		when(httpServletRequest.getParameter("regPassword")).thenReturn("password");
		when(httpServletRequest.getParameter("regPassword2")).thenReturn("password");
		assertEquals(Constants.redirect + "register?attempt=fail", registerController.registerDB(model,  httpSession, httpServletRequest));
		
	}
}