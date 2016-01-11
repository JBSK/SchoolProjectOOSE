package nl.halewijn.persoonlijkheidstest.datasource.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import nl.halewijn.persoonlijkheidstest.application.domain.Question;
import nl.halewijn.persoonlijkheidstest.application.domain.RoutingTable;

@Repository
public interface RoutingTableRepository extends JpaSpecificationExecutor<RoutingTable>, JpaRepository<RoutingTable, Long> {

    RoutingTable findByRouteId(int routeID);
    
    List<RoutingTable> findByQuestion(Question question);
}