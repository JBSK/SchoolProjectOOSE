package nl.halewijn.persoonlijkheidstest.application.services;

import nl.halewijn.persoonlijkheidstest.application.domain.User;

public interface IUserService {

	User findByEmailAddress(String userName);

	Long count();

}
