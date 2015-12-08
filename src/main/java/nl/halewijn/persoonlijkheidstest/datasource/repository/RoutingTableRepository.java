package nl.halewijn.persoonlijkheidstest.datasource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import nl.halewijn.persoonlijkheidstest.domain.RoutingTable;

@Repository
public interface RoutingTableRepository extends JpaSpecificationExecutor<RoutingTable>, JpaRepository<RoutingTable, Long> {


}