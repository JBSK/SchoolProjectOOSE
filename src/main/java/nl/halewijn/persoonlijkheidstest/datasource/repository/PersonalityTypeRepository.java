package nl.halewijn.persoonlijkheidstest.datasource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import nl.halewijn.persoonlijkheidstest.application.domain.PersonalityType;

@Repository
public interface PersonalityTypeRepository extends JpaSpecificationExecutor<PersonalityType>, JpaRepository<PersonalityType, Long> {

	PersonalityType findByTypeId(int id);

}