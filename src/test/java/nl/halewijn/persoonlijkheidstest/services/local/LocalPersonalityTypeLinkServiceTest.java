package nl.halewijn.persoonlijkheidstest.services.local;

import nl.halewijn.persoonlijkheidstest.Application;
import nl.halewijn.persoonlijkheidstest.domain.PersonalityType;
import nl.halewijn.persoonlijkheidstest.domain.PersonalityTypeLink;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@ActiveProfiles("test")
public class LocalPersonalityTypeLinkServiceTest {

	@Autowired
	private LocalPersonalityTypeLinkService localPersonalityTypeLinkService;

    @Autowired
    private LocalPersonalityTypeService localPersonalityTypeService;

	@Test
	public void linkCRUD() {
        PersonalityType optimist = new PersonalityType("Optimist", "Primary description", "Secondary description");
        localPersonalityTypeService.save(optimist);

        String title = "Example link";
        String url = "http://example.com";
        List<PersonalityTypeLink> linksExpected = new ArrayList<>();

		PersonalityTypeLink link = new PersonalityTypeLink(optimist, title, url);
        linksExpected.add(link);
        link = localPersonalityTypeLinkService.save(link);
        assertEquals(title, link.getTitle());

        PersonalityTypeLink firstLink = localPersonalityTypeLinkService.getById(1);
        assertEquals(link.getPersonalityType(), firstLink.getPersonalityType());

        link.setTitle("");
        assertEquals(url, link.getTitle());

        List<PersonalityTypeLink> linksActual = localPersonalityTypeLinkService.getAll();
        assertEquals(linksExpected, linksActual);
		
		localPersonalityTypeLinkService.delete(link);
		assertEquals(true, localPersonalityTypeLinkService.getAll().isEmpty());
	}
}