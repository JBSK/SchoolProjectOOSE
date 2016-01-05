package nl.halewijn.persoonlijkheidstest.repository;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import nl.halewijn.persoonlijkheidstest.Application;
import nl.halewijn.persoonlijkheidstest.domain.PersonalityType;
import nl.halewijn.persoonlijkheidstest.services.local.LocalPersonalityTypeService;

@Transactional
@WebIntegrationTest
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@ActiveProfiles("test")
public class PersonalityTypeRepositoryTest {
	
	@Autowired
	private LocalPersonalityTypeService localPersonalityTypeService;
	
	@Test
	public void testUpdate(){
		String originalTypeName = "ExampleType";
        String updatedTypeName = "ExampleTypeUpdated";

        PersonalityType examplePersonalityType = new PersonalityType(originalTypeName, "Example description 1", "Example description 2");
		localPersonalityTypeService.save(examplePersonalityType);

		examplePersonalityType.setName(updatedTypeName);
		localPersonalityTypeService.save(examplePersonalityType);

        PersonalityType updatedPersonalityType = localPersonalityTypeService.getById(examplePersonalityType.getTypeID());
        assertEquals(updatedTypeName, updatedPersonalityType.getName());
	}
	
	@Test
	@Transactional
	public void testDelete(){
		PersonalityType examplePersonalityType = new PersonalityType("ExampleType", "Example description 1", "Example description 2");
		localPersonalityTypeService.save(examplePersonalityType);

        examplePersonalityType = localPersonalityTypeService.getById(examplePersonalityType.getTypeID());
		localPersonalityTypeService.delete(examplePersonalityType);

		assertNull(localPersonalityTypeService.getById(examplePersonalityType.getTypeID()));
	}

}