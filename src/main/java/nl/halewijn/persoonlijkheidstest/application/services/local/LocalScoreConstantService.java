package nl.halewijn.persoonlijkheidstest.application.services.local;

import nl.halewijn.persoonlijkheidstest.application.domain.ScoreConstant;
import nl.halewijn.persoonlijkheidstest.application.services.IScoreConstantService;
import nl.halewijn.persoonlijkheidstest.datasource.repository.ScoreConstantRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LocalScoreConstantService implements IScoreConstantService {

	@Autowired
	private ScoreConstantRepository scoreConstantRepository;

	public ScoreConstant save(ScoreConstant scoreConstant) {
		return scoreConstantRepository.save(scoreConstant);
	}

	public void delete(ScoreConstant scoreConstant) {
		scoreConstantRepository.delete(scoreConstant);
	}

	public List<ScoreConstant> getAll() {
		return scoreConstantRepository.findAll();
	}

	public ScoreConstant findByAnswer(char answer) {
		return scoreConstantRepository.findByAnswer(String.valueOf(answer));
	}

}