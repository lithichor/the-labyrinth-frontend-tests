package test.parents;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;

import test.models.Browser;
import test.models.constants.BrowserConstants;

public abstract class LabyrinthWebDriverTest
{
	// this will be set when there is a test failure
	protected boolean failed = false;
	private boolean debug = true;
	
	// TODO: TEST #5 - parameterize the browser type string
	protected WebDriver browser = Browser.getInstance(BrowserConstants.FIREFOX);
	
	protected abstract void run()  throws Exception;
	
	@AfterTest
	public void teardown()
	{
		if(!failed && !debug)
		{
			browser.close();
		}
	}
}
