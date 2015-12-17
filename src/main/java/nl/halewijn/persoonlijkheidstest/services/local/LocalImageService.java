package nl.halewijn.persoonlijkheidstest.services.local;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nl.halewijn.persoonlijkheidstest.datasource.repository.ImageRepository;
import nl.halewijn.persoonlijkheidstest.domain.Image;

@Component
public class LocalImageService {
	
	@Autowired
	private ImageRepository localImageRepository;
	
	public Image getByImageId(int id) {
		return localImageRepository.findByImageId(id);
	}

	public Image save(Image image) {
		return localImageRepository.save(image);
	}
}