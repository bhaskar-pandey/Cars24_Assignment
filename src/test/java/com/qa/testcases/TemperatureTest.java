package com.qa.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.base.BaseTest;
import com.qa.pages.NdtvHomePage;
import com.qa.pages.NdtvWeatherPage;
import com.qa.pojo.GetTemerature;
import com.qa.utils.CommonUtilities;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matcher.*;

import java.util.HashMap;
import java.util.Map;

public class TemperatureTest extends BaseTest{
	
	private WebDriver driver;
	NdtvHomePage ndtvhp;
	NdtvWeatherPage ndtvwp;
	HashMap<String,HashMap<String,Double>> resultmap;
	
	@BeforeTest
	public void beforeTest()
	{
		this.driver=basedriver;
		ndtvhp=new NdtvHomePage(driver);
		resultmap = new HashMap<String,HashMap<String,Double>>();
	}
	
	@Test(dataProvider="data",priority=1)
	public void GetUITemparature(String cityname)
	{
		driver.get("http://www.ndtv.com");
		ndtvhp.waitAndDismissAlert();
		if(!ndtvhp.isMoreTabsVisible())
		{
			ndtvhp.showHideMoreTabs();
		}
		ndtvwp=ndtvhp.switchToWeatherPage();
		
		ndtvwp.waitforpageload();
		ndtvwp.deselectAll();
		
		ndtvwp.searchAndSelectACity(cityname);
		double c=ndtvwp.getcelcius(cityname);
		double f=ndtvwp.getfahrenheit(cityname);
		
		HashMap<String,Double> datamap = new HashMap<String,Double>();
		datamap.put("celsius_ui", c);
		datamap.put("fahrenheit_ui",f);
		resultmap.put(cityname,datamap);
	}
	
	@Test(dataProvider="data" ,priority=2)
	public void GetAPITemerature(String cityname)
	{
		RestAssured.baseURI=CommonUtilities.getProperty("base_uri");
		HashMap<String,String> queryparams= new HashMap<String,String>();
		queryparams.put("q", cityname);
		queryparams.put("appid", CommonUtilities.getProperty("appid"));
		GetTemerature getTemperature= given().queryParams(queryparams)
		.when().get(CommonUtilities.getProperty("get_temp_path"))
		.then().statusCode(200).extract().as(GetTemerature.class);
		
		double cel=CommonUtilities.convertKelvin(getTemperature.getMain().getTemp(), 'C');
		double fah=CommonUtilities.convertKelvin(getTemperature.getMain().getTemp(), 'F');
		resultmap.get(cityname).put("celsius_api", cel);
		resultmap.get(cityname).put("fahrenheit_api", fah);
	}
	
	@Test(priority=3)
	public void comparator()
	{
		for(Map.Entry<String,HashMap<String,Double>> city:resultmap.entrySet())
		{
			
			System.out.println("City: "+ city.getKey()+ " Celsius from UI: "+ city.getValue().get("celsius_ui")+" Celsius from API: "+city.getValue().get("celsius_api"));
			System.out.println("City: "+ city.getKey()+ " Fahrenheit from UI: "+ city.getValue().get("fahrenheit_ui")+" Fahrenheit from API: "+city.getValue().get("fahrenheit_api"));

		}
		
	}
	
	
	@DataProvider(name="data")
	public Object[][] getData()
	{
		return new Object[][]{
			{"New Delhi"},
			{"Mumbai"},
			{"Kolkata"},
			{"Chennai"},
			};
		
	}
}