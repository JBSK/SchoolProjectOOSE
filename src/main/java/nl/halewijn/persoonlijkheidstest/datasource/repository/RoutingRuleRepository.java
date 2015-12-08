package nl.halewijn.persoonlijkheidstest.datasource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nl.halewijn.persoonlijkheidstest.domain.RoutingRule;

@Repository
public interface RoutingRuleRepository extends JpaSpecificationExecutor<RoutingRule>, JpaRepository<RoutingRule, Long> {


}