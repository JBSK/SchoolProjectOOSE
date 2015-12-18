package nl.halewijn.persoonlijkheidstest.domain;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import nl.halewijn.persoonlijkheidstest.Application;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@ActiveProfiles("test")
public class ButtonTest {
	
	@Test
	public void gettersAndSettersTest() {
		Button button = new Button();
		button.setButtonDescription("buttonDescription");
		button.setButtonText("buttonText");
		assertEquals("buttonDescription", button.getButtonDescription());
		assertEquals(0, button.getButtonId(), 0);
		assertEquals("buttonText", button.getButtonText());
	}
}