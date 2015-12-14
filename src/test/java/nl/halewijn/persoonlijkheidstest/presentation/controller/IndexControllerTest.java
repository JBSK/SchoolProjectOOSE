package nl.halewijn.persoonlijkheidstest.presentation.controller;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import nl.halewijn.persoonlijkheidstest.Application;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@ActiveProfiles("test")
public class IndexControllerTest {
	
	@Autowired
	private IndexController indexController;
	
	@Test
	public void indexTest() {
		assertEquals("index", indexController.index());
	}
}