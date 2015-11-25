package nl.halewijn.persoonlijkheidstest.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import nl.halewijn.persoonlijkheidstest.Application;

public class QuestionnaireTest {
	
    private Questionnaire questionnaire;	
	
	@Before
    public void setUp() {
		questionnaire = new Questionnaire();
		
    }
	
	@Test
	public void addQuestionTest(){
		List<Question> questionList = new ArrayList<Question>();
		assertEquals(questionnaire.getAnsweredQuestions(), questionList);
		
		OpenQuestion openQuestion = new OpenQuestion("Wat vind je er van?");
		questionList.add(openQuestion);
		
		questionnaire.addQuestion(openQuestion);
		
		assertEquals(questionnaire.getAnsweredQuestions(), questionList);
	}
	
	@Test
	public void calculateResultsTest(){
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
	
}