package nl.halewijn.persoonlijkheidstest.services.local;

import static org.junit.Assert.*;

import nl.halewijn.persoonlijkheidstest.Application;
import nl.halewijn.persoonlijkheidstest.domain.Answer;
import nl.halewijn.persoonlijkheidstest.domain.OpenQuestion;
import nl.halewijn.persoonlijkheidstest.domain.PersonalityType;
import nl.halewijn.persoonlijkheidstest.domain.Result;
import nl.halewijn.persoonlijkheidstest.domain.ResultTypePercentage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@ActiveProfiles("test")
public class LocalResultServiceTest {

    @Autowired
	private LocalResultService localResultService;
    
    @Autowired
    private LocalQuestionService localQuestionService;
    
    @Autowired
    private LocalPersonalityTypeService localPersonalityTypeService;
	
	@Test
	public void getByResultIdTest() {
		Date now = new Date();
        Result result = new Result();
        result.setDate(now);
        localResultService.saveResult(result);
        result = localResultService.getByResultId(result.getId());
        assertEquals(now, result.getDate());
	}  
	
	@Test
	public void findAllAnswersTest() {
		OpenQuestion openQuestion = new OpenQuestion("Een open vraag.");
		localQuestionService.save(openQuestion);
		
		Answer answer = new Answer(openQuestion, "Antwoord", new Date());
		localResultService.saveAnswer(answer);
		
		Answer answer2 = new Answer(openQuestion, "Antwoord 2", new Date());
		localResultService.saveAnswer(answer2);
		
		List<Answer> answerList = new ArrayList<Answer>();
		answerList.add(answer);
		answerList.add(answer2);
		assertEquals(answerList.size(), localResultService.findAllAnswers().size());		
	}  
	
	@Test
	public void findAllResultTypePercentages() {
		Result result = new Result(null);
		localResultService.saveResult(result);
		
		PersonalityType type = new PersonalityType("type", "primary", "secondary");
		localPersonalityTypeService.save(type);
		
		ResultTypePercentage resultTypePercentage = new ResultTypePercentage(result, type, 10.0);
		localResultService.saveResultTypePercentage(resultTypePercentage);
		
		ResultTypePercentage resultTypePercentage2 = new ResultTypePercentage(result, type, 20.0);
		localResultService.saveResultTypePercentage(resultTypePercentage2);
		
		List<ResultTypePercentage> list = new ArrayList<ResultTypePercentage>();
		list.add(resultTypePercentage);
		list.add(resultTypePercentage2);
		
		assertEquals(list.size(), localResultService.findAllResultTypePercentages().size());
	}
	
	@Test
	public void findAnswerTest() {
		OpenQuestion openQuestion = new OpenQuestion("Een open vraag.");
		localQuestionService.save(openQuestion);
		
		Answer answer = new Answer(openQuestion, "Antwoord", new Date());
		localResultService.saveAnswer(answer);
		
		int id = answer.getId();
		assertEquals(answer.getQuestion().getText(), localResultService.findAnswer(id).getQuestion().getText());
	}
	
	@Test
	public void getAnswersByResultIdTest() {
		OpenQuestion openQuestion = new OpenQuestion("Een open vraag.");
		localQuestionService.save(openQuestion);
		
		Answer answer = new Answer(openQuestion, "Antwoord", new Date());
		localResultService.saveAnswer(answer);
		
		Result result = new Result(null);
		localResultService.saveResult(result);
		
		result.addTestResultAnswer(answer);
		
		localResultService.saveResult(result);
		
		List<Answer> list = new ArrayList<Answer>();
		list.add(answer);
		
		assertEquals(list.size(), localResultService.getAnswersByResultId(result.getId()).size());
		assertEquals(list.size(), result.getTestResultAnswers().size());
	}
	
}