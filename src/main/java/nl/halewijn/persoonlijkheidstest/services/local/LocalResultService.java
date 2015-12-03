package nl.halewijn.persoonlijkheidstest.services.local;


import nl.halewijn.persoonlijkheidstest.datasource.repository.AnswerRepository;
import nl.halewijn.persoonlijkheidstest.datasource.repository.ResultRepository;
import nl.halewijn.persoonlijkheidstest.domain.Result;
import nl.halewijn.persoonlijkheidstest.domain.Answer;

import java.util.logging.Logger;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocalResultService  {

	private Logger logger = Logger.getLogger(getClass().getName());
	
	@Autowired
	private ResultRepository testResultRepository;
	
	@Autowired
	private AnswerRepository testResultAnswerRepository;
	
	public Answer saveAnswer(Answer testResultAnswer) {
		return testResultAnswerRepository.save(testResultAnswer);
	}
	
	public Result saveResult(Result testResult) {
		return testResultRepository.save(testResult);
	}


}