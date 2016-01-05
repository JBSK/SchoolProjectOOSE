package nl.halewijn.persoonlijkheidstest.repository;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
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
@WebIntegrationTest
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@ActiveProfiles("test")
public class QuestionRepositoryTest {

    @Autowired
    private LocalPersonalityTypeService localPersonalityTypeService;

    @Autowired
    private LocalTheoremService localTheoremService;

	@Autowired
	private LocalQuestionService localQuestionService;

	@Test
	public void testSave(){
		String exampleTheoremBattleName = "Example theorem battle";

        PersonalityType examplePersonalityType = new PersonalityType("ExampleType", "Example description 1", "Example description 2");
		localPersonalityTypeService.save(examplePersonalityType);
		
		Theorem exampleTheorem1 = new Theorem(examplePersonalityType, "Example theorem 1", 1.2, 0, 0, 0);
		localTheoremService.save(exampleTheorem1);

		Theorem exampleTheorem2 = new Theorem(examplePersonalityType, "Example theorem 2", 0.7, 0, 1, 0);
		localTheoremService.save(exampleTheorem2);
		
		TheoremBattle exampleTheoremBattle = new TheoremBattle(exampleTheoremBattleName, exampleTheorem1, exampleTheorem2);
		localQuestionService.save(exampleTheoremBattle);
		
		TheoremBattle insertedTheoremBattle = (TheoremBattle) localQuestionService.getByQuestionId(exampleTheoremBattle.getQuestionId());

		assertEquals(exampleTheoremBattleName, insertedTheoremBattle.getText());
	}
	
	@Test
	public void testGetAll(){
		List<Question> questionsBefore = localQuestionService.getAll();
		
		OpenQuestion exampleQuestion = new OpenQuestion("Example question text");
		localQuestionService.save(exampleQuestion);
		questionsBefore.add(exampleQuestion);
		
		List<Question> questionsAfter = localQuestionService.getAll();

        assertEquals(questionsBefore, questionsAfter);
	}

	@Test
	public void testGetById(){
        String exampleQuestionText = "Example question text";

		OpenQuestion exampleOpenQuestion = new OpenQuestion(exampleQuestionText);
		localQuestionService.save(exampleOpenQuestion);
		
		Question insertedQuestion = localQuestionService.getByQuestionId(exampleOpenQuestion.getQuestionId());
		assertEquals(exampleQuestionText, insertedQuestion.getText());
	}
	
	@Test
	public void testGetTypeById(){
        String openQuestionTypeString = "nl.halewijn.persoonlijkheidstest.domain.OpenQuestion";
        String theoremBattleTypeString = "nl.halewijn.persoonlijkheidstest.domain.TheoremBattle";

        OpenQuestion exampleOpenQuestion = new OpenQuestion("Example question text");
        localQuestionService.save(exampleOpenQuestion);

        PersonalityType examplePersonalityType = new PersonalityType("ExampleType", "Example description 1", "Example description 2");
        localPersonalityTypeService.save(examplePersonalityType);

        Theorem exampleTheorem1 = new Theorem(examplePersonalityType, "Example theorem 1", 1.2, 0, 0, 0);
        localTheoremService.save(exampleTheorem1);

        Theorem exampleTheorem2 = new Theorem(examplePersonalityType, "Example theorem 2", 0.7, 0, 1, 0);
        localTheoremService.save(exampleTheorem2);

        TheoremBattle exampleTheoremBattle = new TheoremBattle("Example theorem battle", exampleTheorem1, exampleTheorem2);
        localQuestionService.save(exampleTheoremBattle);

        String insertedOpenQuestionType = localQuestionService.getQuestionTypeById(exampleOpenQuestion.getQuestionId());
        String insertedTheoremBattleType = localQuestionService.getQuestionTypeById(exampleTheoremBattle.getQuestionId());

        assertEquals(openQuestionTypeString, insertedOpenQuestionType);
		assertEquals(theoremBattleTypeString, insertedTheoremBattleType);
	}

	@Test
	public void testUpdate(){
        String exampleQuestionText = "Example question text";
        String updatedExampleQuestionText = "Updated example question text";

		OpenQuestion exampleOpenQuestion = new OpenQuestion(exampleQuestionText);
		localQuestionService.save(exampleOpenQuestion);
		
		exampleOpenQuestion.setText(updatedExampleQuestionText);
		localQuestionService.update(exampleOpenQuestion);

        OpenQuestion updatedOpenQuestion = (OpenQuestion) localQuestionService.getByQuestionId(exampleOpenQuestion.getQuestionId());
		assertEquals(updatedExampleQuestionText, updatedOpenQuestion.getText());
	}
	
	@Test
	public void testDelete(){
		Question exampleQuestion = new OpenQuestion("Example question text");
		localQuestionService.save(exampleQuestion);

        exampleQuestion = localQuestionService.getByQuestionId(exampleQuestion.getQuestionId());
        localQuestionService.delete(exampleQuestion);

		assertNull(localQuestionService.getByQuestionId(exampleQuestion.getQuestionId()));
	}
}