package nl.halewijn.persoonlijkheidstest.datasource.dao;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;

import nl.halewijn.persoonlijkheidstest.domain.OpenQuestion;
import nl.halewijn.persoonlijkheidstest.domain.Question;
import nl.halewijn.persoonlijkheidstest.domain.Theorem;
import nl.halewijn.persoonlijkheidstest.domain.TheoremBattle;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class QuestionDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession() {
		return sessionFactory.openSession();
	}
	
	/**
	* Save the new question in the database.
	*/
	public void save(Question question) {
		getSession().save(question);
	}

	/**
	* Return all the questions stored in the database.
	*/
	public List<Question> getAll() {
		return getSession().createQuery("FROM Question").list();
	}

    /**
	* Retrieve a question by ID.
	*/
	public Question getById(int questionID) {
		Question result;

		String typeOfQuestion = getTypeById(questionID);
		switch (typeOfQuestion) {
			case "nl.halewijn.persoonlijkheidstest.domain.OpenQuestion":
				result = (OpenQuestion) getSession().load(OpenQuestion.class, questionID);
				break;
			case "nl.halewijn.persoonlijkheidstest.domain.TheoremBattle":
				result = (TheoremBattle) getSession().load(TheoremBattle.class, questionID);
				break;
			default:
				result = null;
				break;
		}
		return result;
	}
	
	/**
	* Retrieve an questionType by ID.
	*/
	public String getTypeById(int questionID) {
		List<Question> questions = getSession().createQuery("FROM Question WHERE questionId = " + questionID).list();
        try {
            Question question = questions.get(0);
            return question.getClassName();
        } catch (IndexOutOfBoundsException e) {
            return "";
        }
	}

	/**
	* Update the passed question in the database.
	*/
	public void update(Question question) {
		getSession().update(question);
	}

	/**
	 * Delete the question from the database.
	 */
	public void delete(Question question) {
		if(!(getSession().contains(question))) {
			getSession().merge(question);
		}
		getSession().delete(getSession());
	}

}