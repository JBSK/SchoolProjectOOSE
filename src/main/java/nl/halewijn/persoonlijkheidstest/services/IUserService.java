package nl.halewijn.persoonlijkheidstest.services;

import nl.halewijn.persoonlijkheidstest.domain.User;

public interface IUserService {

	User findByName(String userName);

	Long count();

}
