package nl.halewijn.persoonlijkheidstest.datasource.repository;

import nl.halewijn.persoonlijkheidstest.domain.PersonalityType;
import nl.halewijn.persoonlijkheidstest.domain.PersonalityTypeLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonalityTypeLinkRepository extends JpaSpecificationExecutor<PersonalityTypeLink>, JpaRepository<PersonalityTypeLink, Long> {

    PersonalityTypeLink findByLinkId(int linkId);

	List<PersonalityTypeLink> findAllByPersonalityType(PersonalityType personalityType);

	List<PersonalityTypeLink> findAllByUrl(String url);

}