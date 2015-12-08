package nl.halewijn.persoonlijkheidstest.datasource.repository;

import nl.halewijn.persoonlijkheidstest.domain.RoutingRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nl.halewijn.persoonlijkheidstest.domain.RoutingTable;

@Repository
public interface RoutingTableRepository extends JpaSpecificationExecutor<RoutingTable>, JpaRepository<RoutingTable, Long> {

    RoutingTable findByRouteID(int RouteID);

    @Query("select q from Question q where q.id = ?")
    RoutingTable findByQuestionId(int questionId);

}