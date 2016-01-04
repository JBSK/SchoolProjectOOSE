package nl.halewijn.persoonlijkheidstest.services.local;

import nl.halewijn.persoonlijkheidstest.datasource.repository.UserRepository;
import nl.halewijn.persoonlijkheidstest.domain.User;
import nl.halewijn.persoonlijkheidstest.services.IObjectService;
import nl.halewijn.persoonlijkheidstest.services.IUserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocalUserService implements IObjectService<User>, IUserService  {

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
	public User findByEmailAddress(String emailAddress) {
		User user;

		if (emailAddress != null) {
			user = userRepository.findFirstByEmailAddress(emailAddress.toLowerCase());
		} else {
			user = null;
		}

		return user;
	}
	
	@Override
	public Long count() {
		return userRepository.count();
	}
}