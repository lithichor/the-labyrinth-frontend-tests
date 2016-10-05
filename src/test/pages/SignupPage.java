package test.pages;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import test.parents.LabyrinthPage;

public class SignupPage extends LabyrinthPage
{
	private String firstname = "signup_firstname";
	private String lastname = "signup_lastname";
	private String email = "signup_email";
	private String password = "signup_password";
	private String confirm = "signup_confirm";
	private String submit = "signup_submit";
	
	public SignupPage(WebDriver b)
	{
		this.browser = b;
		this.url = url + "/signup";
	}
	
	public void visit()
	{
		browser.navigate().to(url);
	}

	public void signup(HashMap<String, String> params)
	{
		browser.findElement(By.id(firstname)).sendKeys(params.get("firstname"));
		browser.findElement(By.id(lastname)).sendKeys(params.get("lastname"));
		browser.findElement(By.id(email)).sendKeys(params.get("email"));
		browser.findElement(By.id(password)).sendKeys(params.get("password"));
		browser.findElement(By.id(confirm)).sendKeys(params.get("confirm"));
		browser.findElement(By.id(submit)).click();
	}
}
