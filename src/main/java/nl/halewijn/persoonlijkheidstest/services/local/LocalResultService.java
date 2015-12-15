package nl.halewijn.persoonlijkheidstest.services.local;


import nl.halewijn.persoonlijkheidstest.datasource.repository.AnswerRepository;
import nl.halewijn.persoonlijkheidstest.datasource.repository.ResultRepository;
import nl.halewijn.persoonlijkheidstest.datasource.repository.ResultTypePercentageRepository;
import nl.halewijn.persoonlijkheidstest.domain.Result;
import nl.halewijn.persoonlijkheidstest.domain.ResultTypePercentage;
import nl.halewijn.persoonlijkheidstest.services.IResultService;
import nl.halewijn.persoonlijkheidstest.domain.Answer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocalResultService implements IResultService  {

	@Autowired
	private ResultRepository testResultRepository;
	
	@Autowired
	private AnswerRepository testResultAnswerRepository;
	
	@Autowired
	private ResultTypePercentageRepository resultTypePercentageRepository;
	
	@Override
	public Answer saveAnswer(Answer testResultAnswer) {
		return testResultAnswerRepository.save(testResultAnswer);
	}
	
	@Override
	public Result saveResult(Result testResult) {
		return testResultRepository.save(testResult);
	}
	
	@Override
	public void saveResultTypePercentage(ResultTypePercentage resultTypePercentage) {
		resultTypePercentageRepository.save(resultTypePercentage);
	}
	
	@Override
	public List<Answer> findAllAnswers() {
		return testResultAnswerRepository.findAll();
	}
	
	@Override
	public List<Result> findAll() {
		return testResultRepository.findAll();
	}
	
	@Override
	public List<ResultTypePercentage> findAllResultTypePercentages() {
		return resultTypePercentageRepository.findAll();
	}
	
	@Override
	public Answer findAnswer(int id) {
		return testResultAnswerRepository.getByAnswerId(id);
	}
	
	@Override
	public List<ResultTypePercentage> findResultTypePercentageByResult(Result result) {
		return resultTypePercentageRepository.findByResult(result);
	}
	
	@Override
	public Long count() {
		return testResultRepository.count();
	}

	public Result getByResultId(int id) {
		return testResultRepository.getByResultId(id);
	}
	
	public List<Result> getByUserId(int id) {
		return testResultRepository.getByUserId(id);
	}
	
	public List<Answer> getAnswersByResultId(int id) {
		return testResultRepository.getAnswersByResultId(id);
	}

	@Override
	public Long countUserTests() {
		return testResultRepository.countUserTests();
	}

	@Override
	public Long countAnonymousTests() {
		return testResultRepository.countAnonymousTests();
	}
}