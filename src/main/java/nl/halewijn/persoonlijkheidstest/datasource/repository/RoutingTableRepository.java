package nl.halewijn.persoonlijkheidstest.datasource.repository;

import nl.halewijn.persoonlijkheidstest.domain.Question;
import nl.halewijn.persoonlijkheidstest.domain.RoutingRule;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nl.halewijn.persoonlijkheidstest.domain.RoutingTable;

@Repository
public interface RoutingTableRepository extends JpaSpecificationExecutor<RoutingTable>, JpaRepository<RoutingTable, Long> {

    RoutingTable findByRouteId(int RouteID);
    
    List<RoutingTable> findByQuestion(Question question);
}