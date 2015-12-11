package nl.halewijn.persoonlijkheidstest.services.local;

import static org.junit.Assert.*;

import nl.halewijn.persoonlijkheidstest.Application;
import nl.halewijn.persoonlijkheidstest.datasource.repository.QuestionRepository;
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

		questionOne.setAnswer(charAnswer);
		questionTwo.setAnswer(stringAnswer);

		assertEquals(questionOne.getAnswer(), charAnswer);
		assertEquals(questionTwo.getAnswer(), stringAnswer);
	}


	@Test
    public void getNextChronologicalQuestionTest() {
        //import static org.mockito.Mockito.*;
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

         Theorem theorem1 = new Theorem(typePerfectionist, "Dit is een perfectionistische stelling met een weging van 1.0 .", 1.0, 0, 0, 0);
         Theorem theorem2 = new Theorem(typePerfectionist, "Dit is een perfectionistische stelling met een weging van 2.2 .", 2.2, 0, 0, 0);
         Theorem theorem3 = new Theorem(typeWinnaar, "Dit is een perfectionistische stelling met een weging van 0.7 .", 0.7, 0, 0, 0);
         theorem1 = localTheoremService.save(theorem1);
         theorem2 = localTheoremService.save(theorem2);
         theorem3 = localTheoremService.save(theorem3);

         TheoremBattle battle1 = new TheoremBattle("Battle 1", theorem1, theorem2);
         TheoremBattle battle2 = new TheoremBattle("Battle 2", theorem1, theorem2);
         TheoremBattle battle3 = new TheoremBattle("Battle 3", theorem1, theorem3);
         battle1 = (TheoremBattle) localQuestionService.save(battle1);
         battle2 = (TheoremBattle) localQuestionService.save(battle2);
         battle3 = (TheoremBattle) localQuestionService.save(battle3);
         
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
         
         assertEquals(null, localQuestionService.getNextQuestion(battle1, "B"));
         
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
         
         assertEquals(null, localQuestionService.getNextQuestion(battle1, "C"));
         
         RoutingTable routeRule4 = new RoutingTable(battle2, 'E', ruleTwo);
         routeRule2.setRoutingRuleParam(typePerfectionist.getTypeID());
         routeRule2 = localRoutingService.save(routeRule2);
         
         assertEquals(battle3, localQuestionService.getNextQuestion(battle2, "E"));
         
         
    }

}