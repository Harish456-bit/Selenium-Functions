package com.tsui_base;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class SeleniumBase implements SeleniumAPI {
	Duration maxWaitTime = Duration.ofSeconds(15);

	protected RemoteWebDriver driver = null;
	WebDriverWait wait = null;

	@Override
	public void setUp(String url) {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(url);
		wait = new WebDriverWait(driver, maxWaitTime);

	}

	@Override
	public void setUp(browser browserName, String url) {

		switch (browserName) {
		case CHROME:
			driver = new ChromeDriver();

			break;
		case FIREFOX:
			driver = new FirefoxDriver();

		case EDGE:
			driver = new EdgeDriver();
		default:
			System.err.println("Browser is not defined");
			break;
		}

	}

	@Override
	public void close() {
		driver.close();

	}

	@Override
	public void quit() {
		driver.quit();

	}

	@Override
	public WebElement element(Locators type, String value) {
		switch (type) {
		case Id:
			return driver.findElement(By.id(value));
		case Tagname:
			return driver.findElement(By.tagName(value));
		case ClassName:
			return driver.findElement(By.className(value));
		case Xpath:
			return driver.findElement(By.xpath(value));
		case Linktext:
			return driver.findElement(By.xpath(value));
		case Css:
			return driver.findElement(By.cssSelector(value));
		case PartialLinkText:
			return driver.findElement(By.partialLinkText(value));
		case Name:
			return driver.findElement(By.name(value));

		default:
			break;
		}

		return null;
	}

	@Override
	public void switchToWindow(int i) {
		Set<String> windowHandles = driver.getWindowHandles();
		ArrayList<String> list = new ArrayList<>(windowHandles);
		driver.switchTo().window(list.get(i));

	}

	@Override
	public void selectValue(WebElement ele, String value) {
		WebElement element = isElementVisible(ele);
		new Select(element).selectByValue(value);
	}

	@Override
	public void selectText(WebElement ele, String text) {
		WebElement element = isElementVisible(ele);
		new Select(element).selectByVisibleText(text);

	}

	@Override
	public void selectIndex(WebElement ele, int position) {
		WebElement element = isElementVisible(ele);
		new Select(element).selectByIndex(position);

	}

	@Override
	public void click(WebElement ele) {
		WebElement element = wait.withMessage("Element is not Clickble")
				.until(ExpectedConditions.elementToBeClickable(ele));
		element.click();

	}

	@Override
	public void type(WebElement ele, String testdata) {
		WebElement element = isElementVisible(ele);
		element.sendKeys(testdata);

	}

	private WebElement isElementVisible(WebElement ele) {
		WebElement element = wait.withMessage("Element is not visible").until(ExpectedConditions.visibilityOf(ele));
		element.clear();
		return element;
	}

	public void typeandEnter(WebElement ele, String testdata, Keys keys) {
		WebElement element = isElementVisible(ele);
		element.sendKeys(testdata, keys);

	}

	@Override
	public void appendText(WebElement ele, String testdata) {
		WebElement element = isElementVisible(ele);
		element.sendKeys(testdata);

	}

	@Override
	public String getTitle() {

		return driver.getTitle();
	}

	@Override
	public String getUrl() {

		return driver.getCurrentUrl();
	}

	@Override
	public boolean isDisplayed(WebElement ele) {

		return ele.isDisplayed();
	}

	public static String[][] getExcelData(String file) {
	    String filename = file;
	    String filepath = "./data/" + filename + ".xlsx";
	    XSSFWorkbook Wbook = null;
	    try {
	        Wbook = new XSSFWorkbook(filepath);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    XSSFSheet sheet = Wbook.getSheetAt(0);
	    int lastrow = sheet.getLastRowNum() + 1; // Add 1 to include the header row
	    short lastcell = sheet.getRow(0).getLastCellNum();
	    String[][] data = new String[lastrow][lastcell];

	    for (int i = 0; i < lastrow; i++) {
	        XSSFRow row = sheet.getRow(i);
	        for (int j = 0; j < lastcell; j++) {
	            XSSFCell cell = row.getCell(j);
	            DataFormatter dft = new DataFormatter();
	            String value = dft.formatCellValue(cell);
	            data[i][j] = value;
	        }
	    }

	    try {
	        Wbook.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return data;
	}


	public void bottomofPage() {
		JavascriptExecutor js = driver;
		js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
	}
	public void scrollToelement(String xpath) {
		WebElement scroll = driver.findElement(By.xpath(xpath));
		JavascriptExecutor js = driver;
		js.executeScript("arguments[0].scrollIntoView(true);", scroll);
	}
	public void byPixel() {
		JavascriptExecutor js = driver;
		js.executeScript("window.scrollBy(0,1000)");
	}
	public void table(String tablename , String tableheader) {
		WebElement table = driver.findElement(By.tagName(tablename));
		List<WebElement> title = table.findElements(By.tagName(tableheader));
		for (WebElement titles : title) {
			String text = titles.getText();
			System.out.println(text);
		}
	}
	public void Screenshot(String Xpath, String filepath) {
		WebElement ele = driver.findElement(By.xpath(Xpath));
		File first = ele.getScreenshotAs(OutputType.FILE);
		File dest = new File(filepath);
		try {
			FileHandler.copy(first, dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String captureScreenshot(String filepath) {
	    // Capture a screenshot and save it to the specified filepath
	    File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	    File destFile = new File(filepath);

	    try {
	        FileUtils.copyFile(screenshotFile, destFile);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return filepath;
	}
	public void rightclick(String name) {
		Actions builder = new Actions(driver);
		WebElement element = driver.findElement(By.xpath(name));
		builder.contextClick(element).perform();
	}
	public void rightclickbyid(String name) {
		Actions builder = new Actions(driver);
		WebElement element = driver.findElement(By.id(name));
		builder.contextClick(element).perform();
	}
	public void sleep(long num) {
		try {
			Thread.sleep(num);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	  public static void setBrowserZoom(RemoteWebDriver driver, double zoomLevel) {
	        if (driver instanceof JavascriptExecutor) {
	            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
	            String zoomScript = "document.body.style.zoom = '" + zoomLevel + "';";
	            jsExecutor.executeScript(zoomScript);
	        }
	    }
	    public static void horizontalScrollToElement(WebDriver driver, WebElement element) {
	        if (driver instanceof JavascriptExecutor) {
	            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
	            jsExecutor.executeScript("arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", element);
	        }
	    }
	    public void settingswindow() {
	    	WebElement window1 = driver.findElement(By.xpath("//select[@id='dr'][1]"));
			Select fw = new Select(window1);
			fw.selectByIndex(3);
			
			
			WebElement window2 = driver.findElement(By.xpath("(//select[@id='dr'])[2]"));
			Select sw =  new Select(window2);
			sw.selectByIndex(2);
			
			WebElement window3 = driver.findElement(By.xpath("(//select[@id='dr'])[3]"));
			Select Tw =  new Select(window3);
			Tw.selectByIndex(1);
			
			
			WebElement window4 = driver.findElement(By.xpath("(//select[@id='dr'])[4]"));
			Select Fw = new Select(window4);
			Fw.selectByIndex(0);
	    }
	    public void settingswindow1() {
	    	WebElement window1 = driver.findElement(By.xpath("//select[@id='dr'][1]"));
			Select fw = new Select(window1);
			fw.selectByIndex(0);
			
			
			WebElement window2 = driver.findElement(By.xpath("(//select[@id='dr'])[2]"));
			Select sw =  new Select(window2);
			sw.selectByIndex(1);
			
			WebElement window3 = driver.findElement(By.xpath("(//select[@id='dr'])[3]"));
			Select Tw =  new Select(window3);
			Tw.selectByIndex(2);
			
			
			WebElement window4 = driver.findElement(By.xpath("(//select[@id='dr'])[4]"));
			Select Fw = new Select(window4);
			Fw.selectByIndex(3);
	    }
	    public void dragandrop(String Xpath,int num) {
	        WebElement ele = driver.findElement(By.xpath(Xpath));
	        Actions builder = new Actions(driver);
	        for (int i = 0; i < num; i++) {
	            int xOffset = -30;
	            int yOffset = 0;
	            builder.dragAndDropBy(ele, xOffset + -30, yOffset + 0).perform();
	        }
}
}