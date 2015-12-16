package nl.halewijn.persoonlijkheidstest.datasource.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nl.halewijn.persoonlijkheidstest.domain.Answer;
import nl.halewijn.persoonlijkheidstest.domain.Result;

@Repository
public interface ResultRepository extends JpaSpecificationExecutor<Result>, JpaRepository<Result, Long> {

	@Query("select count(*) from Result r where r.user IS NOT NULL")
	Long countUserTests();

	@Query("select count(*) from Result r where r.user IS NULL")
	Long countAnonymousTests();

	Result getByResultId(int id);
	
	List<Result> getByUserId(int id);
	
	List<Answer> getAnswersByResultId(int id);
}