package test.parents;

import java.util.Random;

import org.testng.Assert;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.labyrinth.client.ConstantsClient;
import com.labyrinth.client.GamesClient;
import com.labyrinth.client.HerosClient;
import com.labyrinth.client.InstructionsClient;
import com.labyrinth.client.MapsClient;
import com.labyrinth.client.MonstersClient;
import com.labyrinth.client.TilesClient;
import com.labyrinth.client.TurnsClient;
import com.labyrinth.client.UserClient;

import test.helpers.Faker;

public abstract class LabyrinthAPITest extends Assert
{
	protected String baseUrl = "http://localhost:8080/TheLabyrinth";
	protected String responseString = "";

	protected Gson gson = new Gson();
	protected Faker faker = new Faker();
	protected Random rand = new Random();
	
	// clients
	protected HerosClient herosClient;
	protected UserClient userClient;
	protected GamesClient gamesClient;
	protected MapsClient mapsClient;
	protected TilesClient tilesClient;
	protected ConstantsClient constantsClient;
	protected MonstersClient monstersClient;
	protected TurnsClient turnsClient;
	protected InstructionsClient instructionsClient;
	
	// user
	protected String firstName = faker.getFirstName();
	protected String lastName = faker.getLastName();
	protected String email = firstName + "@" + lastName + ".corn";
	protected String password1 = faker.getPassword();
	private String data = "{firstName: " + firstName + ", "
			+ "lastName: " + lastName + ", "
			+ "email: " + email + ", "
			+ "password: " + password1
			+ "}";
	protected JsonObject userObj;
	
	/**
	 * create a default user client and user json object
	 */
	public void startup()
	{
		userClient = new UserClient(email, password1);
		// create a user for the test suite
		userObj = gson.fromJson(userClient.createUser(data), JsonObject.class);
	}

	/**
	 * This method creates a new user for cross-tenant tests
	 * 
	 * @return an array containing the email and password of the new user
	 */
	protected String[] createSecondUser()
	{
		String firstName = faker.getFirstName();
		String lastName = faker.getLastName();
		String email = firstName + "@" + lastName + ".corn";
		String password = faker.getPassword();
		String data = "{firstName: " + firstName + ", lastName: " + lastName + ", email: " + email + ", password: " + password + "}";
		userClient.createUser(data);
		
		// password isn't represented in the response from creating
		// a user, so we have to do it this way (as opposed to using
		// a JsonObject created from the response).
		return new String[]{email, password};
	}
}
