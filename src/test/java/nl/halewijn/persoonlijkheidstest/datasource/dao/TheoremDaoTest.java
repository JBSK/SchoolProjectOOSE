//package nl.halewijn.persoonlijkheidstest.datasource.dao;
//
//import static org.junit.Assert.*;
//
//import java.util.List;
//
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.boot.test.WebIntegrationTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import nl.halewijn.persoonlijkheidstest.Application;
//import nl.halewijn.persoonlijkheidstest.datasource.dao.PersonalityTypeDao;
//import nl.halewijn.persoonlijkheidstest.datasource.util.DatabaseConfig;
////import nl.halewijn.persoonlijkheidstest.datasource.util.TestDatabaseConfig;
//import nl.halewijn.persoonlijkheidstest.domain.PersonalityType;
//import nl.halewijn.persoonlijkheidstest.domain.Theorem;
//
////@Transactional
//@RunWith(SpringJUnit4ClassRunner.class)
////@ContextConfiguration("applicationContext.xml")
////@ContextConfiguration(classes = {TestDatabaseConfig.class})
//@SpringApplicationConfiguration(Application.class)
////@WebIntegrationTest
//public class TheoremDaoTest {
//	@Autowired
//	private TheoremDao dao;
//	@Autowired
//	private PersonalityTypeDao daoPersonality;
//	
//	private PersonalityType perfectionist;
//	
//	private static String theoremText = "TestTheorem";
//	private static double weight = 1.00;
//	private static int theoremID;
//	
//	private static String newTheoremText = "UpdatedTestTheorem";
//	private static double newWeight = 1.25;
//	
//	@Before
//	public void setUpDB(){
//		perfectionist = daoPersonality.getById(1);
//	}
////	
////	@After
////	public void tearDownDB(){
////		
////	}
////	
////	
////	public Session testGetSession(){
////		return sessionFactory.getCurrentSession();
////	}
//	
//	@Test
//	@Transactional
//	@Rollback(false)
//	public void testSave(){
//		Theorem theorem = new Theorem(perfectionist, theoremText, 1.00);
//		dao.save(theorem);
//		Theorem insertedTheorem = dao.getById(theorem.getTheoremID());
//		theoremID = insertedTheorem.getTheoremID();
//		assertEquals(perfectionist, theorem.getPersonalityType());
//		assertEquals(theoremText, insertedTheorem.getText());
//		assertEquals(weight, insertedTheorem.getWeight(), 0);
//	}
//	
//	@Test
//	@Transactional
//	public void testGetAll(){
//		List<Theorem> results = dao.getAll();
//		for (int i = 1; i <= results.size(); i ++){
//			assertEquals(i, results.get(i-1).getTheoremID());
//		}
//	}
//	
//	@Test
//	@Transactional
//	public void testGetById(){
//		System.out.println(theoremID);
//		Theorem theorem = dao.getById(theoremID);
//		assertNotNull(theorem);
//		assertEquals(perfectionist, theorem.getPersonalityType());
//		assertEquals(theoremText, theorem.getText());
//		assertEquals(weight, theorem.getWeight(), 0);
//	}
//	
//	@Test
//	@Transactional
//	public void testUpdate(){
//		Theorem toUpdate;
//		toUpdate = dao.getById(theoremID);
//		assertNotNull(toUpdate);
//		assertEquals(toUpdate.getPersonalityType(), perfectionist);
//		assertEquals(toUpdate.getText(), theoremText);
//		assertEquals(toUpdate.getWeight(), weight, 0);
//		
//		toUpdate.setText(newTheoremText);
//		toUpdate.setWeight(newWeight);
//		dao.update(toUpdate);
//		
//		Theorem updated;
//		updated = dao.getById(theoremID);
//		assertEquals(updated.getPersonalityType(), perfectionist);
//		assertEquals(newTheoremText, updated.getText());
//		assertEquals(newWeight, updated.getWeight(), 0);
//	}
//	
//	@Test
//	@Transactional
//	public void testDelete(){
//		Theorem toDelete;
//		toDelete = dao.getById(theoremID);
//		assertNotNull(toDelete);
//		assertEquals(toDelete.getPersonalityType(), perfectionist);
//		assertEquals(toDelete.getText(), newTheoremText);
//		assertEquals(toDelete.getWeight(), newWeight, 0);
//		
//		dao.delete(toDelete);
//		Theorem deleted = dao.getById(theoremID);
//		assertNull(deleted);
//		assertNull(toDelete);
//	}
//}