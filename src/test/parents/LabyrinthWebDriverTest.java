package test.parents;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;

import test.models.Browser;
import test.models.constants.BrowserConstants;

public class LabyrinthWebDriverTest
{
	// this will be set when there is a test failure
	protected boolean failed = false;
	
	// TODO: parameterize the browser type string
	protected WebDriver browser = Browser.getInstance(BrowserConstants.FIREFOX);
	
	@AfterTest
	public void teardown()
	{
		if(!failed)
		{
			browser.close();
		}
	}
}
