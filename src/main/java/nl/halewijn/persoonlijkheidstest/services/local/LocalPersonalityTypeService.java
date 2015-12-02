
package nl.halewijn.persoonlijkheidstest.services.local;


import nl.halewijn.persoonlijkheidstest.datasource.repository.PersonalityTypeRepository;
import nl.halewijn.persoonlijkheidstest.domain.PersonalityType;
import nl.halewijn.persoonlijkheidstest.domain.Theorem;
import nl.halewijn.persoonlijkheidstest.services.IPersonalityTypeService;
import nl.halewijn.persoonlijkheidstest.services.ITheoremService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Component
public class LocalPersonalityTypeService implements IPersonalityTypeService {

	private Logger logger = Logger.getLogger(getClass().getName());
	
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
		return personalityTypeRepository.findById(id);
	}

	@Override
	public PersonalityType update(PersonalityType personalityType) {
		return personalityTypeRepository.save(personalityType);
	}
}
