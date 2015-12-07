package nl.halewijn.persoonlijkheidstest.datasource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import nl.halewijn.persoonlijkheidstest.domain.User;

@Repository
public interface UserRepository extends JpaSpecificationExecutor<User>, JpaRepository<User, Long> {

	@Query("select u from User u where u.id = ?")
	User findById(int id);

	User findByEmailAddress(String userName);

}