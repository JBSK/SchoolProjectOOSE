package nl.halewijn.persoonlijkheidstest.services.local;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nl.halewijn.persoonlijkheidstest.datasource.repository.ButtonRepository;
import nl.halewijn.persoonlijkheidstest.domain.Button;

@Component
public class LocalButtonService {
	
	@Autowired
	private ButtonRepository buttonRepository;
	
	public Button getById(int id) {
		return buttonRepository.findByButtonId(id);
	}
}