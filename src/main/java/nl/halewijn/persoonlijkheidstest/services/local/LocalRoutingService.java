package nl.halewijn.persoonlijkheidstest.services.local;

import nl.halewijn.persoonlijkheidstest.datasource.repository.RoutingRuleRepository;
import nl.halewijn.persoonlijkheidstest.datasource.repository.RoutingTableRepository;
import nl.halewijn.persoonlijkheidstest.domain.Question;
import nl.halewijn.persoonlijkheidstest.domain.RoutingRule;
import nl.halewijn.persoonlijkheidstest.domain.RoutingTable;
import nl.halewijn.persoonlijkheidstest.services.IObjectService;
import nl.halewijn.persoonlijkheidstest.services.IRoutingService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocalRoutingService implements IObjectService<RoutingTable>, IRoutingService {

	@Autowired
	private RoutingTableRepository routingTableRepository;
	
	@Autowired
	private RoutingRuleRepository routingRuleRepository;

    public RoutingTable findById(int tableId) {
        return routingTableRepository.findByRouteId(tableId);
    }

	@Override
    public List<RoutingTable> getRoutingRulesByQuestion(Question question) {
        return routingTableRepository.findByQuestion(question);
    }

	@Override
	public RoutingTable save(RoutingTable routingTable) {
		return routingTableRepository.save(routingTable);
	}

	@Override
	public void delete(RoutingTable routingTable) {
		routingTableRepository.delete(routingTable);
	}

	@Override
	public List<RoutingTable> getAll() {
		return routingTableRepository.findAll();
	}

	@Override
	public RoutingTable getById(int id) {
		return routingTableRepository.findByRouteId(id);
	}
	
	@Override
	public RoutingRule save(RoutingRule routingRule) {
		return routingRuleRepository.save(routingRule);
	}

}