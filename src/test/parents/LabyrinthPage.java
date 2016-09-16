package test.parents;

import org.openqa.selenium.WebDriver;

public abstract class LabyrinthPage
{
	protected String url = "http://localhost:8080/TheLabyrinth/";
	protected WebDriver browser;
	
	public abstract void visit();
}
