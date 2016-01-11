package nl.halewijn.persoonlijkheidstest.datasource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import nl.halewijn.persoonlijkheidstest.application.domain.Button;

@Repository
public interface ButtonRepository extends JpaSpecificationExecutor<Button>, JpaRepository<Button, Long>{
	Button findByButtonId(int id);
}