package nl.halewijn.persoonlijkheidstest.datasource.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import nl.halewijn.persoonlijkheidstest.domain.Question;

@Repository
public interface QuestionRepository extends JpaSpecificationExecutor<Question>, JpaRepository<Question, Long> {

	List<Question> findByText(String text);

	Question findByQuestionID(int questionID);

}