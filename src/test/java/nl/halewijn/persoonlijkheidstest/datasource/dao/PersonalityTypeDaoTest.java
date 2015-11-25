package nl.halewijn.persoonlijkheidstest.datasource.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
//import org.junit.FixMethodOrder;
//import org.junit.runners.MethodSorters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import nl.halewijn.persoonlijkheidstest.Application;
import nl.halewijn.persoonlijkheidstest.datasource.dao.PersonalityTypeDao;
import nl.halewijn.persoonlijkheidstest.datasource.util.DatabaseConfig;
//import nl.halewijn.persoonlijkheidstest.datasource.util.TestDatabaseConfig;
import nl.halewijn.persoonlijkheidstest.domain.PersonalityType;

//@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("applicationContext.xml")
//@ContextConfiguration(classes = {TestDatabaseConfig.class})
@SpringApplicationConfiguration(Application.class)
//@WebIntegrationTest
//@FixMethodOrder(MethodSorters.DEFAULT)
public class PersonalityTypeDaoTest {
	@Autowired
	private PersonalityTypeDao dao;
	
	private static String type = "TestPersonality";
	private static String description1 = "Description1";
	private static String description2 = "Description2";
	private PersonalityType personality;
	private static int TypeID;
	
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
	@Transactional
	@Rollback(false)
	public void testSave(){
		personality = new PersonalityType(type, description1, description2);
		dao.save(personality);
		//dao.save(personalityType);
		//personality = dao.getById(personalityType.getTypeID());
		//personality = personalityType;
		this.TypeID = personality.getTypeID();
		assertEquals(type, personality.getName());
		assertEquals(description1, personality.getPrimaryDescription());
		assertEquals(description2, personality.getSecondaryDescription());
	}
	
	@Test
	@Transactional
	public void testGetAll(){
		List<PersonalityType> results = dao.getAll();
		int i = 1;
		for (i = 1; i <= results.size(); i ++){
			//assertEquals(i, results.get(i-1).getTypeID());
			System.out.println("GetAll ID: " + results.get(i-1).getTypeID());
		}
		assertEquals(10, results.size());
		System.out.println("TypeID test GetAll: " + TypeID);
	}
	
	@Test
	@Transactional
	public void testGetById(){
		System.out.println("TypeID test getById: " + TypeID);
		PersonalityType personality = dao.getById(TypeID);
		System.out.println("TESTESTSTEST " + personality.getName());
		assertNotNull(personality);
		assertEquals(type, personality.getName());
	}
	
	@Test
	@Transactional
	public void testUpdate(){
		PersonalityType toUpdate;
		toUpdate = dao.getById(TypeID);
		//toUpdate = personality;
		assertNotNull(toUpdate);
		assertEquals(toUpdate.getName(), type);
		assertEquals(toUpdate.getPrimaryDescription(), description1);
		assertEquals(toUpdate.getSecondaryDescription(), description2);
		
		toUpdate.setPrimaryDescription(newDescription1);
		toUpdate.setSecondaryDescription(newDescription2);
		dao.update(toUpdate);
		
		PersonalityType updated;
		updated = dao.getById(TypeID);
		//updated = toUpdate;
		personality = updated;
		assertEquals(newDescription1, updated.getPrimaryDescription());
		assertEquals(newDescription2, updated.getSecondaryDescription());
	}
	
	@Test
	@Transactional
	@Rollback(false)
	public void testDelete(){
		PersonalityType toDelete;
		toDelete = dao.getById(TypeID);
		//toDelete = personality;
		assertNotNull(toDelete);
		assertEquals(toDelete.getName(), type);
		
		dao.delete(toDelete);
		//PersonalityType deleted = dao.getById(TypeID);
		List<PersonalityType> results = dao.getAll();
		for (int i = 1; i < results.size(); i ++){
			assertNotEquals(results.get(i), toDelete);
		}
		//assertNull(toDelete);
		//assertNull(deleted);
	}
}