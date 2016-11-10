package test.tests.api.user;

import com.labyrinth.client.UserClient;

import test.parents.LabyrinthAPITest;

public class UserAPITest extends LabyrinthAPITest
{
	// create a new user
	protected String firstName = random.oneWord();
	protected String lastName = random.oneWord();
	protected String email = firstName + "@" + lastName + ".corn";
	protected String password = random.sentence(2);
	
	protected UserClient client;

	protected void createNewClient()
	{
		// this email and password aren't in the db yet; we
		// can do this because creating a new user doesn't
		// require authentication
		client = new UserClient(email, password);
	}
	
	/**
	 * This creates a non-default user. While most tests use
	 * the default user, most of the user tests require a user
	 * that can be altered and/or deleted.
	 * @return user - the string response from the user -POST method
	 */
	protected String createNewUser()
	{
		String newUserJson = "{firstName: \"" + firstName +
				"\", lastName: \"" + lastName +
				"\", email: \"" + email +
				"\", password: \"" + password +
				"\"}";
		return client.createUser(newUserJson);
	}
	
	protected String deleteNewUser()
	{
		return client.deleteUser();
	}
}
