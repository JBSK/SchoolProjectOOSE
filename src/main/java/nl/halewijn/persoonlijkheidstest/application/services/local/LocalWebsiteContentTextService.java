package nl.halewijn.persoonlijkheidstest.application.services.local;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nl.halewijn.persoonlijkheidstest.application.domain.WebsiteContentText;
import nl.halewijn.persoonlijkheidstest.datasource.repository.WebsiteContentTextRepository;

@Component
public class LocalWebsiteContentTextService {
	
	@Autowired
	private WebsiteContentTextRepository websiteContentTextRepository;
	
	public WebsiteContentText getByContentId(int id) {
		return websiteContentTextRepository.findByContentId(id);
	}

	public WebsiteContentText save(WebsiteContentText text) {
		return websiteContentTextRepository.save(text);
	}
}