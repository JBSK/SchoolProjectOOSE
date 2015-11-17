
package nl.halewijn.persoonlijkheidstest.services.local;


import nl.halewijn.persoonlijkheidstest.datasource.dao.StellingDao;
import nl.halewijn.persoonlijkheidstest.domain.Stelling;
import nl.halewijn.persoonlijkheidstest.services.IStellingService;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocalStellingService implements IStellingService {

	private Logger logger = Logger.getLogger(getClass().getName());
	
	@Autowired
	private StellingDao stellingDao;

	@Override
	public void save(Stelling stelling) {
		stellingDao.save(stelling);
	}

	@Override
	public void delete(Stelling stelling) {
		stellingDao.delete(stelling);
	}

	@Override
	public List<Stelling> getAll() {
		return stellingDao.getAll();
	}

	@Override
	public Stelling getById(long id) {
		return stellingDao.getById(id);
	}

	@Override
	public void update(Stelling stelling) {
		stellingDao.update(stelling);
	}
}
