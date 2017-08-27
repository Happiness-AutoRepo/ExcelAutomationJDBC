package simpleddt;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.CellStyle;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import utilities.ExcelUtils;

public class AmazonDataSearch {

	static WebDriver driver;
	WebElement results;
	WebElement search; 

	@BeforeClass
	public static void setUp() {
		System.setProperty("webdriver.chrome.driver",
				"C:/Users/Marat Metoff/Documents/Libraries/drivers/chromedriver.exe");

		driver = new HtmlUnitDriver();
		//driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://www.amazon.com");
	}

	@Test
	public void searchTest() {
		ExcelUtils.openExcelFile("./src/test/resources/TestData/Book1.xlsx", "sheet1");
		int rowsCount = ExcelUtils.getUsedRowsCount();
		for (int rowNum = 1; rowNum < rowsCount; rowNum++) {
			
			if(ExcelUtils.getCellData(rowNum, 0).equals("N")) {
				ExcelUtils.setCellData("Skipped", rowNum, 3);
				continue;
			}
			
			String searchItem = ExcelUtils.getCellData(rowNum, 1);

			searchFor(searchItem);
			String resultText = getSearchResult();
			int resultCount = cleanupSearchResultsCount(resultText);
			ExcelUtils.setCellData(String.valueOf(resultCount), rowNum, 2);
			System.out.println("Search count is : " + resultCount);

			if (resultCount > 0) {
				System.out.println("Search Test pass");
				ExcelUtils.setCellData("PASS", rowNum, 3);
			} else {
				System.out.println("Search Test fail");
				ExcelUtils.setCellData("FAIL", rowNum, 3);
			}
			String now = LocalDateTime.now().toString();
			ExcelUtils.setCellData(now, rowNum, 4);
		}
	}

	public String getSearchResult() {

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {

			e1.printStackTrace();
		}

		try {
			results = driver.findElement(By.id("s-result-count"));
		} catch (Exception e) {
			return "0 result";
		}
		return results.getText();
	}

	public void searchFor(String item) {
		search = driver.findElement(By.id("twotabsearchtextbox"));
		search.clear();
		search.sendKeys(item + Keys.ENTER);
	}

	public int cleanupSearchResultsCount(String searchResult) {
		int resultsCount;
		String longOne = searchResult;
		String[] resultArr = searchResult.split(" ");
		if (longOne.contains("of")) {
			resultsCount = Integer.parseInt(resultArr[2].replace(",", ""));
		} else {
			resultsCount = Integer.parseInt(resultArr[0]);
		}
		return resultsCount;
	}

	 @AfterClass
	 public static void tearDown() {
	 driver.quit();
	 }
}
