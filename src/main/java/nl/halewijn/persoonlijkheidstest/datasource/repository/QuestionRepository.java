package nl.halewijn.persoonlijkheidstest.datasource.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nl.halewijn.persoonlijkheidstest.domain.Question;

@Repository
public interface QuestionRepository extends JpaSpecificationExecutor<Question>, JpaRepository<Question, Long> {

	@Query("select q from Question q where q.text=?")
	List<Question> findAllbyText(String text);

	@Query("select q from Question q where q.id=?")
	Question findById(int id);

}