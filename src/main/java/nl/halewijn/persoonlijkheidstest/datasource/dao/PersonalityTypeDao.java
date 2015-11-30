package nl.halewijn.persoonlijkheidstest.datasource.dao;

import nl.halewijn.persoonlijkheidstest.domain.PersonalityType;
import nl.halewijn.persoonlijkheidstest.domain.Question;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class PersonalityTypeDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession() {
		return sessionFactory.openSession();
	}
	
	/**
	* Save the new personality type in the database.
	*/
	
	@Transactional
	public void save(PersonalityType personalityType) {
		getSession().save(personalityType);
	}

	/**
	* Return all the personality types stored in the database.
	*/
	public List<PersonalityType> getAll() {
		return getSession().createQuery("FROM PersonalityType").list();
	}

    /**
	* Retrieve a personality type by ID.
	*/
	public PersonalityType getById(int personalityTypeID) {
		return (PersonalityType) getSession().load(PersonalityType.class, personalityTypeID);
	}

	/**
	* Update the passed personality type in the database.
	*/
	
	@Transactional
	public void update(PersonalityType personalityType) {
		getSession().update(personalityType);
	}

	/**
	 * Delete the personality type from the database.
	 */
	public void delete(PersonalityType personalityType) {
		getSession().delete(personalityType);
	}

}