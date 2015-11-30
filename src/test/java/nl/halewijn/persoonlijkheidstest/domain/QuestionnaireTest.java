package nl.halewijn.persoonlijkheidstest.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Before;
import org.junit.runners.MethodSorters;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import nl.halewijn.persoonlijkheidstest.Application;
import nl.halewijn.persoonlijkheidstest.services.local.LocalPersonalityTypeService;
import nl.halewijn.persoonlijkheidstest.services.local.LocalQuestionService;
import static org.mockito.Mockito.*;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@ActiveProfiles("test")
public class QuestionnaireTest {
	
    private Questionnaire questionnaire;
    
    @Autowired
    private LocalQuestionService localQuestionService;
    
    @Autowired
    private LocalPersonalityTypeService localPersonalityTypeService;
    
	@Before
    public void setUp() {
		questionnaire = new Questionnaire();	
    }
	
	@Test
	public void test1_startNewTest() {
		Model model = mock(Model.class);
		HttpSession httpSession = mock(HttpSession.class);
		
		assertEquals("", questionnaire.startNewTest(model, httpSession, localQuestionService));
		
		Question openQ = new OpenQuestion("test");
		localQuestionService.save(openQ);
		
		assertEquals("", questionnaire.startNewTest(model, httpSession, localQuestionService));
		
		Question firstQuestion = null;
		httpSession.setAttribute("questionnaire", questionnaire);
		when(httpSession.getAttribute("questionnaire")).thenReturn(questionnaire);
		assertEquals(questionnaire, httpSession.getAttribute("questionnaire"));
		
		model.addAttribute("currentQuestion", firstQuestion);
		when(model.containsAttribute("currentQuestion")).thenReturn(true);
		assertEquals(model.containsAttribute("currentQuestion"), true);
	}
	
	@Test
	public void test2_submitAnswer() {
		Model model = mock(Model.class);
		HttpSession httpSession = mock(HttpSession.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		PersonalityType typePerfectionist = new PersonalityType("Perfectionist", "primary tekst", "secondary tekst");
		
		for(int i = 0; i < 10; i++)
			localPersonalityTypeService.save(typePerfectionist);
		
		Question question = new OpenQuestion("Een vraag");
		questionnaire.addQuestion(question);
		
		OpenQuestion previousQuestion = (OpenQuestion) questionnaire.getPreviousQuestion();
		localQuestionService.save(previousQuestion);
		assertEquals(question, previousQuestion);
		
		when(httpServletRequest.getParameter("answer")).thenReturn("Antwoord");
		localQuestionService.setQuestionAnswer(httpServletRequest, previousQuestion);
		assertEquals("Antwoord", previousQuestion.getAnswer());
		
		PersonalityType typeOne = new PersonalityType("Type One", "test", "test");
		localPersonalityTypeService.save(typeOne);
		
		Question nextQuestion = null;
		nextQuestion = localQuestionService.getNextQuestion(previousQuestion);
		assertEquals(null, nextQuestion);
		assertEquals("result", questionnaire.submitAnswer(httpServletRequest, localQuestionService, localPersonalityTypeService, model, httpSession));
		
		Question question2 = new OpenQuestion("Een volgende vraag");
		localQuestionService.save(question2);
		nextQuestion = localQuestionService.getNextQuestion(previousQuestion);
		assertEquals(question2.getText(), nextQuestion.getText());
		assertEquals("questionnaire", questionnaire.submitAnswer(httpServletRequest, localQuestionService, localPersonalityTypeService, model, httpSession));
	}
	
	@Test
	public void test3_addQuestionTest(){
		List<Question> questionList = new ArrayList<Question>();
		assertEquals(questionnaire.getAnsweredQuestions(), questionList);
		
		OpenQuestion openQuestion = new OpenQuestion("Wat vind je er van?");
		questionList.add(openQuestion);
		
		questionnaire.addQuestion(openQuestion);
		
		assertEquals(questionnaire.getAnsweredQuestions(), questionList);
	}
	
	@Test
	public void test4_calculateResultsTest(){
		double[] methodResultArray = { 0,0,0,0,0,0,0,0,0 };
		
		PersonalityType typePerfectionist = new PersonalityType("Perfectionist", "primary tekst", "secondary tekst");
		typePerfectionist.setTypeID(1);
		PersonalityType typeHelper = new PersonalityType("Helper", "primary tekst", "secondary tekst");
		typeHelper.setTypeID(2);
		PersonalityType typeWinnaar = new PersonalityType("Winnaar", "primary tekst", "secondary tekst");
		typeWinnaar.setTypeID(3);
		
		Theorem theorem1 = new Theorem(typePerfectionist, "Dit is een perfectionistische stelling met een weging van 1.0 .", 1.0);
		Theorem theorem2 = new Theorem(typeHelper, "Dit is een behulpzame stelling met een weging van 2.2 .", 2.2);
		Theorem theorem3 = new Theorem(typeWinnaar, "Dit is een winnaar stelling met een weging van 1.3 .", 1.3);
		
		TheoremBattle battle1 = new TheoremBattle("Kies de stelling die het meest voor u van toepassing is", theorem1, theorem2);
		TheoremBattle battle2 = new TheoremBattle("Kies de stelling die het meest voor u van toepassing is", theorem2, theorem3);
		TheoremBattle battle3 = new TheoremBattle("Kies de stelling die het meest voor u van toepassing is", theorem3, theorem1);
		questionnaire.addQuestion(battle1);
		questionnaire.addQuestion(battle2);
		questionnaire.addQuestion(battle3);
		
		battle1.setAnswer('A');
		battle2.setAnswer('C');
		battle3.setAnswer('E');
		
		methodResultArray = questionnaire.calculateResults();
		
		double typeOnePoints = (5*1) + (1*5);
		double typeTwoPoints = (1*2.2);
		double typeThreePoints = (1*1.3);
		double totalPoints = (typeOnePoints) + (typeTwoPoints) + (typeThreePoints);
		
		double typeOnePercentage = (double) Math.round((typeOnePoints/totalPoints) * 100) / 100;
		double typeTwoPercentage = (double) Math.round((typeTwoPoints/totalPoints) * 100) / 100;
		double typeThreePercentage = (double) Math.round((typeThreePoints/totalPoints) * 100) / 100;
		
		double[] testResultArray = { typeOnePercentage,typeTwoPercentage,typeThreePercentage,0,0,0,0,0,0 };
		
		assertArrayEquals(testResultArray, methodResultArray, 0);
	}
	
	@Test 
	public void test5_getCurrentQuestion() {
		Model model = mock(Model.class);
		List<Question> newQuestionList = new ArrayList<>();
		
		assertEquals(newQuestionList, questionnaire.getAnsweredQuestions());	
		OpenQuestion newQuestion = new OpenQuestion();
		questionnaire.addQuestion(newQuestion);
		assertEquals(newQuestion, questionnaire.getAnsweredQuestions().get(questionnaire.getAnsweredQuestions().size()-1));;
		
		questionnaire.getCurrentQuestion(model);
		when(model.containsAttribute("currentQuestion")).thenReturn(true);
		assertEquals(true, model.containsAttribute("currentQuestion"));
	}
}