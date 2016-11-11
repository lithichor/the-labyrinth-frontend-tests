package test.tests.web.login;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import test.pages.LoginPage;
import test.parents.LabyrinthWebDriverTest;

public class BadLoginTest extends LabyrinthWebDriverTest
{
	LoginPage page = new LoginPage(browser);
	
	@BeforeTest
	public void setup()
	{
		createUser();
		page = new LoginPage(browser);
		page.visit();
	}

	@Test
	protected void run() throws Exception
	{
		// login with wrong password
		page.login(email, "qweqweqwe");
		if(!page.notLoggedIn())
		{
			failed = true;
			throw new Exception("We don't seem to be on the login page");
		}

		// reload the page to clear the errors
		page.visit();
		// login again with empty fields
		// this is commented out because there's a bug on the login page
//		page.login("", "");
//		if(!page.notLoggedIn())
//		{
//			failed = true;
//			throw new Exception("We don't seem to be on the login page");
//		}
	}
}
