package test.tests.web.login;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import test.pages.LoginPage;
import test.parents.LabyrinthWebDriverTest;

public class LoginTest extends LabyrinthWebDriverTest
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
	public void run() throws Exception
	{
		page.login(email, password);
		
		if(!page.verifyLoggedIn())
		{
			failed = true;
			throw new Exception("It looks like we aren't logged in");
		}
		if(!page.findLogoutLink())
		{
			failed = true;
			throw new Exception("It looks like the logout link is missing");
		}
	}
}
