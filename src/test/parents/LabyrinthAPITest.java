package test.parents;

import java.util.ArrayList;
import java.util.Random;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.HttpClients;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.labyrinth.client.ConstantsClient;
import com.labyrinth.client.GamesClient;
import com.labyrinth.client.HerosClient;
import com.labyrinth.client.MapsClient;
import com.labyrinth.client.MonstersClient;
import com.labyrinth.client.TilesClient;
import com.labyrinth.client.UserClient;

import test.helpers.Faker;
import test.models.RandomStrings;

public abstract class LabyrinthAPITest extends Assert
{
	protected boolean debug = false;
	
	protected String baseUrl = "http://localhost:8080/TheLabyrinth";
	protected String responseString = "";

	protected HttpClient request = HttpClients.createDefault();
	protected CloseableHttpResponse response = null;
	protected Gson gson = new Gson();
	protected RandomStrings random = new RandomStrings();
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
	private boolean firstStartup = true;

	@BeforeSuite
	public void startup()
	{
		// only print this message once
		if(firstStartup)
		{
			System.out.println("STARTING TESTS ...\n\n");
			firstStartup = false;
		}
		
		userClient = new UserClient(email, password1);
		// create a user for the test suite
		userObj = gson.fromJson(userClient.createUser(data), JsonObject.class);
	}
	
	// for failure with the errors array
	protected void fail(ArrayList<String> messages)
	{
		String allMessages = "";
		
		for(String message: messages)
		{
			allMessages += message + "\n";
		}
		
		throw new RuntimeException(allMessages);
	}
	
	// for a custom message with the errors array
	protected void fail(String message, ArrayList<String> messages)
	{
		String allMessages = message;
		
		for(String m: messages)
		{
			allMessages += m + "\n";
		}
		
		throw new RuntimeException(allMessages);
	}
}
