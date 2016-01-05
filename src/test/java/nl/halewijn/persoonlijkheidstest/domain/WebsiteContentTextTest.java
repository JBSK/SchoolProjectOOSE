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
@WebIntegrationTest
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@ActiveProfiles("test")
public class WebsiteContentTextTest {
	
	@Test
	public void gettersAndSettersTest() {
		WebsiteContentText text = new WebsiteContentText();
		text.setContentDescription("contentDescription");
		text.setContentText("contentText");
		text.setContentTitle("contentTitle");
		assertEquals("contentDescription", text.getContentDescription());
		assertEquals(0, text.getContentId(), 0);
		assertEquals("contentText", text.getContentText());
		assertEquals("contentTitle", text.getContentTitle());
	}
}