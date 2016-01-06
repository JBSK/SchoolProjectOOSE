package nl.halewijn.persoonlijkheidstest.services;

import java.util.List;

import nl.halewijn.persoonlijkheidstest.domain.Question;
import nl.halewijn.persoonlijkheidstest.domain.RoutingRule;
import nl.halewijn.persoonlijkheidstest.domain.RoutingTable;

public interface IRoutingService {

	List<RoutingTable> getRoutingRulesByQuestion(Question question);

	RoutingRule save(RoutingRule routingRule);

}
