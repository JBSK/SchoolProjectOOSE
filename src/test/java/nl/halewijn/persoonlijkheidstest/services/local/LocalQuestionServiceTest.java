package nl.halewijn.persoonlijkheidstest.services.local;

import static org.junit.Assert.*;

import nl.halewijn.persoonlijkheidstest.Application;
import nl.halewijn.persoonlijkheidstest.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.*;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@ActiveProfiles("test")
public class LocalQuestionServiceTest {

    @Autowired
	private LocalQuestionService localQuestionService;

    @Autowired
	private LocalRoutingService localRoutingService;
    
    @Autowired
    private LocalTheoremService localTheoremService;

    @Autowired
    private LocalPersonalityTypeService localPersonalityTypeService;

	@Before
    public void setUp() {	
    }
	
	@Test
	public void setQuestionAnswerTest() {
		String stringAnswer = "Dit is een lang antwoord.";
		char charAnswer = 'D';

		PersonalityType typePerfectionist = new PersonalityType("Perfectionist", "primary tekst", "secondary tekst");
		typePerfectionist.setTypeID(1);
		Theorem theorem1 = new Theorem(typePerfectionist, "Dit is een perfectionistische stelling met een weging van 1.0 .", 1.0, 0, 0, 0);
		Theorem theorem2 = new Theorem(typePerfectionist, "Dit is een behulpzame stelling met een weging van 2.2 .", 2.2, 0, 0, 0);

		TheoremBattle questionOne = new TheoremBattle("Kies de stelling die het meest voor u van toepassing is", theorem1, theorem2);
		OpenQuestion questionTwo = new OpenQuestion("Waarom denk jij dat?");

        assertEquals("TheoremBattle", questionOne.getType());
        assertEquals("OpenQuestion", questionTwo.getType());

		questionOne.setAnswer(charAnswer);
		questionTwo.setAnswer(stringAnswer);

		assertEquals(questionOne.getAnswer(), charAnswer);
		assertEquals(questionTwo.getAnswer(), stringAnswer);
		
		HttpServletRequest req = mock(HttpServletRequest.class);
		when(req.getParameter("answer")).thenReturn("Asd");
		
		localQuestionService.setQuestionAnswer(req, questionTwo);
		assertEquals("Asd", questionTwo.getAnswer());
		
		when(req.getParameter("answer")).thenReturn("B");
		
		localQuestionService.setQuestionAnswer(req, questionOne);
		assertEquals('B', questionOne.getAnswer());
	}


	@Test
    public void getNextChronologicalQuestionTest() {
        Question openQuestion = new OpenQuestion("Waarom vind je dat?");
        openQuestion = localQuestionService.save(openQuestion);

        Question expectedNextQuestion = new OpenQuestion("Wat voor effect heeft dat op je?");
        expectedNextQuestion = localQuestionService.save(expectedNextQuestion);

        int nextQuestionId = openQuestion.getQuestionId() + 1;
        Question actualNextQuestion = localQuestionService.getByQuestionId(nextQuestionId);

        assertEquals(expectedNextQuestion, actualNextQuestion);
    }

    @Test
    public void getAllByTheoremTest() {
        PersonalityType typePerfectionist = new PersonalityType("Perfectionist", "primary tekst", "secondary tekst");
        localPersonalityTypeService.save(typePerfectionist);

        Theorem theorem1 = new Theorem(typePerfectionist, "Dit is een perfectionistische stelling met een weging van 1.0 .", 1.0, 0, 0, 0);
        Theorem theorem2 = new Theorem(typePerfectionist, "Dit is een perfectionistische stelling met een weging van 2.2 .", 2.2, 0, 0, 0);
        Theorem theorem3 = new Theorem(typePerfectionist, "Dit is een perfectionistische stelling met een weging van 0.7 .", 0.7, 0, 0, 0);
        theorem1 = localTheoremService.save(theorem1);
        theorem2 = localTheoremService.save(theorem2);
        theorem3 = localTheoremService.save(theorem3);

        TheoremBattle battle1 = new TheoremBattle("Battle 1", theorem1, theorem2);
        TheoremBattle battle2 = new TheoremBattle("Battle 2", theorem1, theorem3);
        TheoremBattle battle3 = new TheoremBattle("Battle 3", theorem2, theorem3);
        battle1 = (TheoremBattle) localQuestionService.save(battle1);
        battle2 = (TheoremBattle) localQuestionService.save(battle2);
        battle3 = (TheoremBattle) localQuestionService.save(battle3);

        List<TheoremBattle> actualRelevantTheoremBattles = localQuestionService.getAllByTheorem(theorem2);

        List<TheoremBattle> expectedRelevantTheoremBattles = new ArrayList<>();
        expectedRelevantTheoremBattles.add(battle3);
        expectedRelevantTheoremBattles.add(battle1);

        assertEquals(expectedRelevantTheoremBattles, actualRelevantTheoremBattles);
    }
    
