package test.tests.api.user;

import com.labyrinth.client.UserClient;

import test.parents.LabyrinthAPITest;

public class UserAPITest extends LabyrinthAPITest
{
	// create a new user
	protected String firstName = random.oneWord();
	protected String lastName = random.oneWord();
	protected String email = firstName + "@" + lastName + ".corn";
	protected String password = random.oneWord() + "1Qq";
	
	protected void createNewUserClient()
	{
		// this email and password aren't in the db yet; we
		// can do this because creating a new user doesn't
		// require authentication
		userClient = new UserClient(email, password);
	}
	
	/**
	 * This creates a non-default user. While most tests use
	 * the default user, the user tests often require a user
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
		return userClient.createUser(newUserJson);
	}
	
	protected String deleteNewUser()
	{
		return userClient.deleteUser();
	}
}
