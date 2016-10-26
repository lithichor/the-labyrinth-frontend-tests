package test.tests.api.user;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.labyrinth.client.UserClient;

import test.parents.LabyrinthAPITest;

public class UserPostTests extends LabyrinthAPITest
{
	private UserClient client;

	@BeforeTest
	public void setup()
	{
		System.out.println("STARTNG GAMES DELETE TESTS");
		client = new UserClient("eric@eric.corn", "1qweqwe");
	}

	@Test
	public void createGame()
	{
		// create a new user
		client.getUser();
	}
}
