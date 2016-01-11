package nl.halewijn.persoonlijkheidstest.application.services.local;

import nl.halewijn.persoonlijkheidstest.application.domain.PersonalityType;
import nl.halewijn.persoonlijkheidstest.application.services.IObjectService;
import nl.halewijn.persoonlijkheidstest.datasource.repository.PersonalityTypeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LocalPersonalityTypeService implements IObjectService<PersonalityType> {

	@Autowired
	private PersonalityTypeRepository personalityTypeRepository;

	@Override
	public PersonalityType save(PersonalityType personalityType) {
		return personalityTypeRepository.save(personalityType);
	}

	@Override
	public void delete(PersonalityType personalityType) {
		personalityTypeRepository.delete(personalityType);
	}

	@Override
	public List<PersonalityType> getAll() {
		return personalityTypeRepository.findAll();
	}

	@Override
	public PersonalityType getById(int id) {
		return personalityTypeRepository.findByTypeId(id);
	}

}