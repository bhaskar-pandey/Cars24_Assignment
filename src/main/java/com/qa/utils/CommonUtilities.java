package com.qa.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class CommonUtilities {
	static Date date;
	private static Properties properties;
	private final static String propertyFilePath= System.getProperty("user.dir")+"/src/main/resources/apiproperties.properties";
	static BufferedReader reader;
	
	public static int getTempValue(String temperature)
	{
		int temp=0;
		for(int i=0;i<temperature.length();i++)
		{
			if(Character.isDigit(temperature.charAt(i)))
			{
				temp=temp*10+Character.getNumericValue(temperature.charAt(i));
			}
		}
		return temp;
		
	}
	
	public static String getProperty(String prop_name)
	{
		 try {
		 reader = new BufferedReader(new FileReader(propertyFilePath));
		 properties = new Properties();
		 try {
		 properties.load(reader);
		 reader.close();
		 } catch (IOException e) {
		 e.printStackTrace();
		 }
		 } catch (FileNotFoundException e) {
		 e.printStackTrace();
		 throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
		 } 
		
		return properties.getProperty(prop_name);
		
	}
	
	public static double convertKelvin(double kelvin,char requestedmetric)
	{
		if(requestedmetric=='C')
		{
			return kelvin-273.16;
		}
		if(requestedmetric=='F')
		{
			return (((kelvin - 273) * 9/5) + 32);
		}
		return -1000.0;
	
	}

}
