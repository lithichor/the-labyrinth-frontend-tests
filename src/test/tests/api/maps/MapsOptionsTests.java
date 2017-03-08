package test.tests.api.maps;

import org.testng.annotations.Test;

public class MapsOptionsTests extends MapsAPITests
{
	@Test
	public void verifyMapsOptions()
	{
		String options = mapsClient.getMapOptions();
		assertTrue(verifier.verifyMapsOptions(options), verifier.getErrorsAsString());
	}
}
