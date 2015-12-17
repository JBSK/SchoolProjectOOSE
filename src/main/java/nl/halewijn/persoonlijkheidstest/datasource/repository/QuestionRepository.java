package nl.halewijn.persoonlijkheidstest.datasource.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import nl.halewijn.persoonlijkheidstest.domain.Question;
import nl.halewijn.persoonlijkheidstest.domain.Theorem;
import nl.halewijn.persoonlijkheidstest.domain.TheoremBattle;

@Repository
public interface QuestionRepository extends JpaSpecificationExecutor<Question>, JpaRepository<Question, Long> {

	@Query("select count(*) from Question q where q.active = 1")
	int countActiveQuestions();

	List<Question> findAllByText(String text);

	Question findByQuestionId(int questionId);

	List<TheoremBattle> findByFirstTheorem(Theorem firstTheorem);

	List<TheoremBattle> findBySecondTheorem(Theorem secondTheorem);

}