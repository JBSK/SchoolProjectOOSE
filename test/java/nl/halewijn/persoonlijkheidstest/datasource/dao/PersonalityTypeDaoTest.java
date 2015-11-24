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
import nl.halewijn.persoonlijkheidstest.domain.PersonalityType;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = DatabaseConfig.class)
@SpringApplicationConfiguration(Application.class)
@WebIntegrationTest
public class PersonalityTypeDaoTest {
	@Autowired
	private PersonalityTypeDao dao;
	
//	@Autowired
//	private SessionFactory sessionFactory;
//	
//	private Session session;
	
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
	
//	public Session testGetSession(){
//		return sessionFactory.getCurrentSession();
//	}
//	
//	@Test
//	@Transactional
//	public void testSave(PersonalityType personalityType){
//		getSession.save(personalityType);
//		assertEquals();
//	}
//	
	@Test
	public void testGetAll(){
		List<PersonalityType> results = dao.getAll();
		for (int i = 1; i <= results.size(); i ++){
			assertEquals(i, results.get(i-1).getTypeID());
		}
	}
	
	@Test
	public void testGetById(){
		//DatabaseConfig dbc = new DatabaseConfig();
		//dbc.transactionManager();
		//dao = new PersonalityTypeDao();
		int personalityTypeID = 1;
		//PersonalityType result = dao.getById(personalityTypeID);
		//assertEquals(personalityTypeID, result.getTypeID());
		assertNotNull(dao.getById(personalityTypeID));
		assertEquals("Perfectionist", dao.getById(personalityTypeID).getName());
		//assertEquals("test", "test");
		//assertNull((PersonalityType) testGetSession().load(PersonalityType.class, personalityTypeID));
	}
	
//	@Test
//	@Transactional
//	public void testUpdate(){
//		dao = new PersonalityTypeDao();
//		PersonalityType personalityType = ;
//		assertEquals();
//	}
//	
//	@Test
//	@Transactional
//	public void testDelete(){
//		dao = new PersonalityTypeDao();
//		PersonalityType personalityType = ;
//		
//	}
}