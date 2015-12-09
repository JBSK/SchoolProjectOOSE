package nl.halewijn.persoonlijkheidstest.services;

import nl.halewijn.persoonlijkheidstest.domain.User;

public interface IUserRepository {

	User findByName(String userName);

	Long count();

}
