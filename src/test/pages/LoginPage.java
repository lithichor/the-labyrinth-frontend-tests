package test.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import test.parents.LabyrinthPage;

public class LoginPage extends LabyrinthPage
{
	
	private String usernameId = "login_username";
	private String passwordId = "login_password";
	private String submitId = "login_submit";
	
	public LoginPage(WebDriver b)
	{
		this.browser = b;
		this.url = url + "/login";
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
}
