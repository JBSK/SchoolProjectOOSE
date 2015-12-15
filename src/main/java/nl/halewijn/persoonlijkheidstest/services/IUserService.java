package nl.halewijn.persoonlijkheidstest.services;

import nl.halewijn.persoonlijkheidstest.domain.User;

public interface IUserService {

	User findByEmailAddress(String userName);

	Long count();

}
