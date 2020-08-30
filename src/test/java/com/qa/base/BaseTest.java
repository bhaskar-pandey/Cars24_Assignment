package com.qa.base;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import com.qa.base.BaseTest;

import io.restassured.RestAssured;



public class BaseTest {
	public WebDriver basedriver;
	
	
	@BeforeSuite
	public void suite_Level_Setup() {
		//RestAssured.baseURI="api.openweathermap.org";
		
		
		
		String os_name = System.getProperty("os.name").toLowerCase();
		if (os_name.contains("mac")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/drivers/chromedriver");
		} else {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/drivers/chromedriver.exe");
		}
		basedriver = new ChromeDriver();
		
		basedriver.manage().window().maximize();
		basedriver.manage().timeouts().implicitlyWait(3,TimeUnit.SECONDS); //used to wait for elements to render on screen
		basedriver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		System.out.println("********** Starting CARS24 Test Suite **********");
	}
	
	

	
	@AfterSuite
	public void suite_Level_TearDown(){
		basedriver.quit();
	}

}
