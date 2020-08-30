package com.qa.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.utils.CommonUtilities;

public class NdtvWeatherPage {
	
	WebDriver driver;
	WebDriverWait wait;
	
	By SearchCityTextBox = By.id("searchBox");
	By SearchedCityResult = By.xpath("//div[@id='messages']/div[@style!='display: none;']//label");
	By SelectedCities = By.xpath("//div[@id='messages']//input[@checked='checked']");
	By Loading = By.id("loading");
	String TempCelcius= "//div[contains(text(),'city_name')]/preceding-sibling::div/span[@class=\"tempRedText\"]";
	String TempFahrenheit= "//div[contains(text(),'city_name')]/preceding-sibling::div/span[@class=\"tempWhiteText\"]";
	
	 public NdtvWeatherPage(WebDriver driver)
	{
		this.driver=driver;
		 wait=new WebDriverWait(this.driver, 15);
	}
	
	public boolean isCityAvailable(String cityname)
	{
		List<WebElement> cities= driver.findElements(SearchedCityResult);
		if(cities.size()>0)
		{
			for(WebElement we: cities)
			{
				if(we.getText().contains(cityname))
					return true;
			}
			return false;
		}
		else
			return false;
	}
	
	public void searchAndSelectACity(String cityname)
	{
		driver.findElement(SearchCityTextBox).clear();
		driver.findElement(SearchCityTextBox).sendKeys(cityname);
		//wait.until(ExpectedConditions.elementToBeClickable(SearchedCityResult));
		//driver.findElement(SearchedCityResult).click();
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("document.querySelector('[for=\""+cityname+"\"]').click()");
		
	}
	
	public void deselectAll()
	{
		List<WebElement> allcities= driver.findElements(SelectedCities);
		System.out.println(allcities.size()+" preselected cities to be deselected");
		for(WebElement city:allcities)
		{
				city.click();
				
		}
		System.out.println(allcities.size()+" preselected cities deselected");
	}
	
	public double getcelcius(String cityname)
	{
		String tempValue=driver.findElement(By.xpath(TempCelcius.replace("city_name", cityname))).getText();
		return CommonUtilities.getTempValue(tempValue);
	}
	
	public double getfahrenheit(String cityname)
	{
		String tempValue=driver.findElement(By.xpath(TempFahrenheit.replace("city_name", cityname))).getText();
		return CommonUtilities.getTempValue(tempValue);
	}
	
	public void waitforpageload()
	{
		wait.until(ExpectedConditions.attributeToBe(driver.findElement(Loading), "style", "display: none;"));
		System.out.println("Weather page successfully loaded");
	}

}
