package com.vam.auto;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ShareTest {
	// Global variable declaration.
	private static Logger logger = LogManager.getLogger(ShareTest.class);
	static Scanner sc = new Scanner(System.in);
	List<Company> comps = new ArrayList<>();

	static ShareTest s = new ShareTest();

	static ChromeOptions options;
	static WebDriver driver;

	FileInputStream fis;
	FileOutputStream fos;

	Workbook workbook;
	XSSFSheet sheet;

	Row row;
	Cell cell;

	String path;
	String sheetName;

	public void configureDriver() throws IOException {
		// Kill all old ChromeDriver processes
		Runtime.getRuntime().exec("taskkill /F /IM chromedriver_80.exe /T");

		// Setting the driver and launching the browser.
		System.setProperty("webdriver.chrome.driver", "F:\\SOFTWARES\\chromedriver_80.exe");
		options = new ChromeOptions();

		options.addArguments("start-maximized");
		options.addArguments("disable-infobars");
		options.addArguments("--disable-extensions");
		options.addArguments("--disable-notifications");
		options.setExperimentalOption("useAutomationExtension", false);

		driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	public void searchMoneyControl() throws Exception, InterruptedException {
		driver.get("https://www.moneycontrol.com/");
		// verify navigation to Money Control home page
		try {
			driver.findElement(By.xpath("//*[@id=\"mc_mainWrapper\"]/header/div[4]/div/div[2]/ul/li[1]/a"));
		} catch (NoSuchElementException e) {
			logger.error("Failed navigating to money control link");
			throw e;
		}
		for (int i = 1; i <= 5; i++) {
			Company c = new Company();
			WebElement globalMarkets = driver.findElement(By.xpath("//h3[@class='tplhead MB5']/a"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", globalMarkets);

			// Company's element.
			WebElement mcNav = driver.findElement(By.xpath("//*[@id=\"maNSE\"]/table/tbody/tr[" + i + "]/td[1]/a"));
			c.setName(mcNav.getText());
			mcNav.click();

			Thread.sleep(5000);

			// verify page
			String title = driver.getTitle();
			if (!title.toLowerCase().contains(c.getName().toLowerCase())) {
				throw new Exception("Navigation to company: " + c.getName() + " page failed");
			}

			// Scroll to and get Company share market details.
			WebElement historicalPrices = driver.findElement(By.xpath("//h3[contains(text(),'Historical Prices')]"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", historicalPrices);

			mcNav = driver.findElement(
					By.xpath("//div[@class = \"value_txtfl\"][contains(text(), 'Open Price')]/following-sibling::div"));
			c.setMcOpenPrice(mcNav.getText());

			mcNav = driver.findElement(
					By.xpath("//div[@class = \"value_txtfl\"][contains(text(), 'High Price')]/following-sibling::div"));
			c.setMcHighPrice(mcNav.getText());

			mcNav = driver.findElement(
					By.xpath("//div[@class = \"value_txtfl\"][contains(text(), 'Low Price')]/following-sibling::div"));
			c.setMcLowPrice(mcNav.getText());

			mcNav = driver.findElement(By
					.xpath("//div[@class = \"value_txtfl\"][contains(text(), 'Prev. Close')]/following-sibling::div"));
			c.setMcPrevClose(mcNav.getText());

			// add company to list.
			comps.add(c);

			// Navigating back to home page from current Company's page.
			WebElement eleFinder;
			eleFinder = driver.findElement(By.cssSelector("a[title='Home'][href*='moneycontrol'][class=' ']"));
			eleFinder.click();
			Thread.sleep(5000);
		}
	}

	public void printMcResults() {
		logger.info("Share market results by Money Control:");
		for (Company c : comps) {
			logger.info(c.getName() + ":");
			logger.info("Open Price:  " + c.getMcOpenPrice() + "\n" + "High Price:  " + c.getMcHighPrice() + "\n"
					+ "Low Price:   " + c.getMcLowPrice() + "\n" + "Prev. Close: " + c.getMcPrevClose());
			logger.info("");
		}
	}

	public void searchGoogle() {
		// Searching the share price for each company in Google.
		for (Company c : comps) {
			driver.get("https://www.google.com/");
			WebElement search = driver.findElement(By.cssSelector("input[title='Search']"));
			search.sendKeys(c.getName() + " share price");
			search.sendKeys(Keys.ENTER);

			WebElement gooNav = driver.findElement(By.xpath("//div[1]/table/tbody/tr[1]/td[2]"));
			c.setGoogleOpenPrice(gooNav.getText());
			gooNav = driver.findElement(By.xpath("//div[1]/table/tbody/tr[2]/td[2]"));
			c.setGoogleHighPrice(gooNav.getText());
			gooNav = driver.findElement(By.xpath("//div[1]/table/tbody/tr[3]/td[2]"));
			c.setGoogleLowPrice(gooNav.getText());
			gooNav = driver.findElement(By.xpath("//div[2]/table/tbody/tr[2]/td[2]"));
			c.setGooglePrevClose(gooNav.getText());
		}
	}

	public void printGooResults() {
		logger.info("Share market results by Google:");
		for (Company c : comps) {
			logger.info(c.getName() + ": ");
			logger.info("Open Price:  " + c.getGoogleOpenPrice() + "\n" + "High Price:  " + c.getGoogleHighPrice()
					+ "\n" + "Low Price:   " + c.getGoogleLowPrice() + "\n" + "Prev. Close: " + c.getGooglePrevClose());
			logger.info("");
		}
	}

	public void accessExcelFile(String path, String sheetName) throws IOException {
		// Accessing an Excel file. Retrieving the sheet
		fis = new FileInputStream(path);
		workbook = new XSSFWorkbook(fis);
		sheet = (XSSFSheet) workbook.getSheet(sheetName);
		System.out.println(sheet.getLastRowNum());
	}

	public void fillHeadingsToCells() {
		// Entering the column headings into the sheet.
		row = null;
		cell = null;
		String[] headings = { "COMPANY", "mcOpenPrice", "mcHighPrice", "mcLowPrice", "mcPrev.Close", "gOpenPrice",
				"gHighPrice", "gLowPrice", "gPrev.Close", "dOpenPrice", "dHighPrice", "dLowPrice", "dPrev.Close" };

		for (int col = 0; col < headings.length; col++) {
			row = sheet.getRow(0);
			cell = row.createCell(col);
			cell.setCellType(CellType.STRING);
			cell.setCellValue(headings[col]);
		}
		logger.info("Headings have been entered");
	}

	public void insertDataToCells() {
		// Insert data
		int rNum = 1;
		int cNum = 0;
		row = null;
		cell = null;
		for (Company c : comps) {
			cNum = 0;

			row = sheet.createRow(rNum++);
			cell = getCell(row, cNum++);
			cell.setCellValue(c.getName());

			cell = getCell(row, cNum++);
			cell.setCellValue(c.getMcOpenPrice());

			cell = getCell(row, cNum++);
			cell.setCellValue(c.getMcHighPrice());

			cell = getCell(row, cNum++);
			cell.setCellValue(c.getMcLowPrice());

			cell = getCell(row, cNum++);
			cell.setCellValue(c.getMcPrevClose());

			cell = getCell(row, cNum++);
			cell.setCellValue(c.getGoogleOpenPrice());

			cell = getCell(row, cNum++);
			cell.setCellValue(c.getGoogleHighPrice());

			cell = getCell(row, cNum++);
			cell.setCellValue(c.getGoogleLowPrice());

			cell = getCell(row, cNum++);
			cell.setCellValue(c.getGooglePrevClose());
			logger.info(c.getName() + " details have been entered");
		}
		logger.info("Data is entered");
	}

	private Cell getCell(Row row, int colNum) {
		Cell cell = row.createCell(colNum);
		cell.setCellType(CellType.STRING);
		return cell;
	}

	public void exportToExcel(String path) throws IOException {
		// Exporting the file.
		fos = new FileOutputStream(path);
		workbook.write(fos);

		// closing the IOStreams and workbook.
		fos.close();
		workbook.close();
		fis.close();

	}

	@When("I get price values for top {int} most active stocks from Money Control")
	public void i_get_price_values_for_top_most_active_stocks_from_Money_Control(Integer int1)
			throws InterruptedException, Exception {
		s.configureDriver();
		s.searchMoneyControl();
		s.printMcResults();
	}

	@When("I get price values for those companies from Google")
	public void i_get_price_values_for_those_companies_from_Google() {
		s.searchGoogle();
		s.printGooResults();
	}
	
	@Then("print the values in {string} in {string} sheet along with difference in values")
	public void print_the_values_in_in_sheet_along_with_difference_in_values(String filePath, String sheetName) throws IOException {
		s.accessExcelFile(filePath, sheetName);
		s.fillHeadingsToCells();
		s.insertDataToCells();
		s.exportToExcel(filePath);
	}

}
