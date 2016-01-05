package nl.halewijn.persoonlijkheidstest.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import nl.halewijn.persoonlijkheidstest.Application;

@Transactional
@WebIntegrationTest("server.port:85")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@ActiveProfiles("test")
public class PersonalityTypeLinkContainerTest {
	
	@Test
	public void gettersAndSettersTest() {
		PersonalityType type = new PersonalityType();
		PersonalityTypeLink ptl = new PersonalityTypeLink();
		List<PersonalityTypeLink> ptlList = new ArrayList<>();
		ptlList.add(ptl);
		
		PersonalityTypeLinkContainer ptlc = new PersonalityTypeLinkContainer(type, ptlList);
		assertEquals(type, ptlc.getPersonalityType());
		assertEquals(ptlList, ptlc.getLinks());
	}
}