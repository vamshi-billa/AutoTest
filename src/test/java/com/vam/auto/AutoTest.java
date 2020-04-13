package com.vam.auto;

import static org.junit.Assert.assertNotEquals;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import junit.framework.*;
import junit.framework.Assert;

import org.junit.*;
import org.junit.Test;

public class AutoTest {
	
	public void googleSearchTest() {
		System.setProperty("webdriver.chrome.driver", "F:\\SOFTWARES\\chromedriver.exe");
		// Instantiate a ChromeDriver class.
		WebDriver driver = new ChromeDriver();
		// Maximize the browser
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

		// Launches URL
		driver.get("http://www.google.com");

		// Enter search
		WebElement searchBox = driver.findElement(By.xpath("//*[@id=\"tsf\"]/div[2]/div[1]/div[1]/div/div[2]/input"));
		searchBox.sendKeys("Apple");
		searchBox.sendKeys(Keys.ENTER);

		// Click search
//		WebElement searchButton = driver
//				.findElement(By.xpath("//*[@id=\"tsf\"]/div[2]/div[1]/div[2]/div[2]/div[2]/center/input[1]"));
//		searchButton.sendKeys(Keys.ENTER); 

		WebElement searchBox2 = driver.findElement(By.name("q"));
		String text = searchBox2.getAttribute("value");
		driver.quit();

		System.out.print("Text is " + text);

		assertNotEquals("Apple", text);
	}
}
