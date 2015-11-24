package nl.halewijn.persoonlijkheidstest.datasource.dao;

import java.util.List;
import javax.transaction.Transactional;
import nl.halewijn.persoonlijkheidstest.domain.Theorem;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class TheoremDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	/**
	* Save the new theorem in the database.
	*/
	public void save(Theorem theorem) {
		getSession().save(theorem);
	}
	  
	/**
	* Return all the theorems stored in the database.
	*/
	public List<Theorem> getAll() {
		return getSession().createQuery("FROM Theorem").list();
	}

	/**
	* Retrieve a theorem by ID.
	*/
	public Theorem getById(int theoremID) {
		return (Theorem) getSession().load(Theorem.class, theoremID);
	}

	/**
	* Update the passed theorem in the database.
	*/
	public void update(Theorem theorem) {
		getSession().update(theorem);
	}

	/**
	 * Delete the theorem from the database.
	 */
	public void delete(Theorem theorem) {
		getSession().delete(theorem);
	}

}