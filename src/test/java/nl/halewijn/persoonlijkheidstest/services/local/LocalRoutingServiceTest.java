package nl.halewijn.persoonlijkheidstest.services.local;

import static org.junit.Assert.*;

import nl.halewijn.persoonlijkheidstest.Application;
import nl.halewijn.persoonlijkheidstest.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Transactional
@WebIntegrationTest("server.port:85")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@ActiveProfiles("test")
public class LocalRoutingServiceTest {

	@Autowired
	private LocalRoutingService localRoutingService;
	
	@Autowired
	private LocalPersonalityTypeService localPersonalityTypeService;
	
	@Autowired
	private LocalTheoremService localTheoremService;
	
	@Autowired
	private LocalQuestionService localQuestionService;
	
	@Test
	public void routingTableCR() {
		RoutingRule rule = new RoutingRule("Rule één");
		rule = localRoutingService.save(rule);
		rule.setDescription("Rule one");
		rule = localRoutingService.save(rule);
		assertEquals("Rule one", rule.getDescription());

		RoutingTable table = new RoutingTable(null, 'A', rule);
		localRoutingService.save(table);
		
		RoutingTable tableRule = localRoutingService.findById(rule.getRoutingRuleId());
		assertEquals(null, tableRule);
		assertEquals(null, tableRule);
	}  
	
	@Test
	public void routingTableDelete() {
		RoutingRule rule = new RoutingRule("Rule één");
		rule = localRoutingService.save(rule);
	
		PersonalityType type1 = new PersonalityType("Type", "tekst1", "tekst2");
		localPersonalityTypeService.save(type1);
		Theorem theorem = new Theorem(type1, "Stelling", 1.0, 0, 0, 0);
		localTheoremService.save(theorem);
		
		TheoremBattle battle = new TheoremBattle("Battle", theorem, theorem);
		localQuestionService.save(battle);
		
		RoutingTable table = new RoutingTable(battle, 'A', rule);
		localRoutingService.save(table);
		
		table.setRoutingRuleParam('C');
		table = localRoutingService.save(table);
		assertEquals('C', table.getRoutingRuleParam());

		localRoutingService.delete(table);
		assertEquals(null, localRoutingService.getById(rule.getRoutingRuleId()));
		
		assertEquals(new ArrayList<RoutingTable>(), localRoutingService.getAll());
	} 
}