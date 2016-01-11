package nl.halewijn.persoonlijkheidstest.datasource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import nl.halewijn.persoonlijkheidstest.application.domain.ScoreConstant;

@Repository
public interface ScoreConstantRepository extends JpaSpecificationExecutor<ScoreConstant>, JpaRepository<ScoreConstant, Long> {

	ScoreConstant findByAnswer(String answer);

}