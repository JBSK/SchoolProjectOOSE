package nl.halewijn.persoonlijkheidstest.datasource.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import nl.halewijn.persoonlijkheidstest.domain.Stelling;

@Repository
@Transactional
public class StellingDao  {
	
	@Autowired
	private SessionFactory _sessionFactory;
	
	private Session getSession() {
	    return _sessionFactory.getCurrentSession();
	  }
	
	/**
	* Save stelling user in the database.
	*/
	
	public void save(Stelling stelling) {
		getSession().save(stelling);
		return;
	}
	  
	/**
	* Delete the stelling from the database.
	*/
	public void delete(Stelling stelling) {
		getSession().delete(stelling);
	}
	  
	/**
	* Return all the stellingen stored in the database.
	*/
	@SuppressWarnings("unchecked")
	public List<Stelling> getAll() {
		return getSession().createQuery("from Stelling").list();
	}

	/**
	* Return the Stelling having the passed id.
	*/
	public Stelling getById(long id) {
		return (Stelling) getSession().load(Stelling.class, id);
	}

	/**
	* Update the passed Stelling in the database.
	*/
	public void update(Stelling stelling) {
		getSession().update(stelling);
	}
}
