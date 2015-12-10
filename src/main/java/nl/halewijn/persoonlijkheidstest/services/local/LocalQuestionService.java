package nl.halewijn.persoonlijkheidstest.services.local;

import nl.halewijn.persoonlijkheidstest.datasource.repository.QuestionRepository;
import nl.halewijn.persoonlijkheidstest.domain.OpenQuestion;
import nl.halewijn.persoonlijkheidstest.domain.PersonalityType;
import nl.halewijn.persoonlijkheidstest.domain.Question;
import nl.halewijn.persoonlijkheidstest.domain.Questionnaire;
import nl.halewijn.persoonlijkheidstest.domain.RoutingRule;
import nl.halewijn.persoonlijkheidstest.domain.RoutingTable;
import nl.halewijn.persoonlijkheidstest.domain.Theorem;
import nl.halewijn.persoonlijkheidstest.domain.TheoremBattle;
import nl.halewijn.persoonlijkheidstest.services.IQuestionService;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

	/*
	 * Firstly, requests the related personality type from the database.
	 * Secondly, requests all theorems, which have this type, from the database.
	 * Thirdly, requests all questions, which contain one of these theorems, from the database.
	 */
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

    /*
     * Returns a list of all theorem battles which contain only a specific theorem. Does not account for duplicates.
     */
	public List<TheoremBattle> getAllByTheorem(Theorem theorem) {
        List<TheoremBattle> allRelevantTheoremBattles = questionRepository.findByFirstTheorem(theorem); // Already store the first half of all relevant theorem battles.
        List<TheoremBattle> secondHalfOfTheoremBattles = questionRepository.findBySecondTheorem(theorem);
        allRelevantTheoremBattles.addAll(secondHalfOfTheoremBattles);
		return allRelevantTheoremBattles;
	}

	@Override
	public Question save(Question question) {
		return questionRepository.save(question);
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
		return questionRepository.findByQuestionId(questionId);
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
	 * If the previous question was part of a routing rule, the next question of this routing rule is returned.
	 *
	 * If the previous question wasn't part of a routing rule, the next question will be requested based on question id.
	 *
	 * If no next question exists, a null value is returned.
	 */
	@Override
	public Question getNextQuestion(Question previousQuestion, String answer) {
		List<RoutingTable> tables = localRoutingService.getRoutingRulesByQuestion(previousQuestion);
		if(!tables.isEmpty()) {
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

	/**
	 * Determines what the next question should be.
	 * This is based on the previous question, and the routing rules.
	 *
	 * If routing rule type one applies, the next question is called based on its id.
	 * If routing rule type two applies, the next question is called based on its personality type.
	 * If neither applies, the next question is the previous question's id is incremented by one.
	 */
	private Question determineFollowUpQuestion(Question previousQuestion, int ruleId, int ruleParam) {
		switch(ruleId){
            case 1:
				if (ruleParam > previousQuestion.getQuestionId()) {
					return getByQuestionId(ruleParam);
				} else {
                    return null; // We've already answered the specified question.
                }
			case 2:
				return processPersonalityTypeRoutingRule(previousQuestion, ruleParam);
			default:
				return getNextChronologicalQuestion(previousQuestion);
		}
	}

	/**
	 * Requests all questions with a specific personality type, based on the routing rules
	 * If there are none, a null value is returned.
	 *
	 * If there are questions returned, check whether they are eligible to be the next question.
	 * If it isn't, skip the question until an eligible one is found.
	 * If no eligible question is found, return a null value.
	 * If there is an eligible question, return it.
	 */
	private Question  processPersonalityTypeRoutingRule(Question previousQuestion, int ruleParam) {
		List<Question> relevantQuestions = findAllByPersonalityTypeId(ruleParam);
		if(!relevantQuestions.isEmpty()) {
            sortQuestionsArray(relevantQuestions);

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

    /*
     * Sort an list of Questions by their ascending question IDs.
     */
    private void sortQuestionsArray(List<Question> arrayToSort) {
        Collections.sort(arrayToSort, new Comparator<Question>() {
            @Override
            public int compare(Question q1, Question q2) {
                int q1Id = q1.getQuestionId();
                int q2Id = q2.getQuestionId();

                if (q1Id < q2Id) {
                    return -1;
                } else if (q1Id == q2Id) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });
    }

	/**
	 * Returns the next question based on the previous question.
	 * If there is no next question, a null value is returned.
	 * This ,presumably, marks the end of the questionnaire.
	 *
	 * There must always be a previous question.
	 * It is presumed that, when this is not the case, this function can never be called.
	 */
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

        if (getByQuestionId(1) != null) {
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