package nl.halewijn.persoonlijkheidstest.services.local;

import nl.halewijn.persoonlijkheidstest.datasource.repository.RoutingRuleRepository;
import nl.halewijn.persoonlijkheidstest.datasource.repository.RoutingTableRepository;
import nl.halewijn.persoonlijkheidstest.datasource.repository.UserRepository;
import nl.halewijn.persoonlijkheidstest.domain.RoutingRule;
import nl.halewijn.persoonlijkheidstest.domain.RoutingTable;
import nl.halewijn.persoonlijkheidstest.domain.User;
import nl.halewijn.persoonlijkheidstest.services.IObjectService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocalRoutingService {

	@Autowired
	private RoutingTableRepository routingTableRepository;

    public RoutingTable findById(int tableId) {
        return routingTableRepository.findByRouteID(tableId);
    }

    public RoutingTable findByQuestionId(int questionId) {
        return routingTableRepository.findByQuestionId(questionId);
    }

    public List<RoutingTable> getRoutingRulesByQuestionId(int questionId) {
        List<RoutingTable> routingTables = routingTableRepository.findAll();

        for (int i = 0; i < routingTables.size(); i++) {
            RoutingTable temp = routingTables.get(i);
            if (temp.getQuestion().getID() != questionId) {
                routingTables.remove(i); // Not the routing rule we're looking for, wrong question.
            }
        }

        return routingTables;
    }

}