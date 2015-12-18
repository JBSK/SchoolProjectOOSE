package nl.halewijn.persoonlijkheidstest.datasource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import nl.halewijn.persoonlijkheidstest.domain.WebsiteContentText;

@Repository
public interface WebsiteContentTextRepository extends JpaSpecificationExecutor<WebsiteContentText>, JpaRepository<WebsiteContentText, Long>{
	WebsiteContentText findByContentId(int id);
}