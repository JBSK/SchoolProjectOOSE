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
import nl.halewijn.persoonlijkheidstest.domain.Result;
import nl.halewijn.persoonlijkheidstest.domain.User;
import nl.halewijn.persoonlijkheidstest.services.Constants;
import nl.halewijn.persoonlijkheidstest.services.PasswordHash;
import nl.halewijn.persoonlijkheidstest.services.local.LocalResultService;
import nl.halewijn.persoonlijkheidstest.services.local.LocalUserService;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@ActiveProfiles("test")
public class AdminControllerTest {

	@Autowired
	private AdminController adminController;
	
	@Autowired
    private LocalUserService localUserService;
	
	@Autowired
	private LocalResultService localResultService;
	
	@Test
	public void showAdminTest() {	
		Model model = mock(Model.class);
		HttpSession session = mock(HttpSession.class);
		assertEquals(Constants.redirect, adminController.showAdmin(model, session));
		
		User user = new User("duncan@email.eu", true);
		user.setPassword("x");
		localUserService.save(user);
		when(session.getAttribute("email")).thenReturn("duncan@email.eu");
		assertEquals("adminDashboard", adminController.showAdmin(model, session));	
		Result result = new Result(user);
		localResultService.saveResult(result);	
		Result result2 = new Result(null);
		localResultService.saveResult(result2);	
		assertEquals(2, localResultService.count(), 0);
		assertEquals(1, localResultService.countUserTests(), 0);
		assertEquals(1, localResultService.countAnonymousTests(), 0);

	}
}
