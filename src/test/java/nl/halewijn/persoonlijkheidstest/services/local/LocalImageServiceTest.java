package nl.halewijn.persoonlijkheidstest.services.local;

import nl.halewijn.persoonlijkheidstest.Application;
import nl.halewijn.persoonlijkheidstest.application.domain.Image;
import nl.halewijn.persoonlijkheidstest.application.services.local.LocalImageService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@Transactional
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@ActiveProfiles("test")
public class LocalImageServiceTest {

    @Autowired
	private LocalImageService localImageService;
	
	@Test
	public void getByImageIdTest() {
        String imgPath = "/1.jpg";
        Image img = new Image();
        img.setImagePath(imgPath);
		img = localImageService.save(img);
        img = localImageService.getByImageId(img.getImageId());
        assertEquals(imgPath, img.getImagePath());
	}  
}