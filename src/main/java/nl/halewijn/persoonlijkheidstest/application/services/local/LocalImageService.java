package nl.halewijn.persoonlijkheidstest.application.services.local;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nl.halewijn.persoonlijkheidstest.application.domain.Image;
import nl.halewijn.persoonlijkheidstest.datasource.repository.ImageRepository;

@Component
public class LocalImageService {
	
	@Autowired
	private ImageRepository imageRepository;
	
	public Image getByImageId(int id) {
		return imageRepository.findByImageId(id);
	}

	public Image save(Image image) {
		return imageRepository.save(image);
	}
}