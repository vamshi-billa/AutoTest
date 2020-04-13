package com.vam.auto;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByLinkText;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.pagefactory.ByAll;

import junit.framework.Assert;

public class MovieSearchTest {

	
	public void movieSearchTest() {

		try {
			Scanner sc = new Scanner(new File("F:\\ECLIPSE WORKSPACE\\AutoProject\\src\\main\\resources\\movies.txt"));
			
			System.setProperty("webdriver.chrome.driver", "F:\\SOFTWARES\\chromedriver.exe");
			// Browser Launch
			WebDriver driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			
			while (sc.hasNext()) {
				String movieName = sc.nextLine();
				
				// Open URL  
				driver.get("https://www.google.com/");

				// Search for a movie
				WebElement search = driver.findElement(By.xpath("//*[@id=\"tsf\"]/div[2]/div[1]/div[1]/div/div[2]/input"));
				search.sendKeys(movieName);
				search.sendKeys(Keys.ENTER);

				// Get wikipedia link
				try {
					 WebElement wikiURLObj = driver.findElement(By.xpath("//a[contains(@href,'wikipedia')]"));

					// Navigate to wikipedia link
					 String wikiurl = wikiURLObj.getAttribute("href");
					driver.navigate().to(wikiurl);
					System.out.println("Movie name is: " + movieName);
					
					// Get director name								 //*[@id=\"mw-content-text\"]/div/table[1]/tbody/tr[3]/td/a
					WebElement wikiDirObj = driver.findElement(By.xpath("//*[@id=\"mw-content-text\"]/div/table[1]/tbody/tr[3]/th/following-sibling::td/child::a"));   
					String diWikiName = wikiDirObj.getText();
					System.out.println("Wiki director is: " + diWikiName);

					// Get IMDB link
					WebElement imdbURLObj = driver.findElement(By.xpath("//a[contains(@href,'imdb')]/i/parent::a"));
					// navigate to IMDB
					String imdburl = imdbURLObj.getAttribute("href");
					driver.navigate().to(imdburl);

					// Get IMDB director name							//*[@id=\"title-overview-widget\"]//h4[contains(text(),'Director:')]//following-sibling::a
					WebElement imDirObj = driver.findElement(By.xpath("//*[@id=\"title-overview-widget\"]//h4[starts-with(text(),'Director')]/following-sibling::a"));
					String dimName = imDirObj.getText();
					System.out.println("IMDB Director is : " + dimName);

					// Assert wiki director and IMDB director name
					if(diWikiName.equals(dimName)) {
						System.out.println("Match success");
					} else {
						System.out.println("Match failed");
					}
					
				} catch (NoSuchElementException e) {
					System.out.println(movieName + " is not a movie name\n" + e.getMessage());
				}
			}
			driver.quit();
			System.out.println("LIST COMPLETED..!");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
