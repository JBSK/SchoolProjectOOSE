package nl.halewijn.persoonlijkheidstest.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class ResultTypePercentageTest {
	
	@Test
	public void gettersAndSettersTest() {
		PersonalityType type = new PersonalityType();
		
		ResultTypePercentage rtp = new ResultTypePercentage(null, type, 1);
		
		assertEquals(type, rtp.getPersonalityType());
		
		rtp.setPersonalityType(null);
		assertEquals(1, rtp.getPercentage(), 0);
		assertNull(rtp.getPersonalityType());
		assertNull(rtp.getResult());
	}
}