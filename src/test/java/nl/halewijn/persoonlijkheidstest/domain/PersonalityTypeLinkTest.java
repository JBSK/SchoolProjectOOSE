package nl.halewijn.persoonlijkheidstest.domain;

import static org.junit.Assert.*;

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
public class PersonalityTypeLinkTest {
	
	@Test
	public void gettersAndSettersTest() {
		PersonalityType type = new PersonalityType();
		
		PersonalityTypeLink ptl = new PersonalityTypeLink(type, "titleConstrucot", "urlConstructor");
		ptl.setPersonalityType(type);
		ptl.setTitle("title");
		ptl.setUrl("url");
		assertEquals(type, ptl.getPersonalityType());
		assertEquals("title", ptl.getTitle());
		assertEquals("url", ptl.getUrl());
		
		ptl.setTitle("");
		assertEquals(ptl.getUrl(), ptl.getTitle());
		ptl.setTitle(null);
		assertEquals(ptl.getUrl(), ptl.getTitle());
	}
}