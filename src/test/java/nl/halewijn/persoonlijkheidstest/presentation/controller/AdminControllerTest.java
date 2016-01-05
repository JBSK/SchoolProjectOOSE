package nl.halewijn.persoonlijkheidstest.presentation.controller;

import nl.halewijn.persoonlijkheidstest.services.PasswordHash;
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
@WebIntegrationTest("server.port:85")
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

		assertEquals(Constants.redirect, adminController.adminDashboard(model, session));

		User user = new User("duncan@email.eu", true);
		setUserPassword(user);
		localUserService.save(user);

		when(session.getAttribute("email")).thenReturn("duncan@email.eu");
		assertEquals("adminDashboard", adminController.adminDashboard(model, session));	
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
		
		assertEquals(Constants.redirect, adminController.manageTheorems(model, session, req));
		
		User user = new User("duncan@email.eu", true);
		setUserPassword(user);
		localUserService.save(user);
		
		List<Theorem> theorems = localTheoremService.getAll();
		assertEquals(theorems.size(), 0);
		
		PersonalityType type = new PersonalityType("TestType", "Descr1", "Descr2");
		localPersonalityTypeService.save(type);
		
		Theorem theorem = new Theorem(type, "text", 1, 1, 1, 1);
		localTheoremService.save(theorem);
		assertEquals(1, localTheoremService.getAll().size(), 0);
		
		when(session.getAttribute("email")).thenReturn("duncan@email.eu");
		assertEquals("managetheorems", adminController.manageTheorems(model, session, req));
		
		assertEquals(false, model.containsAttribute("theorems"));
	}
	
	@Test
	public void addTheoremTest() {
		Model model = mock(Model.class);
		HttpSession session = mock(HttpSession.class);
		HttpServletRequest req = mock(HttpServletRequest.class);
		
		assertEquals(Constants.redirect, adminController.addTheorem(model, session, req));
		
		User user = new User("duncan@email.eu", true);
		setUserPassword(user);
		
		localUserService.save(user);
		when(session.getAttribute("email")).thenReturn("duncan@email.eu");
		assertEquals("addTheorem", adminController.addTheorem(model, session, req));
		
		List<PersonalityType> types = localPersonalityTypeService.getAll();
		assertEquals(types.size(), 0);
		
		PersonalityType type = new PersonalityType("TestType", "Descr1", "Descr2");
		localPersonalityTypeService.save(type);
		Theorem theorem = new Theorem(type, "text", 1, 1, 1, 1);
		localTheoremService.save(theorem);
		
		assertEquals(1, localTheoremService.getAll().size(), 0);
	}
	
	@Test
	public void addTheoremToDBTest() {
		Model model = mock(Model.class);
		HttpSession session = mock(HttpSession.class);
		HttpServletRequest req = mock(HttpServletRequest.class);
		
		assertEquals(Constants.redirect, adminController.addTheoremToDB(model, session, req));
		
		User user = new User("duncan@email.eu", true);
		setUserPassword(user);
		localUserService.save(user);
		
		PersonalityType type = new PersonalityType("TestType", "Descr1", "Descr2");
		localPersonalityTypeService.save(type);
		int typeID = type.getTypeID();
		String typeIDstring = Integer.toString(typeID);


		when(session.getAttribute("email")).thenReturn("duncan@email.eu");
		getParameters(req, typeIDstring);
		
		assertEquals("redirect:/manageTheorems", adminController.addTheoremToDB(model, session, req));
		assertEquals("TheoremText", localTheoremService.getAll().get(0).getText());
		assertEquals(localTheoremService.getAll().size(), 1, 0);
	}
	
	@Test
	public void editTheoremTest() {
		Model model = mock(Model.class);
		HttpSession session = mock(HttpSession.class);
		HttpServletRequest req = mock(HttpServletRequest.class);
		
		assertEquals(Constants.redirect, adminController.editTheorem(model, session, req));
		
		User user = new User("duncan@email.eu", true);
		setUserPassword(user);
		localUserService.save(user);
		
		when(session.getAttribute("email")).thenReturn("duncan@email.eu");
		
		PersonalityType type = new PersonalityType("TestType", "Descr1", "Descr2");
		localPersonalityTypeService.save(type);
		int typeID = type.getTypeID();
		String typeIDstring = Integer.toString(typeID);


		Theorem theorem = new Theorem(type, "text", 1, 1, 1, 1);
		localTheoremService.save(theorem);
		
		when(req.getParameter("number")).thenReturn(typeIDstring);
		
		assertEquals("editTheorem", adminController.editTheorem(model, session, req));
		assertEquals(false, model.containsAttribute("theorem"));
	}
	
	@Test
	public void updateTheoremTest() {
		Model model = mock(Model.class);
		HttpSession session = mock(HttpSession.class);
		HttpServletRequest req = mock(HttpServletRequest.class);
		
		assertEquals(Constants.redirect, adminController.updateTheorem(model, session, req));
		
		User user = new User("duncan@email.eu", true);
		setUserPassword(user);
		localUserService.save(user);
		
		when(session.getAttribute("email")).thenReturn("duncan@email.eu");
		
		List<Theorem> theorems = localTheoremService.getAll();
		assertEquals(theorems.size(), 0);
		
		PersonalityType type = new PersonalityType("TestType", "Descr1", "Descr2");
		localPersonalityTypeService.save(type);
		assertEquals(localPersonalityTypeService.getAll().size(), 1, 0);
		int typeID = type.getTypeID();
		String typeIDstring = Integer.toString(typeID);
		assertEquals(localPersonalityTypeService.getById(typeID).getName(), "TestType");
		
		Theorem theorem = new Theorem(type, "text", 1, 1, 1, 1);
		localTheoremService.save(theorem);
		assertEquals(localTheoremService.getAll().size(), 1, 0);
		
		when(req.getParameter("number")).thenReturn(Integer.toString(theorem.getTheoremID()));
		assertEquals(1, localTheoremService.getAll().size(), 0);

		getParameters(req, typeIDstring);
		
		assertEquals("redirect:/manageTheorems", adminController.updateTheorem(model, session, req));
		assertEquals(theorem.getText(), localTheoremService.getById(theorem.getTheoremID()).getText());
		assertEquals(false, model.containsAttribute("theorem"));
	}

	@Test
	public void deleteTheoremTest() {
		Model model = mock(Model.class);
		HttpSession session = mock(HttpSession.class);
		HttpServletRequest req = mock(HttpServletRequest.class);
		
		assertEquals(Constants.redirect, adminController.deleteTheorem(model, session, req));
		
		User user = new User("duncan@email.eu", true);
		setUserPassword(user);
		localUserService.save(user);
		
		when(session.getAttribute("email")).thenReturn("duncan@email.eu");
		
		PersonalityType type = new PersonalityType("TestType", "Descr1", "Descr2");
		localPersonalityTypeService.save(type);
		assertEquals(1, localPersonalityTypeService.getAll().size(), 0);
		int typeID = type.getTypeID();

		assertEquals(localPersonalityTypeService.getById(typeID).getName(), "TestType");
		
		Theorem theorem = new Theorem(type, "text", 1, 1, 1, 1);
		localTheoremService.save(theorem);
		assertEquals(localTheoremService.getAll().size(), 1, 0);

		when(req.getParameter("number")).thenReturn(Integer.toString(theorem.getTheoremID()));
		
		assertEquals("redirect:/manageTheorems", adminController.deleteTheorem(model, session, req));
		assertEquals(0, localTheoremService.getAll().size(), 0);
	}
	
	@Test
	public void manageQuestionsTest() {
		Model model = mock(Model.class);
		HttpSession session = mock(HttpSession.class);
		HttpServletRequest req = mock(HttpServletRequest.class);
		
		assertEquals(Constants.redirect, adminController.manageQuestions(model, session, req));
		
		User user = new User("duncan@email.eu", true);
		setUserPassword(user);
		localUserService.save(user);
		when(session.getAttribute("email")).thenReturn("duncan@email.eu");
		
		assertEquals("managequestions", adminController.manageQuestions(model, session, req));
	}
	
	@Test
	public void sendMessagesTest() {
		Model model = mock(Model.class);
		HttpSession session = mock(HttpSession.class);
		HttpServletRequest req = mock(HttpServletRequest.class);
		
		assertEquals(Constants.redirect, adminController.sendMessages(model, session, req));
		
		User user = new User("duncan@email.eu", true);
		setUserPassword(user);
		localUserService.save(user);
		when(session.getAttribute("email")).thenReturn("duncan@email.eu");
		
		assertEquals("sendmessages", adminController.sendMessages(model, session, req));
	}
	
	@Test
	public void userOverviewTest() {
		Model model = mock(Model.class);
		HttpSession session = mock(HttpSession.class);
		HttpServletRequest req = mock(HttpServletRequest.class);
		
		assertEquals(Constants.redirect, adminController.userOverview(model, session, req));
		
		User user = new User("duncan@email.eu", true);
		setUserPassword(user);
		localUserService.save(user);
		when(session.getAttribute("email")).thenReturn("duncan@email.eu");
		
		assertEquals("useroverview", adminController.userOverview(model, session, req));
	}
	
	@Test
	public void editResultTextsTest() {
		Model model = mock(Model.class);
		HttpSession session = mock(HttpSession.class);
		HttpServletRequest req = mock(HttpServletRequest.class);
		
		assertEquals(Constants.redirect, adminController.editResultTexts(model, session, req));
		
		User user = new User("duncan@email.eu", true);
		setUserPassword(user);
		localUserService.save(user);
		when(session.getAttribute("email")).thenReturn("duncan@email.eu");
		
		assertEquals("editresulttexts", adminController.editResultTexts(model, session, req));
	}
	
	@Test
	public void editAnswerValuesTest() {
		Model model = mock(Model.class);
		HttpSession session = mock(HttpSession.class);
		HttpServletRequest req = mock(HttpServletRequest.class);
		
		assertEquals(Constants.redirect, adminController.editAnswerValues(model, session, req));
		
		User user = new User("duncan@email.eu", true);
		setUserPassword(user);
		localUserService.save(user);
		when(session.getAttribute("email")).thenReturn("duncan@email.eu");
		
		assertEquals("editanswervalues", adminController.editAnswerValues(model, session, req));
	}

	@Test
	public void checkIfAdminTest() {
		HttpSession session = mock(HttpSession.class);
		
		assertEquals(null, localUserService.findByEmailAddress((String) session.getAttribute("email")));
		assertEquals(false, adminController.checkIfAdmin(session));
		
		when(session.getAttribute("email")).thenReturn("duncan@email.eu");
		assertEquals("duncan@email.eu", session.getAttribute("email"));
		when(session.getAttribute("admin")).thenReturn(true);
		assertEquals(null, localUserService.findByEmailAddress((String) session.getAttribute("email")));
		assertEquals(true, adminController.checkIfAdmin(session));
	}

	private void getParameters(HttpServletRequest req, String typeIDstring) {
		when(req.getParameter("personality")).thenReturn(typeIDstring);
		when(req.getParameter("denial")).thenReturn("1.5");
		when(req.getParameter("recognition")).thenReturn("1.3");
		when(req.getParameter("development")).thenReturn("0.1");
		when(req.getParameter("text")).thenReturn("TheoremText");
		when(req.getParameter("weight")).thenReturn("1.2");
	}

	private void setUserPassword(User user) {
		String password = "x"; // Plaintext password
		String passwordHash = new PasswordHash().hashPassword(password); // Hashed password
		password = null; // Prepare plaintext password for clearing from memory by the Java garbage collector.
		user.setPasswordHash(passwordHash); // Stored hash in user
	}

}