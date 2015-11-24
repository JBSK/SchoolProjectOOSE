//package nl.halewijn.persoonlijkheidstest.datasource.dao;
//
//import static org.junit.Assert.*;
//
//import java.util.List;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import nl.halewijn.persoonlijkheidstest.Application;
//import nl.halewijn.persoonlijkheidstest.domain.PersonalityType;
//import nl.halewijn.persoonlijkheidstest.domain.Question;
//import nl.halewijn.persoonlijkheidstest.domain.Theorem;
//import nl.halewijn.persoonlijkheidstest.domain.TheoremBattle;
//
//@Transactional
//@RunWith(SpringJUnit4ClassRunner.class)
////@ContextConfiguration("applicationContext.xml")
////@ContextConfiguration(classes = {TestDatabaseConfig.class})
//@SpringApplicationConfiguration(Application.class)
////@WebIntegrationTest
//public class QuestionDaoTest {
//	@Autowired
//	private QuestionDao dao;
//	private PersonalityTypeDao daoPersonality;
//	private TheoremDao daoTheorem;
//	
////	@Before
////	public void setUpDB(){
////		
////	}
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
//	public void testSave(){
//		PersonalityType perfectionist = daoPersonality.getById(1);
//		Theorem theorem1 = daoTheorem.getById(1);
//		Theorem theorem2 = daoTheorem.getById(2);
//		Question Question = new Question();
//		dao.save(theoremBattle);
//		TheoremBattle insertedTheoremBattle = dao.getById(theoremBattle.getID());
//		assertEquals(insertedTheoremBattle.getID(), theoremBattle.getID());
//		assertEquals("TestTheoremBattle", insertedTheoremBattle.getText());
//		assertEquals(theorem1, insertedTheoremBattle.getFirstTheorem());
//		assertEquals(theorem2, insertedTheoremBattle.getSecondTheorem());
//	}
//	
//	@Test
//	public void testGetAll(){
//		List<Question> results = daoQuestion.getAll();
//		for (int i = 1; i <= results.size(); i ++){
//			assertEquals(i, results.get(i-1).getID());
//		}
//	}
//	
//	@Test
//	public void testGetById(){
//		int theoremID = 1;
//		TheoremBattle battle = dao.getById(theoremID);
//		assertNotNull(battle);
//		assertEquals("Perfectionist", battle.getText());
//	}
//	
//	@Test
//	public void testGetTypeById(){
//		
//	}
//	
//	@Test
//	public void testUpdate(){
//		PersonalityType perfectionist = daoPersonality.getById(1);
//		Theorem theorem1 = daoTheorem.getById(1);
//		Theorem theorem2 = daoTheorem.getById(2);
//		Theorem newTheorem1 = daoTheorem.getById(3);
//		Theorem newTheorem2 = daoTheorem.getById(4);
//		TheoremBattle toUpdate;
//		toUpdate = dao.getById(10);
//		assertNotNull(toUpdate);
//		assertEquals("TestTheoremBattle", toUpdate.getText());
//		assertEquals(theorem1, toUpdate.getFirstTheorem());
//		assertEquals(theorem2, toUpdate.getSecondTheorem());
//		
//		toUpdate.setText("UpdatedTestTheoremBattle");
//		toUpdate.setFirstTheorem(newTheorem1);
//		toUpdate.setSecondTheorem(newTheorem2);
//		dao.update(toUpdate);
//		
//		TheoremBattle updated;
//		updated = dao.getById(10);
//		assertEquals("UpdatedTestTheoremBattle", updated.getText());
//		assertEquals(newTheorem1, updated.getFirstTheorem());
//		assertEquals(newTheorem2, updated.getSecondTheorem());
//	}
//	
//	@Test
//	public void testDelete(){
//		PersonalityType perfectionist = daoPersonality.getById(1);
//		TheoremBattle toDelete;
//		toDelete = dao.getById(10);
//		Theorem theorem1 = toDelete.getFirstTheorem();
//		Theorem theorem2 = toDelete.getSecondTheorem();
//		assertNotNull(toDelete);
//		assertEquals("TestTheoremBattle", toDelete.getText());
//		assertEquals(theorem1, toDelete.getFirstTheorem());
//		assertEquals(theorem2, toDelete.getSecondTheorem());
//		
//		// Delete TheoremBattle.
//		dao.delete(toDelete);
//		// Check if TheoremBattle is deleted.
//		TheoremBattle deleted = dao.getById(10);
//		assertNull(toDelete);
//		assertNull(deleted);
//	}
//}