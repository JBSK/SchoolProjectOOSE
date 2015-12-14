package nl.halewijn.persoonlijkheidstest.datasource.repository;

import nl.halewijn.persoonlijkheidstest.domain.ScoreConstant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreConstantRepository extends JpaSpecificationExecutor<ScoreConstant>, JpaRepository<ScoreConstant, Long> {

	ScoreConstant findByAnswer(char answer);

}