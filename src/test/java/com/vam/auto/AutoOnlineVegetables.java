package com.vam.auto;

import java.io.IOException;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AutoOnlineVegetables {
	
	//Global Variable Declaration:
	static AutoOnlineVegetables ov = new AutoOnlineVegetables();
	
	ChromeOptions options;
	WebDriver driver;
	WebElement navigator;
	JavascriptExecutor js;
	Logger logger = LogManager.getLogger(AutoOnlineVegetables.class);
	//Save the current window handle:
	String mainWindow;
	
	public void configureDriver() throws IOException {
		//Kill Previous Chrome Processes and Set System Property:
		Runtime.getRuntime().exec("Taskkill /F /IM chromedriver_80.exe /T");
		System.setProperty("webdriver.chrome.driver", "F:\\SOFTWARES\\chromedriver_80.exe");
		
		//Set Chrome Options:
		options = new ChromeOptions();
		options.addArguments("start-maximized");
		options.addArguments("disable-infobars");
		options.addArguments("--disable-extensions");
		options.addArguments("--disable-notifications");
		options.setExperimentalOption("useAutomationExtension", false);
		
		this.driver = new ChromeDriver(options);
		this.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		this.driver.get("file:///C:/Users/91871/Desktop/WebDev.html");
		
		this.mainWindow = driver.getWindowHandle();
	}
	
	@When("I click on Online Vegetables text")
	public void i_click_on_Online_Vegetables_text() throws IOException {
		ov.configureDriver();
		
		//Clicking on the hyperlink Online Vegetables:
	    navigator = ov.driver.findElement(By.cssSelector("a[target='myframe']"));
	    navigator.click();
	    
	    //Switching to iframe:
	    WebElement iframeSwitch = ov.driver.findElement(By.cssSelector("iframe[name='myframe']"));
	    ov.driver.switchTo().frame(iframeSwitch);
	    logger.info("Navigation switched to iframe.");
	}


	@Then("verify Order button is enabled in the iframe")
	public void verify_Order_button_is_enabled_in_the_iframe() throws Exception {
		js = (JavascriptExecutor) ov.driver;
		
	    //Scrolling to the order button:
	    WebElement orderButton = ov.driver.findElement(By.cssSelector("input[type='submit'][value='order']"));
	    js.executeScript("arguments[0].scrollIntoView(true);", orderButton);
	    
	    //Check if the order button is enabled:
	    if(orderButton.isEnabled()) {
	    	logger.info("Order button is enabled.");
	    } else {
	    	logger.error("Order button is not enabled.");
	    	throw new Exception("Order button is not enabled.");
	    }
	    
	    //Switching back to main page from iframe:
	    ov.driver.switchTo().defaultContent();
	}

	@When("I click on vegetable link in the Vegetables Bill table where vegetable name is {string}")
	public void i_click_on_vegetable_link_in_the_Vegetables_Bill_table_where_vegetable_name_is(String vegetableName) throws IOException {
		
		//Navigating to the table element "Tomatoes":
		navigator = ov.driver.findElement(By.xpath("//table/tbody/descendant::td[contains(text(),'Tomatoes')]"));
		navigator.click();
	}

	@Then("I should find {string} text element displayed in a new window")
	public void i_should_find_text_element_displayed_in_a_new_window(String text) throws Exception {
		
		//Find the window:
		for (String windowHandle : ov.driver.getWindowHandles()) {
			if(ov.driver.switchTo().window(windowHandle).getTitle().equals("TOMATOES Page")) {
				break;
			} else {
				ov.driver.switchTo().window(ov.mainWindow);
			}
		}
		navigator = ov.driver.findElement(By.cssSelector("div[class='popup']"));
		String actualText = navigator.getText();
		
		//Check if the text in the new window is matched:
		if(actualText.equals(text)) {
			logger.info("New window verified.");
		} else {
			logger.error("New window failed.");
			throw new Exception("Switching to new window failed");
		}
	}

	@When("I click on Click to view the Vegetable name! element")
	public void i_click_on_Click_to_view_the_Vegetable_name_element() {
		
		//Click on the Click to view the Vegetable name! text:
		navigator = ov.driver.findElement(By.cssSelector("div[class='popup']"));
		navigator.click();
	}

	@Then("I should verify that {string} is displayed in a popup")
	public void i_should_verify_that_is_displayed_in_a_popup(String expPopupText) {
		
		//Navigate to the popup:
		navigator = ov.driver.findElement(By.xpath("//div[@class='popup']/span[@id='myPopup']"));
		
		//If popup is displayed:
		if(navigator.isDisplayed()) {
			String actualPopupText = navigator.getText();
			logger.info("Popup is displayed.");
			
			//If popup texts are same:
			if(actualPopupText.equals(expPopupText)) {
				logger.info("Popup text Tomatoes is verified.");
			} else {
				logger.info("Popup texts are different.");
			}
		} else {
			logger.info("Popup is not displayed.");
		}
	}

	@When("I close the vegetable window")
	public void i_close_the_vegetable_window() {

		//Close the current window:
		ov.driver.close();
		ov.driver.switchTo().window(ov.mainWindow);
	}

	@When("I click on the {string} vegetable image")
	public void i_click_on_the_vegetable_image(String string) {
		
		//Navigate to the vegetable image:
		navigator = ov.driver.findElement(By.xpath("//table/tbody/descendant::td[contains(text(),'Tomatoes')]/following-sibling::td/img"));
		navigator.click();
	}

	@Then("I should find the alert box with message {string}")
	public void i_should_find_the_alert_box_with_message(String expAlertText) {
		
		//Extract text from alert box:
		String actualAlertText = ov.driver.switchTo().alert().getText();
		
		//Compare the text:
		if(actualAlertText.equals(expAlertText)) {
			logger.info("Alert box came up and " + expAlertText + "text is matched.");
		} else {
			logger.info("Either alert box didn't come up or text isn't matched");
		}
	}

	@Then("I accept alert")
	public void i_accept_alert() {

		//Accept the alert by clicking Ok:
		ov.driver.switchTo().alert().accept();
	}

	@When("I find the date input field")
	public void i_find_the_date_input_field() {
		
		//Navigate to the date input field:
	     navigator = ov.driver.findElement(By.cssSelector("input[type='date']"));
	}

	@Then("I input yesterday's date in deliver on control")
	public void i_input_yesterday_s_date_in_deliver_on_control() {
	    
		//Get today's date:
		LocalDate todaysDate = java.time.LocalDate.now();
		
		//Convert to string:
		String stringDate = todaysDate.toString();
		
		//Split to day, month, year:
		String[] resetDate = stringDate.split("-");
		String year = resetDate[0];
		String month = resetDate[1];
		int day = Integer.parseInt(resetDate[2]);
		 
		//Calculate previous day date:
		int yesterdaysDay = day-1;
		String actualDay = Integer.toString(yesterdaysDay);
		
		//
		navigator = ov.driver.findElement(By.cssSelector("input[type='date']"));
		navigator.sendKeys(actualDay + month + year);
		ov.driver.quit();
	}
	
	public static void main(String[] args) throws IOException {
		ov.i_click_on_Online_Vegetables_text();
	}
}

