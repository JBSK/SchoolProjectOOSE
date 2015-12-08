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
public class AdminControllerTest {

	@Autowired
	private AdminController adminController;
	
	@Autowired
    private LocalUserService localUserService;
	
	@Autowired
	private LocalResultService localResultService;
	
	@Autowired
	private LocalTheoremService localTheoremService;
	
	@Autowired
	private LocalPersonalityTypeService localPersonalityTypeService;
	
	@Test
	public void showAdminTest() {	
		Model model = mock(Model.class);
		HttpSession session = mock(HttpSession.class);

		assertEquals(Constants.redirect, adminController.showAdmin(model, session));
		
		User user = new User("duncan@email.eu", true);

        String password = "x"; // Plaintext password
        String passwordHash = new PasswordHash().hashPassword(password); // Hashed password
        password = null; // Prepare plaintext password for clearing from memory by the Java garbage collector.
		user.setPasswordHash(passwordHash); // Stored hash in user

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
	
	@Test
	public void manageTheoremsTest() {
		Model model = mock(Model.class);
		HttpSession session = mock(HttpSession.class);
		HttpServletRequest req = mock(HttpServletRequest.class);
		
		assertEquals(Constants.redirect, adminController.managetheorems(model, session, req));
		
		List<Theorem> theorems = localTheoremService.getAll();
		assertEquals(theorems.size(), 0);
		
		Theorem theorem = new Theorem(mock(PersonalityType.class), "text", 1, 1, 1, 1);
		model.addAttribute("theorems", theorem);
		//when(model.containsAttribute("theorem")).thenReturn(theorem);
		//assertEquals(true, model.containsAttribute("theorems"));
		//when(model.addAttribute("theorems", theorem)).thenReturn(model.containsAttribute("theorems"));
	}
	
	@Test
	public void addTheoremTest() {
		Model model = mock(Model.class);
		HttpSession session = mock(HttpSession.class);
		HttpServletRequest req = mock(HttpServletRequest.class);
		
		assertEquals(Constants.redirect, adminController.addTheorem(model, session, req));
		
		List<PersonalityType> types = localPersonalityTypeService.getAll();
		assertEquals(types.size(), 0);
		
		Theorem theorem = new Theorem(mock(PersonalityType.class), "text", 1, 1, 1, 1);
		localTheoremService.save(theorem);
		
		assertEquals(1, localTheoremService.getAll().size(), 0);
	}
	
	@Test
	public void addTheoremToDBTest() {
		Model model = mock(Model.class);
		HttpSession session = mock(HttpSession.class);
		HttpServletRequest req = mock(HttpServletRequest.class);
		
		
	}
	
//	@Test
//	public void editTheoremTest() {
//		Model model = mock(Model.class);
//		HttpSession session = mock(HttpSession.class);
//		HttpServletRequest req = mock(HttpServletRequest.class);
//		
//		
//	}
//	
//	@Test
//	public void updateTheoremTest() {
//		Model model = mock(Model.class);
//		HttpSession session = mock(HttpSession.class);
//		HttpServletRequest req = mock(HttpServletRequest.class);
//		
//		
//	}
//	
//	@Test
//	public void deleteTheoremTest() {
//		Model model = mock(Model.class);
//		HttpSession session = mock(HttpSession.class);
//		HttpServletRequest req = mock(HttpServletRequest.class);
//		
//		
//	}
//	
//	@Test
//	public void addToTheoremTest() {
//		Theorem theorem = mock(Theorem.class);
//		
//		
//	}
//	
//	@Test
//	public void checkIfAdminTest() {
//		HttpSession session = mock(HttpSession.class);
//		
//		
//	}
}