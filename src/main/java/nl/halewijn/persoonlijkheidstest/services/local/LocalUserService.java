package nl.halewijn.persoonlijkheidstest.services.local;

import nl.halewijn.persoonlijkheidstest.datasource.repository.UserRepository;
import nl.halewijn.persoonlijkheidstest.domain.User;
import nl.halewijn.persoonlijkheidstest.services.IObjectService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocalUserService implements IObjectService<User>  {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User save(User o) {
		return userRepository.save(o);
	}

	@Override
	public void delete(User o) {
		userRepository.delete(o);
	}

	@Override
	public List<User> getAll() {
		return userRepository.findAll();
	}

	@Override
	public User getById(int id) {
		return userRepository.findById(id);
	}

	@Override
	public void update(User o) {
		userRepository.save(o);
	}

	public User findByName(String userName) {
		return userRepository.findByEmailAddress(userName);
	}
	
	public Long count() {
		return userRepository.count();
	}
}