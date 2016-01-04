package nl.halewijn.persoonlijkheidstest.services.local;

import nl.halewijn.persoonlijkheidstest.Application;
import nl.halewijn.persoonlijkheidstest.domain.WebsiteContentText;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@ActiveProfiles("test")
public class LocalWebsiteContentTextServiceTest {

    @Autowired
	private LocalWebsiteContentTextService localWebsiteContentTextServiceService;
	
	@Test
	public void getByContentIdTest() {
        String contentText = "This is a piece of content.";
        WebsiteContentText content = new WebsiteContentText();
        content.setContentText(contentText);
        content = localWebsiteContentTextServiceService.save(content);
        content = localWebsiteContentTextServiceService.getByContentId(content.getContentId());
        assertEquals(contentText, content.getContentText());
	}  
}