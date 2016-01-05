package nl.halewijn.persoonlijkheidstest.domain;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import nl.halewijn.persoonlijkheidstest.Application;

@Transactional
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@ActiveProfiles("test")
public class PersonalityTypeTest {
	
	@Test
	public void gettersAndSettersTest() {
		PersonalityType type = new PersonalityType();
		type.setName("name");
		type.setTypeID(0);
		assertEquals("name", type.getName());
		assertEquals(0, type.getTypeID(), 0);
		assertNull(type.getPrimaryDescription());
		assertNull(type.getSecondaryDescription());
	}
}