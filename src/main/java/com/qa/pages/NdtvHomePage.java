package com.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NdtvHomePage {
	
	WebDriver driver;
	WebDriverWait wait;
	
	By ShowHideMoreTabs = By.id("h_sub_menu");
	By DismissAlert = By.xpath("//div[@class='noti_btnwrap']//a[contains(text(),'No Thanks')]");
	By WeatherTab = By.linkText("WEATHER");
	By MoreTabVisible = By.xpath("//div[@id='subnav' and contains(@style,'block')]");
	By MoreTabHidden = By.xpath("//div[@id='subnav' and contains(@style,'none')]");
	 public NdtvHomePage(WebDriver driver)
	{
		this.driver=driver;
		wait=new WebDriverWait(this.driver, 40);
	}
	
	public boolean isMoreTabsVisible()
	{
		if(driver.findElement(MoreTabHidden).isDisplayed())
			return true;
		else
			return false;
	}
	
	public void showHideMoreTabs()
	{
		wait.until(ExpectedConditions.elementToBeClickable(ShowHideMoreTabs));
		driver.findElement(ShowHideMoreTabs).click();
		//JavascriptExecutor js=(JavascriptExecutor)driver;
		//js.executeScript("document.getElementById('h_sub_menu').click()");
		System.out.println("clicked on show hide button");
		
	}
	
	public NdtvWeatherPage switchToWeatherPage()
	{
		wait.until(ExpectedConditions.elementToBeClickable(WeatherTab));
		driver.findElement(WeatherTab).click();
		System.out.println("Switched to weather page");
		return new NdtvWeatherPage(driver);
	}
	
	public void waitAndDismissAlert()
	{
		try {
			wait.until(ExpectedConditions.elementToBeClickable(DismissAlert));
			driver.findElement(DismissAlert).click();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
	}

	
}
