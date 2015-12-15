package nl.halewijn.persoonlijkheidstest.services.local;

import static org.junit.Assert.*;

import nl.halewijn.persoonlijkheidstest.Application;
import nl.halewijn.persoonlijkheidstest.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@ActiveProfiles("test")
public class LocalUserServiceTest {

	@Autowired
	private LocalUserService localUserService;

	@Test
	public void getByIdTest() { // TODO: Improving test coverage
		String emailAddress = "test@test.com";
		User user = new User(emailAddress, true);
        user.setPasswordHash("test");
        user = localUserService.save(user);
		user = localUserService.getById(user.getId());
		assertEquals(emailAddress, user.getEmailAddress());
	}

	@Test
	public void userCRUD() {
		User user = new User("Name", false);
		user.setPasswordHash("pass");
		user = localUserService.save(user);
		
		user.setAdmin(true);
		user = localUserService.update(user);
		assertEquals(true, localUserService.findByEmailAddress("Name").isAdmin());
		
		localUserService.delete(user);
		assertEquals(null, localUserService.findByEmailAddress("Name"));
	}  
}