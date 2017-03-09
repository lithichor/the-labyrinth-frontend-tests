package test.tests.api.hero;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.labyrinth.client.HerosClient;

import test.parents.LabyrinthAPITest;

public class HerosOptionsTests extends LabyrinthAPITest
{
	@BeforeTest
	public void setup()
	{
		herosClient = new HerosClient(email, password1);
	}

	@Test
	public void verifyHerosOptions()
	{
		HerosVerifier verifier = new HerosVerifier();
		String options = herosClient.getHeroOptions();
		assertTrue(verifier.verifyHeroOptions(options), verifier.getErrorsAsString());
	}

//	@Test
	// to do after API Client number 38
	public void verifyHerosGameOptions()
	{
		HerosVerifier verifier = new HerosVerifier();
		// this will be getHerosGameOptions()
		String options = herosClient.getHeroOptions();
		assertTrue(verifier.verifyHeroOptions(options), verifier.getErrorsAsString());
	}
}
