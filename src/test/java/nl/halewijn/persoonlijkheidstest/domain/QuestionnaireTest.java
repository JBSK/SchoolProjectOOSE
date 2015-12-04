package nl.halewijn.persoonlijkheidstest.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import nl.halewijn.persoonlijkheidstest.Application;
import nl.halewijn.persoonlijkheidstest.services.local.LocalPersonalityTypeService;
import nl.halewijn.persoonlijkheidstest.services.local.LocalQuestionService;
import nl.halewijn.persoonlijkheidstest.services.local.LocalResultService;
import nl.halewijn.persoonlijkheidstest.services.local.LocalTheoremService;
import nl.halewijn.persoonlijkheidstest.services.local.LocalUserService;

import static org.mockito.Mockito.*;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class QuestionnaireTest {
	
    private Questionnaire questionnaire;
    
    @Autowired
    private LocalQuestionService localQuestionService;
    
    @Autowired
    private LocalPersonalityTypeService localPersonalityTypeService;
    
    @Autowired
    private LocalResultService localResultService;
    
    @Autowired
    private LocalUserService localUserService;
    
    @Autowired
    private LocalTheoremService localTheoremService;
    
	@Before
    public void setUp() {
		questionnaire = new Questionnaire();	
    }
	
	@Test
	public void startNewTest() {
		Model model = mock(Model.class);
		HttpSession httpSession = mock(HttpSession.class);
		
		assertEquals("questionnaire", questionnaire.startNewTest(model, httpSession, localQuestionService));
		
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
	public void submitAnswer() {
		Model model = mock(Model.class);
		HttpSession httpSession = mock(HttpSession.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		PersonalityType typePerfectionist = null;
		
		for(int i = 0; i < 9; i++) {
			PersonalityType newType = new PersonalityType("Perfectionist", "primary tekst", "secondary tekst");
			localPersonalityTypeService.save(newType);
		}
	
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
		
		Question nextQuestion;
		nextQuestion = localQuestionService.getNextQuestion(previousQuestion);
		assertEquals(null, nextQuestion);
		assertEquals("result", questionnaire.submitAnswer(httpServletRequest, localQuestionService, localPersonalityTypeService, model, httpSession, localResultService, localUserService));
		
		Question question2 = new OpenQuestion("Een volgende vraag");
		localQuestionService.save(question2);
		nextQuestion = localQuestionService.getNextQuestion(previousQuestion);
		assertEquals(question2.getText(), nextQuestion.getText());
		assertEquals("questionnaire", questionnaire.submitAnswer(httpServletRequest, localQuestionService, localPersonalityTypeService, model, httpSession, localResultService, localUserService));
	}
	
	@Test
	public void addQuestionTest(){
		List<Question> questionList = new ArrayList<>();
		assertEquals(questionnaire.getAnsweredQuestions(), questionList);
		
		OpenQuestion openQuestion = new OpenQuestion("Wat vind je er van?");
		questionList.add(openQuestion);
		
		questionnaire.addQuestion(openQuestion);
		
		assertEquals(questionnaire.getAnsweredQuestions(), questionList);
	}
	
	@Test
	public void calculateResultsTest(){
		double[] methodResultArray;
		
		PersonalityType typePerfectionist = new PersonalityType("Perfectionist", "primary tekst", "secondary tekst");
		typePerfectionist.setTypeID(1);
		PersonalityType typeHelper = new PersonalityType("Helper", "primary tekst", "secondary tekst");
		typeHelper.setTypeID(2);
		PersonalityType typeWinnaar = new PersonalityType("Winnaar", "primary tekst", "secondary tekst");
		typeWinnaar.setTypeID(3);
		
		Theorem theorem1 = new Theorem(typePerfectionist, "Dit is een perfectionistische stelling met een weging van 1.0 .", 1.0, 0, 0, 0);
		Theorem theorem2 = new Theorem(typeHelper, "Dit is een behulpzame stelling met een weging van 2.2 .", 2.2, 0, 1, 1);
		Theorem theorem3 = new Theorem(typeWinnaar, "Dit is een winnaar stelling met een weging van 1.3 .", 1.3, 1, 0, 0);
		
		TheoremBattle battle1 = new TheoremBattle("Kies de stelling die het meest voor u van toepassing is", theorem1, theorem2);
		TheoremBattle battle2 = new TheoremBattle("Kies de stelling die het meest voor u van toepassing is", theorem2, theorem3);
		TheoremBattle battle3 = new TheoremBattle("Kies de stelling die het meest voor u van toepassing is", theorem3, theorem1);
		questionnaire.addQuestion(battle1);
		questionnaire.addQuestion(battle2);
		questionnaire.addQuestion(battle3);
		
		battle1.setAnswer('A');
		battle2.setAnswer('C');
		battle3.setAnswer('E');
		
		methodResultArray = questionnaire.calculatePersonalityTypeResults(questionnaire.getAnsweredQuestions());
		
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
	public void getCurrentQuestion() {
		Model model = mock(Model.class);
		List<Question> newQuestionList = new ArrayList<>();
		
		assertEquals(newQuestionList, questionnaire.getAnsweredQuestions());	
		OpenQuestion newQuestion = new OpenQuestion();
		questionnaire.addQuestion(newQuestion);
		assertEquals(newQuestion, questionnaire.getAnsweredQuestions().get(questionnaire.getAnsweredQuestions().size()-1));
		
		questionnaire.getCurrentQuestion(model);
		when(model.containsAttribute("currentQuestion")).thenReturn(true);
		assertEquals(true, model.containsAttribute("currentQuestion"));
	}

	@Test
	public void calculatePercentageTest(){
		double dividend = 5.0;
        double divisor = 10.0;

        double actualResult = questionnaire.calculatePercentage(dividend, divisor);

        double expectedResult = dividend / divisor;
        expectedResult = expectedResult * 100;
        expectedResult = Math.round(expectedResult);
        expectedResult = expectedResult / 100;

        assert(actualResult == expectedResult);
	}

	@Test
	public void saveResults() {
		PersonalityType type = new PersonalityType("TypeOne", "first", "second");
		type.setTypeID(1);
		type = localPersonalityTypeService.save(type);
		System.out.println(localPersonalityTypeService.getAll());
		Theorem theoremOne = new Theorem(type, "Stelling 1", 1.0, 1.0, 1.0, 1.0);
		Theorem theoremSecond = new Theorem(type, "Stelling 2", 1.0, 1.0, 1.0, 1.0);
		localTheoremService.save(theoremOne);
		localTheoremService.save(theoremSecond);
		TheoremBattle theoremBattle = new TheoremBattle("Theorom Battle", theoremOne, theoremSecond);
		List<Question> questions = new ArrayList<>();
		HttpSession session = mock(HttpSession.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		Model model = mock(Model.class);
		HttpSession httpSession = mock(HttpSession.class);
		User user = new User("User");
		localQuestionService.save(theoremBattle);
		theoremBattle.setAnswer('C');
		questions.add(theoremBattle);
		localQuestionService.save(theoremBattle);
		questionnaire.setAnsweredQuestions(questions);
		double[] pTypeResultArray = questionnaire.calculatePersonalityTypeResults(questions);
		int[] subTypeResultArray = questionnaire.calculateSubTypeResults(questions);
		assertEquals(1.0, pTypeResultArray[0], 0);
		assertEquals(33.0, subTypeResultArray[0], 0);
		
		when(session.getAttribute("email")).thenReturn(null);
		Result result = new Result(null);
		assertEquals(session.getAttribute("email"), result.getUser());
		when(session.getAttribute("email")).thenReturn(user);
		if(session.getAttribute("email") != null) {
			result.setUser(user);
		}
		assertEquals(session.getAttribute("email"), result.getUser());
		
		result.setWeight2_score(subTypeResultArray[0]);
		result.setWeight3_score(subTypeResultArray[1]);
		result.setWeight4_score(subTypeResultArray[2]);
		assertEquals(result.getWeight2_score(), subTypeResultArray[0], 0);
		assertEquals(result.getWeight3_score(), subTypeResultArray[0], 0);
		assertEquals(result.getWeight4_score(), subTypeResultArray[0], 0);
		
		int totalResults = 0;
		if(localResultService.findAll() != null)
			totalResults = localResultService.findAll().size();
		
		when(httpServletRequest.getParameter("answer")).thenReturn("C");
		questionnaire.submitAnswer(httpServletRequest, localQuestionService, localPersonalityTypeService, model, httpSession, localResultService, localUserService);
		assertEquals(totalResults+1, localResultService.findAll().size());
	}
}