
package nl.halewijn.persoonlijkheidstest.services.local;


import nl.halewijn.persoonlijkheidstest.datasource.repository.TheoremRepository;
import nl.halewijn.persoonlijkheidstest.domain.Theorem;
import nl.halewijn.persoonlijkheidstest.services.IObjectService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocalTheoremService implements IObjectService<Theorem> {

	@Autowired
	private TheoremRepository theoremRepository;

	@Override
	public Theorem save(Theorem theorem) {
		return theoremRepository.save(theorem);
	}

	@Override
	public void delete(Theorem theorem) {
		theoremRepository.delete(theorem);
	}

	@Override
	public List<Theorem> getAll() {
		return theoremRepository.findAll();
	}

	@Override
	public Theorem getById(int id) {
		return theoremRepository.findByTheoremID(id);
	}

	@Override
	public void update(Theorem theorem) {
		theoremRepository.save(theorem);
	}
}