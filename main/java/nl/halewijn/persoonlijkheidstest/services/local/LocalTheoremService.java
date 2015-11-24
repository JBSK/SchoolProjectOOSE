
package nl.halewijn.persoonlijkheidstest.services.local;


import nl.halewijn.persoonlijkheidstest.datasource.dao.TheoremDao;
import nl.halewijn.persoonlijkheidstest.domain.Theorem;
import nl.halewijn.persoonlijkheidstest.services.ITheoremService;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocalTheoremService implements ITheoremService {

	private Logger logger = Logger.getLogger(getClass().getName());
	
	@Autowired
	private TheoremDao theoremDao;

	@Override
	public void save(Theorem theorem) {
		theoremDao.save(theorem);
	}

	@Override
	public void delete(Theorem theorem) {
		theoremDao.delete(theorem);
	}

	@Override
	public List<Theorem> getAll() {
		return theoremDao.getAll();
	}

	@Override
	public Theorem getById(int id) {
		return theoremDao.getById(id);
	}

	@Override
	public void update(Theorem theorem) {
		theoremDao.update(theorem);
	}
}
