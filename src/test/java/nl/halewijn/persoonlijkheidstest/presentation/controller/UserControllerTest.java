package nl.halewijn.persoonlijkheidstest.presentation.controller;

import nl.halewijn.persoonlijkheidstest.services.PasswordHash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import nl.halewijn.persoonlijkheidstest.Application;
import nl.halewijn.persoonlijkheidstest.domain.PersonalityType;
import nl.halewijn.persoonlijkheidstest.domain.Result;
import nl.halewijn.persoonlijkheidstest.domain.Theorem;
import nl.halewijn.persoonlijkheidstest.domain.User;
import nl.halewijn.persoonlijkheidstest.services.Constants;
import nl.halewijn.persoonlijkheidstest.services.local.LocalPersonalityTypeService;
import nl.halewijn.persoonlijkheidstest.services.local.LocalResultService;
import nl.halewijn.persoonlijkheidstest.services.local.LocalTheoremService;
import nl.halewijn.persoonlijkheidstest.services.local.LocalUserService;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@ActiveProfiles("test")
public class UserControllerTest {
	
	@Autowired
    private LocalUserService localUserService;
	
	@Autowired
	private UserController userController;
	
	private PasswordHash passwordHash = new PasswordHash();
	
	@Test
	public void myResultsTest() {
		Model model = mock(Model.class);
		HttpSession session = mock(HttpSession.class);
		HttpServletRequest req = mock(HttpServletRequest.class);
		
		assertEquals(Constants.redirect, userController.myResults(model, session, req));
		
		when(session.getAttribute("email")).thenReturn("email@mail.com");
//		when(localUserService.findByEmailAddress("duncan@email.eu").getId()).thenReturn(0);
		User user = new User("email@mail.com", false);
		user.setPasswordHash(passwordHash.hashPassword("aa"));
		localUserService.save(user);
		assertEquals("myResults", userController.myResults(model, session, req));
		}
}