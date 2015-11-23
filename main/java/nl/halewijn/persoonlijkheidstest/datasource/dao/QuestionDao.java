package nl.halewijn.persoonlijkheidstest.datasource.dao;

import java.util.List;
import javax.transaction.Transactional;

import nl.halewijn.persoonlijkheidstest.domain.OpenQuestion;
import nl.halewijn.persoonlijkheidstest.domain.Question;
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
		return sessionFactory.getCurrentSession();
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
		return (Question) getSession().load(Question.class, questionID);
	}
	
	/**
	* Retrieve an questionType by ID.
	*/
	public String getTypeById(int questionID) {
		Question q = (Question) getSession().createQuery("FROM Question").list();
		return q.getClassName();
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
		getSession().delete(question);
	}

}