package nl.halewijn.persoonlijkheidstest.services.local;


import nl.halewijn.persoonlijkheidstest.datasource.repository.UserRepository;
import nl.halewijn.persoonlijkheidstest.domain.User;
import nl.halewijn.persoonlijkheidstest.services.IObjectService;

import java.util.List;
import java.util.logging.Logger;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocalUserService implements IObjectService<User>  {

	private Logger logger = Logger.getLogger(getClass().getName());
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public void save(User o) {
		userRepository.save(o);
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
		return userRepository.findByName(userName);
	}
}