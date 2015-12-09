package nl.halewijn.persoonlijkheidstest.datasource.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import nl.halewijn.persoonlijkheidstest.domain.Question;
import nl.halewijn.persoonlijkheidstest.domain.Theorem;
import nl.halewijn.persoonlijkheidstest.domain.TheoremBattle;

@Repository
public interface QuestionRepository extends JpaSpecificationExecutor<Question>, JpaRepository<Question, Long> {

	List<Question> findAllByText(String text);

	Question findByQuestionID(int questionId);

	List<TheoremBattle> findAllByFirstTheorem(Theorem firstTheorem);

}