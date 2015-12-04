package nl.halewijn.persoonlijkheidstest.repository;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import nl.halewijn.persoonlijkheidstest.Application;
import nl.halewijn.persoonlijkheidstest.domain.PersonalityType;
import nl.halewijn.persoonlijkheidstest.services.local.LocalPersonalityTypeService;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@ActiveProfiles("test")
public class PersonalityTypeRepositoryTest {
	
	@Autowired
	private LocalPersonalityTypeService localPersonalityTypeService;
	
	@Test
	public void testUpdate(){
		PersonalityType type = new PersonalityType("Een type", "test", "test");
		localPersonalityTypeService.save(type);	
		type.setName("Een andere naam voor het type.");
		localPersonalityTypeService.update(type);
		assertEquals("Een andere naam voor het type.", type.getName());
		
	}
	
	@Test
	@Transactional
	public void testDelete(){
		PersonalityType type = new PersonalityType("Een type", "test", "test");
		localPersonalityTypeService.save(type);	
		localPersonalityTypeService.delete(type);
		assertEquals(null, localPersonalityTypeService.getById(type.getTypeID()));
	}
}