package nl.halewijn.persoonlijkheidstest.domain;

import nl.halewijn.persoonlijkheidstest.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.Assert.assertEquals;

@Transactional
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@ActiveProfiles("test")
public class ResultTest {
	
	@Test
	public void getFormattedDateTest() {
		Date now = new Date();
        Result result = new Result();
		result.setDate(now);
        assertEquals(now.toString(), result.getFormattedDate());
	}
}