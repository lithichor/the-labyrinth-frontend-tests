package test.tests.api.user;

import java.util.Date;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;
import com.labyrinth.client.UserClient;

import test.helpers.UserVerifier;
import test.parents.LabyrinthAPITest;

public class UserPostTests extends LabyrinthAPITest
{
	private UserClient client;
	private UserVerifier verifier;

	@BeforeTest
	public void setup()
	{
		System.out.println("STARTNG USER POST TESTS");
		client = new UserClient("eric@eric.corn", "1qweqwe");
		verifier = new UserVerifier();
	}

	@Test
	public void createNewUser()
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
		String user = client.createUser(userJson);
		
		// verify response has a user
		if(!verifier.verifyUser(gson.fromJson(user, JsonObject.class)))
		{
			fail(verifier.getErrors());
		}
		
		// verify user was created
		UserClient client2 = new UserClient(email, password);
		String getUser = client2.getUser();
		
		if(!verifier.verifyUser(gson.fromJson(getUser, JsonObject.class)))
		{
			fail(verifier.getErrors());
		}
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
		String postUser = client.createUser(userJson);
		
		if(!postUser.contains(expectedMessage))
		{
			System.out.println("Response was: " + postUser);
			fail("The error message returned was not correct");
		}
	}
	
	@Test
	public void userPostWithArrayInData()
	{
		// local versions of the parent's data
		String first = firstName;
		String last = lastName;
		String email = faker.getEmail();
		String password = password1;
		String emailStr = "";
		
		// error in Labyrinth when password is empty array (#91)
		switch(rand.nextInt(3))
		{
		case 0:
			first = "[]";
			break;
		case 1:
			last = "[]";
			break;
		case 2:
			emailStr = new Date().getTime() +"@" + faker.getFirstName() + ".corn";
			email = "[" + emailStr + ".corn]";
			break;
		case 3:
			password = "[]";
			break;
		}
		String rawData = "{firstName: " + first + ","
				+ "lastName: " + last + ","
				+ "email: " + email + ","
				+ "password: " + "\"" + password + "\""
				+ "}";
		
		String response = userClient.createUser(rawData);
		
		// verify user created (response.contains(first, last, and email)
		if(!(response.contains(first) && response.contains(last) && response.contains(emailStr)))
		{
			System.out.println("ARRAYS");
			System.out.println("RAW_DATA: " + rawData);
			System.out.println("RESPONSE: " + response);
			fail("The response was not what was expected");
		}
		userClient.deleteUser();
	}

	@Test
	public void userPostWithHashInData()
	{
		// local versions of the parent's data
		String first = firstName;
		String last = lastName;
		String email = faker.getEmail();
		String password = password1;
		String emailStr = "";
		
		// error in Labyrinth when password is empty array (#91)
		switch(rand.nextInt(3))
		{
		case 0:
			first = "{" +  "}";
			break;
		case 1:
			last = "{}";
			break;
		case 2:
			emailStr = new Date().getTime() +"@" + faker.getFirstName() + ".corn";
			email = "{q:" + emailStr + "}";
			break;
		case 3:
			password = "{}";
			break;
		}
		String rawData = "{firstName: " + first + ","
				+ "lastName: " + last + ","
				+ "email: " + email + ","
				+ "password: " + "\"" + password + "\""
				+ "}";
		
		String response = userClient.createUser(rawData);

		// verify user created (response.contains(first, last, and email)
		if(!(response.contains(first) && response.contains(last) && response.contains(emailStr)))
		{
			System.out.println("HASHES");
			System.out.println("RAW_DATA: " + rawData);
			System.out.println("RESPONSE: " + response);
			fail("The response was not what was expected");
		}
		userClient.deleteUser();
	}
}
