package nl.halewijn.persoonlijkheidstest.services.local;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import nl.halewijn.persoonlijkheidstest.Application;
import nl.halewijn.persoonlijkheidstest.domain.OpenQuestion;
import nl.halewijn.persoonlijkheidstest.domain.PersonalityType;
import nl.halewijn.persoonlijkheidstest.domain.Theorem;
import nl.halewijn.persoonlijkheidstest.domain.TheoremBattle;

public class LocalQuestionServiceTest {
		
	
	@Before
    public void setUp() {	
    }
	
	@Test
	public void setQuestionAnswerTest(){
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
}