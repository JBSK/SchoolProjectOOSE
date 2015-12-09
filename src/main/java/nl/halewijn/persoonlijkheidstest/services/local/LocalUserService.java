package nl.halewijn.persoonlijkheidstest.services.local;

import nl.halewijn.persoonlijkheidstest.datasource.repository.UserRepository;
import nl.halewijn.persoonlijkheidstest.domain.User;
import nl.halewijn.persoonlijkheidstest.services.IObjectService;
import nl.halewijn.persoonlijkheidstest.services.IUserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocalUserService implements IObjectService<User>, IUserRepository  {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public void delete(User user) {
		userRepository.delete(user);
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
	public User update(User user) {
		return userRepository.save(user);
	}

	@Override
	public User findByName(String userName) {
		return userRepository.findByEmailAddress(userName);
	}
	
	@Override
	public Long count() {
		return userRepository.count();
	}
}