package nl.halewijn.persoonlijkheidstest.repository;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import nl.halewijn.persoonlijkheidstest.Application;
import nl.halewijn.persoonlijkheidstest.domain.PersonalityType;
import nl.halewijn.persoonlijkheidstest.domain.Theorem;
import nl.halewijn.persoonlijkheidstest.services.local.LocalPersonalityTypeService;
import nl.halewijn.persoonlijkheidstest.services.local.LocalTheoremService;

@Transactional
@WebIntegrationTest("server.port:85")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@ActiveProfiles("test")
public class TheoremRepositoryTest {

    @Autowired
    private LocalPersonalityTypeService localPersonalityTypeService;

	@Autowired
	private LocalTheoremService localTheoremService;

	@Test
	@Transactional
	public void testGetAll(){
		List<Theorem> theoremsBefore = localTheoremService.getAll();
        PersonalityType examplePersonalityType = new PersonalityType("ExampleType", "Example description 1", "Example description 2");
		localPersonalityTypeService.save(examplePersonalityType);

		Theorem exampleTheorem = new Theorem(examplePersonalityType, "Example theorem", 1.2, 0, 0, 0);
		localTheoremService.save(exampleTheorem);
		theoremsBefore.add(exampleTheorem);
		
		List<Theorem> theoremsAfter = localTheoremService.getAll();
		assertEquals(theoremsBefore, theoremsAfter);
	}
	
	@Test
	public void testGetById(){
        String theoremText = "Example theorem text";

        PersonalityType examplePersonalityType = new PersonalityType("ExampleType", "Example description 1", "Example description 2");
		localPersonalityTypeService.save(examplePersonalityType);

        Theorem exampleTheorem = new Theorem(examplePersonalityType, theoremText, 1.2, 0, 0, 0);
        exampleTheorem = localTheoremService.save(exampleTheorem);

        Theorem insertedTheorem = localTheoremService.getById(exampleTheorem.getTheoremID());
		assertEquals(theoremText, insertedTheorem.getText());
	}
	
	@Test
	@Transactional
	public void testUpdate(){
        String originalTheoremName = "ExampleTheorem";
        String updatedTheoremName = "ExampleTheoremUpdated";

        PersonalityType examplePersonalityType = new PersonalityType("ExampleType", "Example description 1", "Example description 2");
		localPersonalityTypeService.save(examplePersonalityType);

        Theorem exampleTheorem = new Theorem(examplePersonalityType, originalTheoremName, 1.2, 0, 0, 0);
		localTheoremService.save(exampleTheorem);

        exampleTheorem.setText(updatedTheoremName);
		localTheoremService.save(exampleTheorem);

        Theorem updatedTheorem = localTheoremService.getById(exampleTheorem.getTheoremID());
		assertEquals(updatedTheoremName, updatedTheorem.getText());
	}
	
	@Test
	@Transactional
	public void testDelete(){
        PersonalityType examplePersonalityType = new PersonalityType("ExampleType", "Example description 1", "Example description 2");
		localPersonalityTypeService.save(examplePersonalityType);

        Theorem exampleTheorem = new Theorem(examplePersonalityType, "Example theorem", 1.2, 0, 0, 0);
		localTheoremService.save(exampleTheorem);

        exampleTheorem = localTheoremService.getById(exampleTheorem.getTheoremID());
		localTheoremService.delete(exampleTheorem);

        assertNull(localTheoremService.getById(exampleTheorem.getTheoremID()));
	}
}