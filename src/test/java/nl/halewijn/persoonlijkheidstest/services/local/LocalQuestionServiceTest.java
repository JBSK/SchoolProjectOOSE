package nl.halewijn.persoonlijkheidstest.services.local;

import static org.junit.Assert.*;

import nl.halewijn.persoonlijkheidstest.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class LocalQuestionServiceTest {

    @Autowired
	private LocalQuestionService localQuestionService;

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

    /*
	@Test
    private void getNextChronologicalQuestionTest() {
        Question previousQuestion = new OpenQuestion("Waarom denk jij dat?");

        if(getByQuestionId(previousQuestion.getQuestionId()+1) != null) {
            next = getByQuestionId(previousQuestion.getQuestionId() + 1);
        }
        return next;
    }
    */
}