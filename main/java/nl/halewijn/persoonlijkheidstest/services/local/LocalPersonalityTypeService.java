
package nl.halewijn.persoonlijkheidstest.services.local;


import nl.halewijn.persoonlijkheidstest.datasource.dao.PersonalityTypeDao;
import nl.halewijn.persoonlijkheidstest.datasource.dao.TheoremDao;
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
	private PersonalityTypeDao personalityTypeDao;

	@Override
	public void save(PersonalityType personalityType) {
		personalityTypeDao.save(personalityType);
	}

	@Override
	public void delete(PersonalityType personalityType) {
		personalityTypeDao.delete(personalityType);
	}

	@Override
	public List<PersonalityType> getAll() {
		return personalityTypeDao.getAll();
	}

	@Override
	public PersonalityType getById(int id) {
		return personalityTypeDao.getById(id);
	}

	@Override
	public void update(PersonalityType personalityType) {
		personalityTypeDao.update(personalityType);
	}
}
