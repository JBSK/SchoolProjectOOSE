package nl.halewijn.persoonlijkheidstest.datasource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import nl.halewijn.persoonlijkheidstest.application.domain.PersonalityType;
import nl.halewijn.persoonlijkheidstest.application.domain.PersonalityTypeLink;

import java.util.List;

@Repository
public interface PersonalityTypeLinkRepository extends JpaSpecificationExecutor<PersonalityTypeLink>, JpaRepository<PersonalityTypeLink, Long> {

    PersonalityTypeLink findByLinkId(int linkId);

	List<PersonalityTypeLink> findAllByPersonalityType(PersonalityType personalityType);

}