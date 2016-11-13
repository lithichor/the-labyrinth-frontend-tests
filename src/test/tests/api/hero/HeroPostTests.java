package test.tests.api.hero;

import org.junit.Test;
import org.testng.annotations.BeforeTest;

import com.labyrinth.client.HerosClient;

import test.parents.LabyrinthAPITest;

public class HeroPostTests extends LabyrinthAPITest
{
	@BeforeTest
	public void setup()
	{
		herosClient = new HerosClient(email, password1);
	}
	
	@Test
	public void verifyPostNotPermitted()
	{
		
	}
}
