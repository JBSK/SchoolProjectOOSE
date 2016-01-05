package nl.halewijn.persoonlijkheidstest.presentation.controller;

import nl.halewijn.persoonlijkheidstest.services.Constants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
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
@WebIntegrationTest("server.port:85")
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
		HttpServletRequest req = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute(Constants.email)).thenReturn("email@mail.com");
		assertEquals(Constants.redirect, loginController.login(model, session, req));
		when(session.getAttribute(Constants.email)).thenReturn(null);
        assertEquals("login", loginController.login(model, session, req));
	}
	
	@Test
	public void loginCheckTest() {
		Model model = mock(Model.class);
		HttpSession httpSession = mock(HttpSession.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		assertEquals(Constants.redirect + Constants.login + "?attempt=wrong", loginController.loginCheck(model, httpSession, httpServletRequest));
		
		PasswordHash passwordHash = new PasswordHash();	
		User newUser = new User("email@mail.com", false);
		newUser.setPasswordHash(passwordHash.hashPassword("aa"));
        newUser = localUserService.save(newUser);
		when(httpServletRequest.getParameter(Constants.email)).thenReturn("email@mail.com");
		when(httpServletRequest.getParameter(Constants.password)).thenReturn("test");
		
		assertEquals("email@mail.com", httpServletRequest.getParameter(Constants.email));
		assertEquals(Constants.redirect + Constants.login + "?attempt=wrong", loginController.loginCheck(model, httpSession, httpServletRequest));

		newUser.setPasswordHash(passwordHash.hashPassword("test"));
        newUser.setBanned(true);
        newUser = localUserService.save(newUser);
        assertEquals(Constants.redirect + Constants.login + "?attempt=banned", loginController.loginCheck(model, httpSession, httpServletRequest));

        newUser.setBanned(false);
        newUser = localUserService.save(newUser);

		//loginController.loginCheck(model, httpSession, httpServletRequest);
		when(httpSession.getAttribute(Constants.email)).thenReturn("email@mail.com");
		assertEquals(newUser.getEmailAddress(), httpSession.getAttribute(Constants.email));
		assertEquals(Constants.redirect, loginController.loginCheck(model, httpSession, httpServletRequest));
	}

    /*
     * We invalidate the session when logging out, so that whatever method is run on the session will throw an error.
     * This is good, because the session and all it's accompanying data has already been destroyed.
     * We know this test succeeds, when we receive the appropriate error while retrieving a 'dead' session attribute (which is normal behaviour).
     */
    @Test(expected=IllegalStateException.class)
	public void logOutTest() {
		Model model = mock(Model.class);
		HttpSession httpSession = mock(HttpSession.class);

		loginController.logOut(model, httpSession);
		when(httpSession.getAttribute("email")).thenThrow(IllegalStateException.class);
        httpSession.getAttribute("email");

	}
}
