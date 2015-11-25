//package nl.halewijn.persoonlijkheidstest.domain;
//
//import static org.junit.Assert.*;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import nl.halewijn.persoonlijkheidstest.Application;
//
//@Transactional
//@RunWith(SpringJUnit4ClassRunner.class)
////@ContextConfiguration("applicationContext.xml")
////@ContextConfiguration(classes = {TestDatabaseConfig.class})
//@SpringApplicationConfiguration(Application.class)
////@WebIntegrationTest
//public class PersonalityTypeTest {
//	private PersonalityType personality = new PersonalityType("TestType", "Descr1", "Descr2");
//	
//	@Test
//	public void testSetTypeID(){
//		assertEquals(10, personality.getTypeID());
//		personality.setTypeID(11);
//	}
//	
//	@Test
//	public void testGetTypeID(){
//		assertEquals(11, personality.getTypeID());
//		personality.getTypeID();
//	}
//	
//	@Test
//	public void testSetName(){
//		
//	}
//	
//	@Test
//	public void testGetName(){
//		
//	}
//	
//	@Test
//	public void testSetPrimaryDescription(){
//		
//	}
//	
//	@Test
//	public void testGetPrimaryDescription(){
//		
//	}
//	
//	@Test
//	public void testSetSecondaryDescription(){
//		
//	}
//	
//	@Test
//	public void testGetSecondaryDescription(){
//		
//	}
//}