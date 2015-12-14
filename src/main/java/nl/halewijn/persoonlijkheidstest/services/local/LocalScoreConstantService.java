package nl.halewijn.persoonlijkheidstest.services.local;

import nl.halewijn.persoonlijkheidstest.datasource.repository.ScoreConstantRepository;
import nl.halewijn.persoonlijkheidstest.domain.ScoreConstant;
import nl.halewijn.persoonlijkheidstest.services.IScoreConstantService;
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

	public ScoreConstant update(ScoreConstant scoreConstant) {
		return scoreConstantRepository.save(scoreConstant);
	}
}