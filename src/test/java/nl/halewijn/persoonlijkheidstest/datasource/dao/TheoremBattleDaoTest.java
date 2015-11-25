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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import nl.halewijn.persoonlijkheidstest.Application;
import nl.halewijn.persoonlijkheidstest.datasource.dao.PersonalityTypeDao;
import nl.halewijn.persoonlijkheidstest.datasource.util.DatabaseConfig;
//import nl.halewijn.persoonlijkheidstest.datasource.util.TestDatabaseConfig;
import nl.halewijn.persoonlijkheidstest.domain.PersonalityType;
import nl.halewijn.persoonlijkheidstest.domain.Question;
import nl.halewijn.persoonlijkheidstest.domain.Theorem;
import nl.halewijn.persoonlijkheidstest.domain.TheoremBattle;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("applicationContext.xml")
//@ContextConfiguration(classes = {TestDatabaseConfig.class})
@SpringApplicationConfiguration(Application.class)
//@WebIntegrationTest
public class TheoremBattleDaoTest {
	@Autowired
	private TheoremBattleDao dao;
	@Autowired
	private QuestionDao daoQuestion;
	@Autowired
	private TheoremDao daoTheorem;
	
	private Theorem theorem1;
	private Theorem theorem2;
	private Theorem newTheorem1;
	private Theorem newTheorem2;
	private static int TheoremID;
	
	private static String theoremBattleText = "TestTheoremBattle";
	private static String newTheoremBattleText = "UpdatedTestTheoremBattle";
	
	@Before
	public void setUpDB(){
		theorem1 = daoTheorem.getById(1);
		theorem2 = daoTheorem.getById(2);
		newTheorem1 = daoTheorem.getById(3);
		newTheorem2 = daoTheorem.getById(4);
	}
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
		TheoremBattle theoremBattle = new TheoremBattle(theoremBattleText, theorem1, theorem2);
		dao.save(theoremBattle);
		TheoremBattle insertedTheoremBattle = dao.getById(theoremBattle.getID());
		this.TheoremID = insertedTheoremBattle.getID();
		assertEquals(insertedTheoremBattle.getID(), theoremBattle.getID());
		assertEquals(theoremBattleText, insertedTheoremBattle.getText());
		assertEquals(theorem1, insertedTheoremBattle.getFirstTheorem());
		assertEquals(theorem2, insertedTheoremBattle.getSecondTheorem());
		assertEquals(theorem1, dao.getById(TheoremID).getFirstTheorem());
	}
	
	@Test
	@Transactional
	public void testGetAll(){
		List<Question> results = daoQuestion.getAll();
		for (int i = 1; i <= results.size(); i ++){
			assertEquals(i, results.get(i-1).getID());
		}
	}
	
	@Test
	@Transactional
	public void testGetById(){
		TheoremBattle battle = dao.getById(TheoremID);
		assertNotNull(battle);
		assertEquals(theoremBattleText, battle.getText());
	}
	
	@Test
	@Transactional
	public void testUpdate(){
		TheoremBattle toUpdate;
		toUpdate = dao.getById(TheoremID);
		assertNotNull(toUpdate);
		assertEquals(theoremBattleText, toUpdate.getText());
		System.out.println("ID: " + TheoremID);
		System.out.println("Vraag2: " + toUpdate.getSecondTheorem());
		System.out.println("Vraag1: " + toUpdate.getFirstTheorem());
		assertEquals(theorem1, dao.getById(TheoremID).getFirstTheorem());
		assertEquals(theorem1, toUpdate.getFirstTheorem());
		assertEquals(theorem2, toUpdate.getSecondTheorem());
		
		toUpdate.setText(newTheoremBattleText);
		toUpdate.setFirstTheorem(newTheorem1);
		toUpdate.setSecondTheorem(newTheorem2);
		dao.update(toUpdate);
		
		TheoremBattle updated;
		updated = dao.getById(TheoremID);
		assertEquals(newTheoremBattleText, updated.getText());
		assertEquals(newTheorem1, updated.getFirstTheorem());
		assertEquals(newTheorem2, updated.getSecondTheorem());
	}
	
	@Test
	@Transactional
	public void testDelete(){
		TheoremBattle toDelete;
		toDelete = dao.getById(TheoremID);
		assertNotNull(toDelete);
		assertEquals(newTheoremBattleText, toDelete.getText());
		assertEquals(theorem1, toDelete.getFirstTheorem());
		assertEquals(theorem2, toDelete.getSecondTheorem());
		
		dao.delete(toDelete);
		TheoremBattle deleted = dao.getById(10);
		//assertNull(toDelete);
		assertNull(deleted);
	}
}