package nl.halewijn.persoonlijkheidstest.services.local;

import static org.junit.Assert.*;

import nl.halewijn.persoonlijkheidstest.Application;
import nl.halewijn.persoonlijkheidstest.domain.Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@ActiveProfiles("test")
public class LocalResultServiceTest {

    @Autowired
	private LocalResultService localResultService;
	
	@Test
	public void getByResultIdTest() {
		Date now = new Date(); // TODO: Improving test coverage
        Result result = new Result();
        result.setDate(now);
        localResultService.saveResult(result);
        result = localResultService.getByResultId(result.getId());
        assertEquals(now, result.getDate());
	}  
}