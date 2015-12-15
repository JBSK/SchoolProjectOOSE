package nl.halewijn.persoonlijkheidstest.datasource.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import nl.halewijn.persoonlijkheidstest.domain.Result;
import nl.halewijn.persoonlijkheidstest.domain.ResultTypePercentage;

@Repository
public interface ResultTypePercentageRepository extends JpaSpecificationExecutor<ResultTypePercentage>, JpaRepository<ResultTypePercentage, Long> {

	List<ResultTypePercentage> findByResult(Result result);


}