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
	
	private String type = "TestPersonality";
	private String description1 = "Description1";
	private String description2 = "Description2";
	private int TypeID;
	
	private String newDescription1 = "UpdatedDescription1";
	private String newDescription2 = "UpdatedDescription2";
	
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
	
	@Test
	public void testSave(){
		PersonalityType personalityType = new PersonalityType(type, description1, description2);
		dao.save(personalityType);
		PersonalityType personality = dao.getById(personalityType.getTypeID());
		this.TypeID = personality.getTypeID();
		assertEquals(type, personality.getName());
		assertEquals(description1, personality.getSecondaryDescription());
		assertEquals(description2, personality.getPrimaryDescription());
	}
	
	@Test
	public void testGetAll(){
		List<PersonalityType> results = dao.getAll();
		for (int i = 1; i <= results.size(); i ++){
			assertEquals(i, results.get(i-1).getTypeID());
		}
	}
	
	@Test
	public void testGetById(){
		PersonalityType personality = dao.getById(TypeID);
		assertNotNull(personality);
		assertEquals(type, personality.getName());
	}
	
	@Test
	public void testUpdate(){
		PersonalityType toUpdate;
		toUpdate = dao.getById(TypeID);
		assertNotNull(toUpdate);
		assertEquals(toUpdate.getName(), type);
		assertEquals(toUpdate.getPrimaryDescription(), description1);
		assertEquals(toUpdate.getSecondaryDescription(), description2);
		
		toUpdate.setPrimaryDescription(newDescription1);
		toUpdate.setSecondaryDescription(newDescription2);
		dao.update(toUpdate);
		
		PersonalityType updated;
		updated = dao.getById(TypeID);
		assertEquals(newDescription1, updated.getPrimaryDescription());
		assertEquals(newDescription2, updated.getSecondaryDescription());
	}
	
	@Test
	public void testDelete(){
		PersonalityType toDelete;
		toDelete = dao.getById(TypeID);
		assertNotNull(toDelete);
		assertEquals(toDelete.getName(), type);
		
		dao.delete(toDelete);
		PersonalityType deleted = dao.getById(TypeID);
		//assertNull(toDelete);
		assertNull(deleted);
	}
}