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

        System.out.println(localTheoremService.getAll());

        List<TheoremBattle> actualRelevantTheoremBattles = localQuestionService.getAllByTheorem(theorem2);

        List<TheoremBattle> expectedRelevantTheoremBattles = new ArrayList<>();
        expectedRelevantTheoremBattles.add(battle3);
        expectedRelevantTheoremBattles.add(battle1);

        assertEquals(expectedRelevantTheoremBattles, actualRelevantTheoremBattles);
    }

}