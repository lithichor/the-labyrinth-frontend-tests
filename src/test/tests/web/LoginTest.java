package test.tests.web;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import test.pages.LoginPage;
import test.parents.LabyrinthWebDriverTest;

public class LoginTest extends LabyrinthWebDriverTest
{
	private String username = "eric@eric.corn";
	private String password = "1qweqwe";
	LoginPage page = new LoginPage(browser);
	
	@BeforeTest
	public void setup()
	{
		page = new LoginPage(browser);
		page.visit();
	}
	
	@Test
	public void run()
	{
		page.enterUsername(username);
		page.enterPassword(password);
		page.clickSubmit();
	}

}
