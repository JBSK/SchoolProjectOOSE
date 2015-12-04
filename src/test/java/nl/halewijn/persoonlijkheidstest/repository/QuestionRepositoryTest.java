package nl.halewijn.persoonlijkheidstest.repository;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import nl.halewijn.persoonlijkheidstest.Application;
import nl.halewijn.persoonlijkheidstest.domain.OpenQuestion;
import nl.halewijn.persoonlijkheidstest.domain.PersonalityType;
import nl.halewijn.persoonlijkheidstest.domain.Question;
import nl.halewijn.persoonlijkheidstest.domain.Theorem;
import nl.halewijn.persoonlijkheidstest.domain.TheoremBattle;
import nl.halewijn.persoonlijkheidstest.services.local.LocalPersonalityTypeService;
import nl.halewijn.persoonlijkheidstest.services.local.LocalQuestionService;
import nl.halewijn.persoonlijkheidstest.services.local.LocalTheoremService;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@ActiveProfiles("test")
public class QuestionRepositoryTest {
	
	@Autowired
	private LocalQuestionService localQuestionService;
	
	@Autowired
	private LocalPersonalityTypeService localPersonalityTypeService;
	
	@Autowired
	private LocalTheoremService localTheoremService;
	
	@Test
	public void testSave(){
		PersonalityType perfectionist = new PersonalityType("Perfectionist", "first", "second");
		localPersonalityTypeService.save(perfectionist);
		
		Theorem theorem1 = new Theorem(perfectionist, "Stelling", 1.2, 0, 0, 0);
		localTheoremService.save(theorem1);
		Theorem theorem2 = new Theorem(perfectionist, "Stelling2", 1.2, 0, 0, 0);
		localTheoremService.save(theorem2);
		
		TheoremBattle theoremBattle = new TheoremBattle("TestTheoremBattle", theorem1, theorem2);
		localQuestionService.save(theoremBattle);
		
		TheoremBattle insertedTheoremBattle = (TheoremBattle) localQuestionService.getQuestionById(theoremBattle.getID());
		assertEquals(insertedTheoremBattle.getID(), theoremBattle.getID());
		assertEquals("TestTheoremBattle", insertedTheoremBattle.getText());
	}
	
	@Test
	public void testGetAll(){
		List<Question> newResults = localQuestionService.getAll();
		
		OpenQuestion newQuestion = new OpenQuestion("Nieuwe vraag");
		localQuestionService.save(newQuestion);
		newResults.add(newQuestion);
		
		List<Question> results = localQuestionService.getAll();
		assertEquals(newResults.size(), results.size());		
	}

	@Test
	public void testGetById(){
		OpenQuestion newQuestion = new OpenQuestion("Nieuwe vraag...");
		localQuestionService.save(newQuestion);
		
		Question question = localQuestionService.getQuestionById(2);
		assertEquals(newQuestion.getText(), question.getText());
	}
	
	@Test
	public void testGetTypeById(){
		PersonalityType perfectionist = new PersonalityType("Perfectionist", "first", "second");
		localPersonalityTypeService.save(perfectionist);
		
		Theorem theorem1 = new Theorem(perfectionist, "Stelling", 1.2, 0, 0, 0);
		localTheoremService.save(theorem1);
		Theorem theorem2 = new Theorem(perfectionist, "Stelling2", 1.2, 0, 0, 0);
		localTheoremService.save(theorem2);
		
		TheoremBattle theoremBattle = new TheoremBattle("TestTheoremBattle", theorem1, theorem2);
		localQuestionService.save(theoremBattle);
		
		OpenQuestion openQuestion = new OpenQuestion("Een nieuwe open vraag");
		localQuestionService.save(openQuestion);
		
		assertEquals("nl.halewijn.persoonlijkheidstest.domain.TheoremBattle",localQuestionService.getTypeById(theoremBattle.getID()));
		assertEquals("nl.halewijn.persoonlijkheidstest.domain.OpenQuestion",localQuestionService.getTypeById(openQuestion.getID()));
	}

	@Test
	public void testUpdate(){
		OpenQuestion openQuestion = new OpenQuestion("Nieuwe vraag");
		localQuestionService.save(openQuestion);
		assertEquals("Nieuwe vraag", openQuestion.getText());
		
		openQuestion.setText("nieuwe tekst");
		localQuestionService.update(openQuestion);
		assertEquals("nieuwe tekst", openQuestion.getText());
	}
	
	@Test
	public void testDelete(){
		Question openQuestion = new OpenQuestion("Nieuwe vraag");
		localQuestionService.save(openQuestion);	
		localQuestionService.delete(openQuestion);
		assertEquals(null, localQuestionService.getQuestionById(openQuestion.getID()));
	}
}