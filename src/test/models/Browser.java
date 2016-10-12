package test.models;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import test.models.constants.BrowserConstants;

public class Browser
{
	private static WebDriver driver = null;

	public static WebDriver getBrowser(String type) throws RuntimeException
	{
		if (BrowserConstants.FIREFOX.equalsIgnoreCase(type))
		{
			driver =  new FirefoxDriver();
		}
		else if (BrowserConstants.CHROME.equalsIgnoreCase(type))
		{
			driver = new ChromeDriver();
		}
		else if(BrowserConstants.IE.equalsIgnoreCase(type))
		{
			driver = new InternetExplorerDriver();
		}
		else if(BrowserConstants.EDGE.equalsIgnoreCase(type))
		{
			driver = new InternetExplorerDriver();
		}
		else
		{
			throw new RuntimeException("The " + type + " driver isn't supported");
		}
		
		return driver;
	}
}

