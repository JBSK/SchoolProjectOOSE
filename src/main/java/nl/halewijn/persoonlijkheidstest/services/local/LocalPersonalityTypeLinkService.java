package nl.halewijn.persoonlijkheidstest.services.local;

import nl.halewijn.persoonlijkheidstest.datasource.repository.PersonalityTypeLinkRepository;
import nl.halewijn.persoonlijkheidstest.domain.PersonalityType;
import nl.halewijn.persoonlijkheidstest.domain.PersonalityTypeLink;
import nl.halewijn.persoonlijkheidstest.services.IObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LocalPersonalityTypeLinkService implements IObjectService<PersonalityTypeLink> {

	@Autowired
	private PersonalityTypeLinkRepository personalityTypeLinkRepository;

	@Override
	public PersonalityTypeLink save(PersonalityTypeLink personalityTypeLink) {
		return personalityTypeLinkRepository.save(personalityTypeLink);
	}

	@Override
	public void delete(PersonalityTypeLink personalityTypeLink) {
		personalityTypeLinkRepository.delete(personalityTypeLink);
	}

	@Override
	public List<PersonalityTypeLink> getAll() {
		return personalityTypeLinkRepository.findAll();
	}

	@Override
	public PersonalityTypeLink getById(int id) {
		return personalityTypeLinkRepository.findByLinkId(id);
	}

	public List<PersonalityTypeLink> getAllByPersonalityType(PersonalityType personalityType) {
		return personalityTypeLinkRepository.findAllByPersonalityType(personalityType);
	}

	@Override
	public PersonalityTypeLink update(PersonalityTypeLink personalityTypeLink) {
		return personalityTypeLinkRepository.save(personalityTypeLink);
	}
}