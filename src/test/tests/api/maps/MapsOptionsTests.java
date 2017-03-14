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

	@Test
	public void verifyMapsGameOptions()
	{
		String options = mapsClient.getMapsGameOptions();
		assertTrue(verifier.verifyMapsGameOptions(options), verifier.getErrorsAsString());
	}
}