    @Test
    public void getNextQuestion() {
        PersonalityType typePerfectionist = new PersonalityType("Perfectionist", "primary tekst", "secondary tekst");
        typePerfectionist = localPersonalityTypeService.save(typePerfectionist);
        PersonalityType typeWinnaar = new PersonalityType("Winnaar", "primary tekst", "secondary tekst");
        typeWinnaar = localPersonalityTypeService.save(typeWinnaar);
        PersonalityType typeVerliezer = new PersonalityType("Verliezer", "primary tekst", "secondary tekst");
        typeVerliezer = localPersonalityTypeService.save(typeVerliezer);
        PersonalityType typeBaas = new PersonalityType("Baas", "primary tekst", "secondary tekst");
        typeBaas = localPersonalityTypeService.save(typeBaas);

        Theorem theorem1 = new Theorem(typePerfectionist, "Dit is een perfectionistische stelling met een weging van 1.0 .", 1.0, 0, 0, 0);
        Theorem theorem2 = new Theorem(typePerfectionist, "Dit is een perfectionistische stelling met een weging van 2.2 .", 2.2, 0, 0, 0);
        Theorem theorem3 = new Theorem(typeWinnaar, "Dit is een perfectionistische stelling met een weging van 0.7 .", 0.7, 0, 0, 0);
        Theorem theorem4 = new Theorem(typeBaas, "Dit is baas stelling met een weging van 0.7 .", 0.7, 0, 0, 0);
        theorem1 = localTheoremService.save(theorem1);
        theorem2 = localTheoremService.save(theorem2);
        theorem3 = localTheoremService.save(theorem3);
        theorem4 = localTheoremService.save(theorem3);

        TheoremBattle battle1 = new TheoremBattle("Battle 1", theorem1, theorem2);
        TheoremBattle battle2 = new TheoremBattle("Battle 2", theorem1, theorem2);
        TheoremBattle battle3 = new TheoremBattle("Battle 3", theorem1, theorem3);
        TheoremBattle battle4 = new TheoremBattle("Battle 4", theorem3, theorem3);
        TheoremBattle battle5 = new TheoremBattle("Battle 5", theorem4, theorem4);
        battle1 = (TheoremBattle) localQuestionService.save(battle1);
        battle2 = (TheoremBattle) localQuestionService.save(battle2);
        battle3 = (TheoremBattle) localQuestionService.save(battle3);
        battle4 = (TheoremBattle) localQuestionService.save(battle4);
        battle5 = (TheoremBattle) localQuestionService.save(battle5);

        assertEquals(battle2.getQuestionId(), localQuestionService.getNextQuestion(battle1, "A").getQuestionId());

        RoutingRule ruleOne = new RoutingRule("To specified ID");
        ruleOne = localRoutingService.save(ruleOne);
        RoutingRule ruleTwo = new RoutingRule("To next question with specified Type");
        ruleTwo = localRoutingService.save(ruleTwo);
        RoutingRule ruleThree = new RoutingRule("Non existing type that should end up in the default switch.");
        ruleThree = localRoutingService.save(ruleThree);

        RoutingTable routeRule = new RoutingTable(battle1, 'B', ruleOne);
        routeRule.setRoutingRuleParam(battle3.getQuestionId());
        routeRule = localRoutingService.save(routeRule);

        assertEquals(battle3.getQuestionId(), localQuestionService.getNextQuestion(battle1, "B").getQuestionId());

        routeRule.setRoutingRuleParam(0);
        routeRule = localRoutingService.save(routeRule);

        assertEquals(battle2, localQuestionService.getNextQuestion(battle1, "B"));

        RoutingTable routeRule2 = new RoutingTable(battle1, 'D', ruleThree);
        routeRule2.setRoutingRuleParam(battle3.getQuestionId());
        routeRule2 = localRoutingService.save(routeRule2);

        assertEquals(battle2.getQuestionId(), localQuestionService.getNextQuestion(battle1, "D").getQuestionId());

        RoutingTable routeRule3 = new RoutingTable(battle1, 'C', ruleTwo);
        routeRule3.setRoutingRuleParam(typeWinnaar.getTypeID());
        routeRule3 = localRoutingService.save(routeRule3);

        assertEquals(battle3.getQuestionId(), localQuestionService.getNextQuestion(battle1, "C").getQuestionId());

        routeRule3.setRoutingRuleParam(typeVerliezer.getTypeID());
        routeRule3 = localRoutingService.save(routeRule3);

        assertEquals(battle2, localQuestionService.getNextQuestion(battle1, "C"));

        RoutingTable routeRule4 = new RoutingTable(battle2, 'E', ruleTwo);
        routeRule4.setRoutingRuleParam(typePerfectionist.getTypeID());
        routeRule4 = localRoutingService.save(routeRule4);

        assertEquals(battle3, localQuestionService.getNextQuestion(battle2, "E"));

        RoutingTable routeRule5 = new RoutingTable(battle4, 'A', ruleTwo);
        routeRule5.setRoutingRuleParam(typePerfectionist.getTypeID());
        routeRule5 = localRoutingService.save(routeRule5);

        assertEquals(battle5, localQuestionService.getNextQuestion(battle4, "A"));

        RoutingTable routeRule6 = new RoutingTable(battle5, 'A', ruleTwo);
        routeRule6.setRoutingRuleParam(typeWinnaar.getTypeID());
        routeRule6 = localRoutingService.save(routeRule6);

        assertEquals(null, localQuestionService.getNextQuestion(battle5, "A"));
        
        // isActive test:
        Question openQuestionOne = new OpenQuestion("Open Question");
        Question openQuestionTwo = new OpenQuestion("Open Question");
        Question openQuestionThree = new OpenQuestion("Open Question");
        openQuestionOne = localQuestionService.save(openQuestionOne);
        openQuestionTwo = localQuestionService.save(openQuestionTwo);
        openQuestionThree = localQuestionService.save(openQuestionThree);
        openQuestionTwo.setActive(false);
        
        assertEquals(openQuestionThree.getQuestionId(), localQuestionService.getNextQuestion(openQuestionOne, "Test").getQuestionId());
        
        
    }
    
