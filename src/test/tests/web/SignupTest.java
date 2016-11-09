package test.tests.web;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import test.models.RandomStrings;
import test.pages.SignupPage;
import test.parents.LabyrinthWebDriverTest;

public class SignupTest extends LabyrinthWebDriverTest
{
	private RandomStrings strings = new RandomStrings();
	private String firstname = strings.oneWord();
	private String lastname = strings.oneWord();
	private String email = firstname + "." + lastname + "@eric.corn";
	private String password = strings.sentence(2);
	private SignupPage page;
	
	@BeforeTest
	public void setup()
	{
		page = new SignupPage(browser);
		page.visit();
	}
	
	@Test
	public void run() throws Exception
	{
		try
		{
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("firstname", firstname);
		params.put("lastname", lastname);
		params.put("email", email);
		params.put("password", password);
		params.put("confirm", password);
		page.signup(params);

		
		String greeting = browser.findElement(By.id("greetings")).getText();
		if(!(greeting.contains(firstname) && greeting.contains(lastname)))
		{
			failed = true;
			throw new Exception("The greeting doesn't include the first and last names");
		}
		}
		catch(Exception e)
		{
			failed = true;
			throw new Exception(e);
		}
	}

}
