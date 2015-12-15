package nl.halewijn.persoonlijkheidstest.services.local;

import nl.halewijn.persoonlijkheidstest.Application;
import nl.halewijn.persoonlijkheidstest.domain.ScoreConstant;
import nl.halewijn.persoonlijkheidstest.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@ActiveProfiles("test")
public class LocalScoreConstantServiceTest {

	@Autowired
	private LocalScoreConstantService localScoreConstantService;

	@Test
	public void scoreConstantCRUD() {
		ScoreConstant constantA = new ScoreConstant('A', 5.0);
		localScoreConstantService.save(constantA);

		List<ScoreConstant> constants = localScoreConstantService.getAll();
		assertEquals(1, constants.size());

		constantA.setScore(3.0);
		constantA = localScoreConstantService.save(constantA);
		assertEquals(3.0, constantA.getScore(), 0.0);

		localScoreConstantService.delete(constantA);
		constants = localScoreConstantService.getAll();
		assertEquals(0, constants.size());
	}
}