    @Test
    public void sortQuestionsArrayTest() {
    	List<Question> questionList = new ArrayList<>();
    	OpenQuestion newQuestion = new OpenQuestion("Open Question");
    	localQuestionService.save(newQuestion);
    	OpenQuestion newQuestion2 = new OpenQuestion("Open Question 2");
    	questionList.add(newQuestion);
    	questionList.add(newQuestion2);
    	
    	List<Question> questionListSorted = new ArrayList<>(questionList);
    	localQuestionService.sortQuestionsArray(questionListSorted);
    	assertEquals(newQuestion2, questionListSorted.get(0));
    	
    	localQuestionService.sortQuestionsArray(questionListSorted);
    	assertEquals(newQuestion2, questionListSorted.get(0));
    	questionListSorted.get(0).setQuestionId(1);
    	questionListSorted.get(1).setQuestionId(1);
    	
    	localQuestionService.sortQuestionsArray(questionListSorted);
    	assertEquals(newQuestion2, questionListSorted.get(0));
    }
    
    @Test
    public void findAllTest() {
    	OpenQuestion questionOne = new OpenQuestion("1");
    	OpenQuestion questionTwo = new OpenQuestion("2");
    	int questionsAmount = localQuestionService.findAll().size();
    	localQuestionService.save(questionOne);
    	localQuestionService.save(questionTwo);
    	assertEquals(questionsAmount+2, localQuestionService.findAll().size());
    }
    
    @Test
    public void findAllByTextTest() {
    	OpenQuestion questionOne = new OpenQuestion("Specific text");
    	OpenQuestion questionTwo = new OpenQuestion("Specific text");
    	int questionsAmount = localQuestionService.findAllByText("Specific text").size();
    	localQuestionService.save(questionOne);
    	localQuestionService.save(questionTwo);
    	assertEquals(questionsAmount+2, localQuestionService.findAllByText("Specific text").size());
    }
    
}