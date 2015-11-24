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
public class TheoremBattleDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	/**
	* Save the new question in the database.
	*/
	public void save(TheoremBattle question) {
		getSession().save(question);
	}

	/**
	* Return all the questions stored in the database.
	*/
	public List<Question> getAll() {
		return getSession().createQuery("FROM TheoremBattle").list();
	}

    /**
	* Retrieve an OpenQuestion by ID.
	*/
	public TheoremBattle getById(int id) {
		return (TheoremBattle) getSession().get(TheoremBattle.class, id);
	}

	/**
	* Update the passed question in the database.
	*/
	public void update(TheoremBattle question) {
		getSession().update(question);
	}

	/**
	 * Delete the question from the database.
	 */
	public void delete(TheoremBattle question) {
		getSession().delete(question);
	}

}