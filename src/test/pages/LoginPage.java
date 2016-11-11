package test.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import test.parents.LabyrinthPage;

public class LoginPage extends LabyrinthPage
{
	
	private String usernameId = "login_username";
	private String passwordId = "login_password";
	private String submitId = "login_submit";
	
	private String logoutLink = "logout_link";
	
	public LoginPage(WebDriver b)
	{
		this.browser = b;
		this.url = url + "login";
	}
	
	public void visit()
	{
		browser.navigate().to(url);
	}
	
	public void login(String name, String password)
	{
		this.enterUsername(name);
		this.enterPassword(password);
		this.clickSubmit();
	}
	
	public void enterUsername(String name)
	{
		browser.findElement(By.id(usernameId)).sendKeys(name);
	}
	
	public void enterPassword(String pwd)
	{
		browser.findElement(By.id(passwordId)).sendKeys(pwd);
	}
	
	public void clickSubmit()
	{
		browser.findElement(By.id(submitId)).click();
	}
	
	public boolean findLogoutLink()
	{
		try
		{
			return browser.findElement(By.id(logoutLink)) != null;
		}
		// swallow the exception thrown by not finding the element
		catch(NoSuchElementException nsee)
		{
			return false;
		}
	}
	
	public boolean verifyLoggedIn()
	{
		String[] urlArray = browser.getCurrentUrl().split("/");
		if(!"login".equalsIgnoreCase(urlArray[urlArray.length - 1]))
		{
			return false;
		}
		return true;
	}
	
	public boolean notLoggedIn()
	{
		boolean loginFailed = true;
		
		try
		{
			// look for the three elements in the login form; if
			// not present, an exception will be thrown, and we
			// will know we're not logged in
			browser.findElement(By.id(usernameId));
			browser.findElement(By.id(passwordId));
			browser.findElement(By.id(submitId));
		}
		catch(NoSuchElementException nsee)
		{
			loginFailed = false;
		}
		
		return loginFailed;
	}
}
