package nl.halewijn.persoonlijkheidstest.application.services;

import java.util.List;

import nl.halewijn.persoonlijkheidstest.application.domain.Question;
import nl.halewijn.persoonlijkheidstest.application.domain.RoutingRule;
import nl.halewijn.persoonlijkheidstest.application.domain.RoutingTable;

public interface IRoutingService {

	List<RoutingTable> getRoutingRulesByQuestion(Question question);

	RoutingRule save(RoutingRule routingRule);

}
