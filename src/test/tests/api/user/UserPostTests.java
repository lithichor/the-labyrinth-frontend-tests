package test.tests.api.user;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.labyrinth.client.UserClient;

public class UserPostTests extends UserAPITest
{
	@BeforeTest
	public void setup()
	{
		System.out.println("STARTING USER POST TESTS");
		verifier = new UserVerifier();
		createNewUserClient();
	}

	@Test
	public void createNewUserTest()
	{
		String firstName = faker.getFirstName();
		String lastName = faker.getLastName();
		String email = firstName + "@" + lastName + ".corn";
		String password = faker.getPassword();
		
		// create json for a new user
		String userJson = "{firstName: \"" + firstName +
				"\", lastName: \"" + lastName +
				"\", email: \"" + email +
				"\", password: \"" + password +
				"\"}";
		String user = userClient.createUser(userJson);
		
		// verify response has a user
		assertTrue(verifier.verifyUser(user), verifier.getErrorsAsString());
		
		// verify user was created
		UserClient client2 = new UserClient(email, password);
		String getUser = client2.getUser();
		
		assertTrue(verifier.verifyUser(getUser), verifier.getErrorsAsString());
	}
	
	@Test
	public void createUserWithError()
	{
		String firstName = faker.getFirstName();
		String lastName = faker.getLastName();
		String email = firstName + "@" + lastName + ".corn";
		String password = faker.getPassword();
		String expectedMessage = "";
		int mod = rand.nextInt(4);

		// randomly set one of the fields to an empty string
		switch(mod % 4)
		{
			case 0:
				firstName = "";
				expectedMessage = "The Player needs to have a first name";
				break;
			case 1:
				lastName = "";
				expectedMessage = "The Player needs to have a last name";
				break;
			case 2:
				email = "";
				expectedMessage = "You need to include an email address";
				break;
			case 3:
				password = "";
				expectedMessage = "The Player needs a password";
				break;
		}
		
		// create json for a new user
		String userJson = "{firstName: \"" + firstName +
				"\", lastName: \"" + lastName +
				"\", email: \"" + email +
				"\", password: \"" + password +
				"\"}";
		
		// this will be an error message
		String postUser = userClient.createUser(userJson);
		
		assertTrue(postUser.contains(expectedMessage),
				"The error message returned was not correct: " + postUser);
	}
	
	@Test
	public void userPostWithEmptyArrayInData()
	{
		// local versions of the parent's data
		String first = firstName;
		String last = lastName;
		String email = faker.getEmail();
		String password = password1;
		String expectedMessage = "";
		
		switch(rand.nextInt(8))
		{
		case 0:
			first = "[]";
			expectedMessage = "The Player needs to have a first name";
			break;
		case 1:
			last = "[]";
			expectedMessage = "The Player needs to have a last name";
			break;
		case 2:
			email = "[]";
			expectedMessage = "You need to include an email address";
			break;
		case 3:
			password = "[]";
			expectedMessage = "The password needs to be more than six (6) characters";
			break;
		case 4:
			first = "[$, %, ^]";
			expectedMessage = "The Player needs to have a first name";
			break;
		case 5:
			last = "[a, b, c]";
			expectedMessage = "The Player needs to have a last name";
			break;
		case 6:
			email = "[1, 2, 3]";
			expectedMessage = "You need to include an email address";
			break;
		case 7:
			password = "[a, 1, @]";
			expectedMessage = "A password has to have at least one digit (0-9), one uppercase letter, and one lowercase letter";
			break;
		}
		String rawData = "{firstName: " + first + ","
				+ "lastName: " + last + ","
				+ "email: " + email + ","
				+ "password: " + "\"" + password + "\""
				+ "}";
		
		String response = userClient.createUser(rawData);
		
		// verify user created
		assertTrue(response.contains(expectedMessage),
				"The response was not what was expected:\n" + response);
	}

	@Test
	public void userPostWithEmptyHashInData()
	{
		// local versions of the parent's data
		String first = firstName;
		String last = lastName;
		String email = faker.getEmail();
		String password = password1;
		String expectedMessage = "";
		
		switch(rand.nextInt(8))
		{
		case 0:
			first = "{}";
			expectedMessage = "The Player needs to have a first name";
			break;
		case 1:
			last = "{}";
			expectedMessage = "The Player needs to have a last name";
			break;
		case 2:
			email = "{}";
			expectedMessage = "You need to include an email address";
			break;
		case 3:
			password = "{}";
			expectedMessage = "The password needs to be more than six (6) characters";
			break;
		case 4:
			first = "{a: 2}";
			expectedMessage = "The Player needs to have a first name";
			break;
		case 5:
			last = "{2: r}";
			expectedMessage = "The Player needs to have a last name";
			break;
		case 6:
			email = "{2: $}";
			expectedMessage = "You need to include an email address";
			break;
		case 7:
			password = "{*: 1}";
			expectedMessage = "A password has to have at least one digit (0-9), one uppercase letter, and one lowercase letter";
			break;
		}
		String rawData = "{firstName: " + first + ","
				+ "lastName: " + last + ","
				+ "email: " + email + ","
				+ "password: " + "\"" + password + "\""
				+ "}";
		
		String response = userClient.createUser(rawData);

		// verify user created
		assertTrue(response.contains(expectedMessage),
				"The response was not what was expected:\n" + response);
	}
}
