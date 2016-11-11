package test.parents;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;

import com.labyrinth.client.UserClient;

import test.models.Browser;
import test.models.RandomStrings;
import test.models.constants.BrowserConstants;

public abstract class LabyrinthWebDriverTest
{
	// this will be set when there is a test failure
	protected boolean failed = false;
	protected RandomStrings strings = new RandomStrings();
	protected UserClient userClient;
	
	// to create a user for the test
	protected String firstName = strings.oneWord();
	protected String lastName = strings.oneWord();
	protected String email = firstName + "@" + lastName + ".corn";
	protected String password = "1QWEqwe";
	
	// this will keep the browser from closing on passing tests
	private boolean debug = false;
	
	// TODO: TEST #5 - parameterize the browser type string
	protected WebDriver browser = Browser.getBrowser(BrowserConstants.FIREFOX);
	
	protected abstract void run()  throws Exception;
	
	protected String createUser()
	{
		String rawData =
				"{firstName: " + firstName + ","
				+ "lastName: " + lastName + ","
				+ "email: " + email + ","
				+ "password: " + password + "}";
		
		userClient = new UserClient(email, password);
		
		return userClient.createUser(rawData);
	}
	
	@AfterTest
	protected void teardown()
	{
		if(userClient != null)
		{
			userClient.deleteUser();
		}
		if(!failed && !debug)
		{
			browser.close();
		}
	}
}
