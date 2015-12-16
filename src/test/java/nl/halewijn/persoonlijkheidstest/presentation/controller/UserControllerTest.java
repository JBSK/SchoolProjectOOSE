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
import nl.halewijn.persoonlijkheidstest.domain.Answer;
import nl.halewijn.persoonlijkheidstest.domain.PersonalityType;
import nl.halewijn.persoonlijkheidstest.domain.Question;
import nl.halewijn.persoonlijkheidstest.domain.Result;
import nl.halewijn.persoonlijkheidstest.domain.ResultTypePercentage;
import nl.halewijn.persoonlijkheidstest.domain.Theorem;
import nl.halewijn.persoonlijkheidstest.domain.TheoremBattle;
import nl.halewijn.persoonlijkheidstest.domain.User;
import nl.halewijn.persoonlijkheidstest.services.Constants;
import nl.halewijn.persoonlijkheidstest.services.local.LocalPersonalityTypeService;
import nl.halewijn.persoonlijkheidstest.services.local.LocalResultService;
import nl.halewijn.persoonlijkheidstest.services.local.LocalTheoremService;
import nl.halewijn.persoonlijkheidstest.services.local.LocalUserService;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Date;

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
	private LocalPersonalityTypeService localPersonalityTypeService;
	
	@Autowired
	private LocalTheoremService localTheoremService;
	
	@Autowired
	private LocalResultService localResultService;
	
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
		User user = new User("email@mail.com", false);
		user.setPasswordHash(passwordHash.hashPassword("aa"));
		localUserService.save(user);
		assertEquals(Constants.myresults, userController.myResults(model, session, req));
	}
	
	@Test
	public void showResultGETTest() {
		Model model = mock(Model.class);
		HttpSession session = mock(HttpSession.class);
		HttpServletRequest req = mock(HttpServletRequest.class);
		
		assertEquals(Constants.redirect, userController.showResultGET(model, session, req));
	}
	
	@Test
	public void showResultTest() {
		Model model = mock(Model.class);
		HttpSession session = mock(HttpSession.class);
		HttpServletRequest req = mock(HttpServletRequest.class);
		
		User user = new User("duncan@email.eu", true);
		setUserPassword(user);
		localUserService.save(user);
		when(session.getAttribute("email")).thenReturn(user.getEmailAddress());
		Result result = new Result(user);
		result.setScoreDenial(1);
		result.setScoreRecognition(2);
		result.setScoreDevelopment(3);
		ArrayList<Answer> testResultAnswers = new ArrayList<>();
		PersonalityType type1 = new PersonalityType("Type1", "PrimaryDescr", "SecondaryDescr");
		PersonalityType type2 = new PersonalityType("Type2", "PrimaryDescr", "SecondaryDescr");
		localPersonalityTypeService.save(type1);
		localPersonalityTypeService.save(type2);
		Theorem theorem1 = new Theorem(type1, "text1", 1, 1, 1, 1);
		Theorem theorem2 = new Theorem(type2, "text2", 1, 1, 1, 1);
		localTheoremService.save(theorem1);
		localTheoremService.save(theorem2);
		Question question = new TheoremBattle("Text", theorem1, theorem2);
		Answer answer = new Answer(question, "A", new Date());
		testResultAnswers.add(answer);
		result.setTestResultAnswers(testResultAnswers);
		localResultService.saveResult(result);
		ResultTypePercentage resultTypePercentage = new ResultTypePercentage(result, type1, 100);
		localResultService.saveResultTypePercentage(resultTypePercentage);
		when(req.getParameter("number")).thenReturn(Integer.toString(result.getId()));
		assertEquals(Constants.result, userController.showResult(model, session, req));
	}
	
	private void setUserPassword(User user) {
		String password = "x"; // Plaintext password
		String passwordHash = new PasswordHash().hashPassword(password); // Hashed password
		password = null; // Prepare plaintext password for clearing from memory by the Java garbage collector.
		user.setPasswordHash(passwordHash); // Stored hash in user
	}
}