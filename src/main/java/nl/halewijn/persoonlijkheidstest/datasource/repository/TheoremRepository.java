package nl.halewijn.persoonlijkheidstest.datasource.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import nl.halewijn.persoonlijkheidstest.application.domain.PersonalityType;
import nl.halewijn.persoonlijkheidstest.application.domain.Theorem;

@Repository
public interface TheoremRepository extends JpaSpecificationExecutor<Theorem>, JpaRepository<Theorem, Long> {

	// @Query annotation is not needed. Spring automatically generates the Query if you format the
	// methodname correctly.
	
	Theorem findByTheoremId(int id);
	
	List<Theorem> findByPersonalityType(PersonalityType type);

}