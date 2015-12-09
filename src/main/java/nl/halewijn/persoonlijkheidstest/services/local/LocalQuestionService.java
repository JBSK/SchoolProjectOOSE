package nl.halewijn.persoonlijkheidstest.services.local;

import nl.halewijn.persoonlijkheidstest.datasource.repository.QuestionRepository;
import nl.halewijn.persoonlijkheidstest.datasource.repository.RoutingTableRepository;
import nl.halewijn.persoonlijkheidstest.domain.OpenQuestion;
import nl.halewijn.persoonlijkheidstest.domain.PersonalityType;
import nl.halewijn.persoonlijkheidstest.domain.Question;
import nl.halewijn.persoonlijkheidstest.domain.Questionnaire;
import nl.halewijn.persoonlijkheidstest.domain.RoutingRule;
import nl.halewijn.persoonlijkheidstest.domain.RoutingTable;
import nl.halewijn.persoonlijkheidstest.domain.Theorem;
import nl.halewijn.persoonlijkheidstest.domain.TheoremBattle;
import nl.halewijn.persoonlijkheidstest.services.IQuestionService;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocalQuestionService implements IQuestionService  {

	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private LocalRoutingService localRoutingService;
	
	@Autowired
	private LocalTheoremService localTheoremService;
	
	@Autowired
	private LocalPersonalityTypeService localPersonalityTypeService;
	
	@Override
	public List<Question> findAll() {
		return questionRepository.findAll();
	}
	
	@Override
	public List<Question> findAllByText(String text) {
		return questionRepository.findAllByText(text);
	}
	
	@Override
	public List<Question> findAllByPersonalityTypeId(int typeId) {
		PersonalityType type = localPersonalityTypeService.getById(typeId);

		List<Theorem> theorems = localTheoremService.getAllByPersonalityType(type);
		List<Question> questionsWithTheoremsWithRelevantType = new ArrayList<>();
		
		for (Theorem theorem : theorems) {
			List<TheoremBattle> questions = getAllByTheorem(theorem);
			questionsWithTheoremsWithRelevantType.addAll(questions); // TODO Handle duplicates
		}
		
		return questionsWithTheoremsWithRelevantType;
	}
	
	public List<TheoremBattle> getAllByTheorem(Theorem theorem) {
		return questionRepository.findAllByFirstTheorem(theorem);
	}

	@Override
	public void save(Question question) {
		questionRepository.save(question);
	}
	
	@Override
	public void delete(Question question) {
		questionRepository.delete(question);
	}
	
	@Override
	public List<Question> getAll() {
		return questionRepository.findAll();
	}

	@Override
	public Question getByQuestionId(int questionId) {
		return questionRepository.findByQuestionID(questionId);
	}
	
	@Override
	public String getQuestionTypeById(int questionId) {
		Question question = getByQuestionId(questionId);
		return question.getClassName();
	}
	
	@Override
	public void update(Question question) {
		questionRepository.save(question);
	}

	/**
	 * Checks whether a next question exists or not.
	 * 
	 * If a next question exists, this question is requested from the database, and then returned.
	 * 
	 * If no next question exists, a null value is returned.
	 * @param answer 
	 */
	
	@Override
	public Question getNextQuestion(Question previousQuestion, String answer) {
		List<RoutingTable> tables = localRoutingService.getRoutingRulesByQuestion(previousQuestion);
		if(tables.isEmpty() == false) {
			for(RoutingTable table : tables){ 
				if(table.getAnswer() == answer.charAt(0)){  
					RoutingRule rule = table.getRoutingRule();
					int ruleId = rule.getRoutingRuleId();
					int ruleParam = table.getRoutingRuleParam();
					return determineFollowUpQuestion(previousQuestion, ruleId, ruleParam);	
				}
			}
		}
		return getNextChronologicalQuestion(previousQuestion);		
	}

	private Question determineFollowUpQuestion(Question previousQuestion, int ruleId, int ruleParam) {
		switch(ruleId){
			case 1:
				if (ruleParam > previousQuestion.getQuestionId()) {
					return getByQuestionId(ruleParam);
				}
			case 2:
				return processPersonalityTypeRoutingRule(previousQuestion, ruleParam);
			default:
				return getNextChronologicalQuestion(previousQuestion);
		}
	}

	private Question  processPersonalityTypeRoutingRule(Question previousQuestion, int ruleParam) {
		List<Question> relevantQuestions = findAllByPersonalityTypeId(ruleParam);
		if(relevantQuestions.isEmpty() == false){
			Question firstQuestionInTheList = relevantQuestions.get(0);
			while (firstQuestionInTheList.getQuestionId() <= previousQuestion.getQuestionId()) {
				try { 
					relevantQuestions.remove(0);
					firstQuestionInTheList = relevantQuestions.get(0);
				} catch (Exception e) {
					firstQuestionInTheList = null;
					break;
				}
			}
			if (firstQuestionInTheList != null) {
				return firstQuestionInTheList; 
			}
		}
		return null;
	}

	private Question getNextChronologicalQuestion(Question previousQuestion) {
		Question next = null;
		if(getByQuestionId(previousQuestion.getQuestionId()+1) != null) {
		    next = getByQuestionId(previousQuestion.getQuestionId() + 1);
		}
		return next;
	}
	
	/**
	 * Checks in the database whether a question with ID 1 exists.
	 * 
	 * If it exists, question ID 1 is requested from the database, and returned.
	 * 
	 * If it doesn't exist, a null value is returned.
	 */
	
	@Override
	public Question getFirstQuestion(Questionnaire questionnaire) {
		Question firstQuestion = null;

        if(getByQuestionId(1) != null) {
            firstQuestion = getByQuestionId(1);
        }

		questionnaire.addQuestion(firstQuestion);
		return firstQuestion;
	}
	
	/**
	 * This sets the answer for question X at Y.
	 * 
	 * If the question was a theorem battle, the answer is set between zero and four.
	 * 
	 * If the question was an open question, the answer is requested from the browser session.
	 * The requested text will be set as the answer.
	 */
	
	@Override
	public void setQuestionAnswer(HttpServletRequest req, Question previousQuestion) {
		String answer = req.getParameter("answer");
		if(previousQuestion instanceof TheoremBattle) {
			((TheoremBattle) previousQuestion).setAnswer(answer.charAt(0));
		} else if(previousQuestion instanceof OpenQuestion) {
			((OpenQuestion) previousQuestion).setAnswer(answer);
		}
	}
}