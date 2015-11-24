package nl.halewijn.persoonlijkheidstest.datasource.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import nl.halewijn.persoonlijkheidstest.Application;
import nl.halewijn.persoonlijkheidstest.datasource.dao.PersonalityTypeDao;
import nl.halewijn.persoonlijkheidstest.datasource.util.DatabaseConfig;
//import nl.halewijn.persoonlijkheidstest.datasource.util.TestDatabaseConfig;
import nl.halewijn.persoonlijkheidstest.domain.PersonalityType;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("applicationContext.xml")
//@ContextConfiguration(classes = {TestDatabaseConfig.class})
@SpringApplicationConfiguration(Application.class)
//@WebIntegrationTest
public class PersonalityTypeDaoTest {
	
	@Autowired
	private PersonalityTypeDao dao;
	
//	@Before
//	public void setUpDB(){
//		
//	}
//	
//	@After
//	public void tearDownDB(){
//		
//	}
//	
//	
//	public Session testGetSession(){
//		return sessionFactory.getCurrentSession();
//	}
	
//	@Test
//	public void testSave(){
//		PersonalityType personalityType = new PersonalityType("TestPersonality", "Description1", "Description2");
//		dao.save(personalityType);
//		PersonalityType personality = dao.getById(personalityType.getTypeID());
//		assertEquals("TestPersonality", personality.getName());
//		assertEquals("Description1", personality.getSecondaryDescription());
//		assertEquals("Description1", personality.getPrimaryDescription());
//	}
	
	@Test
	public void testGetAll(){
		List<PersonalityType> results = dao.getAll();
		for (int i = 1; i <= results.size(); i ++){
			assertEquals(i, results.get(i-1).getTypeID());
		}
	}
	
	@Test
	public void testGetById(){
		int personalityTypeID = 1;
		PersonalityType personality = dao.getById(personalityTypeID);
		assertNotNull(personality);
		assertEquals("Perfectionist", personality.getName());
	}
	
//	@Test
//	public void testUpdate(){
//		PersonalityType toUpdate;
//		toUpdate = dao.getById(10);
//		assertNotNull(toUpdate);
//		assertEquals(toUpdate.getName(), "TestPersonality");
//		assertEquals(toUpdate.getPrimaryDescription(), "Description1");
//		
//		toUpdate.setPrimaryDescription("UpdatedDescription1");
//		dao.update(toUpdate);
//		
//		PersonalityType updated;
//		updated = dao.getById(10);
//		assertEquals("UpdatedDescription1", updated.getPrimaryDescription());
//	}
//	
//	@Test
//	public void testDelete(){
//		PersonalityType toDelete;
//		toDelete = dao.getById(10);
//		assertNotNull(toDelete);
//		assertEquals(toDelete.getName(), "TestPersonality");
//		
//		dao.delete(toDelete);
//		toDelete = dao.getById(10);
//		assertNull(toDelete);
//	}
}