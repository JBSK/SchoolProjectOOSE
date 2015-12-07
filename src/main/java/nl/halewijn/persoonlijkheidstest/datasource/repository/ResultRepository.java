package nl.halewijn.persoonlijkheidstest.datasource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import nl.halewijn.persoonlijkheidstest.domain.Result;

@Repository
public interface ResultRepository extends JpaSpecificationExecutor<Result>, JpaRepository<Result, Long> {


}