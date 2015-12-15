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

import java.util.Date;

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
        Date expectedRegisteredOn = user.getDateRegisteredOn();
        user = localUserService.save(user);
		user = localUserService.getById(user.getId());
		assertEquals(emailAddress, user.getEmailAddress());
        assertEquals(expectedRegisteredOn, user.getDateRegisteredOn());
	}

	@Test
	public void userCRUD() {
		User user = new User("name@test.net", false);
		user.setPasswordHash("pass");
		user = localUserService.save(user);
		
		user.setAdmin(true);
        user.setEmailAddress("name@test.com");
		user = localUserService.update(user);
		assertEquals(true, localUserService.findByEmailAddress("name@test.com").isAdmin());
		
		localUserService.delete(user);
		assertEquals(null, localUserService.findByEmailAddress("name@test.com"));
	}
}