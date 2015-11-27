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
public class OpenQuestionDao {
	
	@Autowired	
	private SessionFactory sessionFactory;
	
	private Session getSession() {
		return sessionFactory.openSession();
	}
	
	/**
	* Save the new question in the database.
	*/
	public void save(OpenQuestion question) {
		getSession().save(question);
	}

	/**
	* Return all the questions stored in the database.
	*/
	public List<OpenQuestion> getAll() {
		return getSession().createQuery("FROM OpenQuestion").list();
	}

    /**
	* Retrieve an OpenQuestion by ID.
	*/
	public OpenQuestion getById(int questionID) {
		return (OpenQuestion) getSession().get(OpenQuestion.class, questionID);
	}

	/**
	* Update the passed question in the database.
	*/
	public void update(OpenQuestion question) {
		getSession().update(question);
	}

	/**
	 * Delete the question from the database.
	 */
	public void delete(OpenQuestion question) {
		getSession().delete(question);
	}

}