package nl.halewijn.persoonlijkheidstest.application.services.local;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nl.halewijn.persoonlijkheidstest.application.domain.Button;
import nl.halewijn.persoonlijkheidstest.datasource.repository.ButtonRepository;

@Component
public class LocalButtonService {
	
	@Autowired
	private ButtonRepository buttonRepository;
	
	public Button getByButtonId(int id) {
		return buttonRepository.findByButtonId(id);
	}

	public Button save(Button button) {
		return buttonRepository.save(button);
	}
}