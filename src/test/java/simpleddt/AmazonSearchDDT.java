package simpleddt;


import java.io.IOException;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import utilities.ExcelUtils;

public class AmazonSearchDDT {
	
	static WebDriver driver;
	WebElement search;
	WebElement results;
	
	@BeforeClass
	public static void setUp() {
	
		System.setProperty("webdriver.chrome.driver", "C:/Users/Marat Metoff/Documents/Libraries/drivers/chromedriver.exe");
		driver = new HtmlUnitDriver();
		driver.get("https://www.amazon.com");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
		
	@Test
	public void test() throws InterruptedException, IOException {
		
		
		
		
		String ExcelFilePath = "./src/test/resources/TestData/Book1.xlsx";
		ExcelUtils.openExcelFile(ExcelFilePath, "Sheet1");
		int rowsCount = ExcelUtils.getUsedRowsCount();
		
		for (int i = 1; i < rowsCount + 3; i++) {
			
			
			String searchItem = ExcelUtils.getCellData(i, 1);
			Thread.sleep(2000);
			search = driver.findElement(By.id("twotabsearchtextbox"));
			search.clear();
			search.sendKeys(searchItem + Keys.ENTER);
			results = driver.findElement(By.id("s-result-count"));
			String searchResults = results.getText();
			
			String[] arrResult = searchResults.split(" ");
			int resultsCount;
			if(searchResults.contains(" of ")) {
				resultsCount = Integer.parseInt(arrResult[2].replace(",", ""));
			}else {
				resultsCount = Integer.parseInt(arrResult[0]);
			}
			
			System.out.println(resultsCount);
			
			
			if(resultsCount > 0) {
				ExcelUtils.setCellData("Pass", i, 3);
			} else {
				ExcelUtils.setCellData("Fail", i, 3);
			}
		
			ExcelUtils.setCellData( new Integer(resultsCount).toString(), i, 2);
			ExcelUtils.setCellData(LocalTime.now().toString(), i, 4);
		}
		
		
		//input.close();
	    //output.close();
	    //workbook.close();
		
		Thread.sleep(5000);
		driver.quit();
	}
	
	
	

}
