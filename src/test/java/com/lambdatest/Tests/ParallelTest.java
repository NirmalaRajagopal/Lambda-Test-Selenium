package com.lambdatest.Tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.AssertJUnit;
import java.lang.reflect.Method;
import java.net.URL;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.interactions.Actions;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.Keys;
import java.util.Set;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.interactions.*;
import java.net.MalformedURLException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;



public class ParallelTest {

	// Lambdatest Credentails can be found here at https://www.lambdatest.com/capabilities-generator
	String username = System.getenv("LT_USERNAME") == null ? "YOUR LT_USERNAME" : System.getenv("LT_USERNAME"); 
	String accessKey = System.getenv("LT_ACCESS_KEY") == null ? "YOUR LT_ACCESS_KEY" : System.getenv("LT_ACCESS_KEY");

	public static WebDriver driver;

	@BeforeTest(alwaysRun = true)
	@Parameters(value = { "browser", "version", "platform" })
	protected void setUp(String browser, String version, String platform) throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();

		// set desired capabilities to launch appropriate browser on Lambdatest
		capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
		capabilities.setCapability(CapabilityType.VERSION, version);
		capabilities.setCapability(CapabilityType.PLATFORM, platform);
		capabilities.setCapability("build", "TestNG Parallel");
		capabilities.setCapability("name", "TestNG Parallel");
		capabilities.setCapability("network", true);
		capabilities.setCapability("video", true);
		capabilities.setCapability("console", true);
		capabilities.setCapability("visual", true);


		System.out.println("capabilities" + capabilities);

		// Launch remote browser and set it as the current thread
		String gridURL = "https://" + username + ":" + accessKey + "@hub.lambdatest.com/wd/hub";
		try {
			driver = new RemoteWebDriver(new URL(gridURL), capabilities);
		} catch (Exception e) {
			System.out.println("driver error");
			System.out.println(e.getMessage());
		}

	}

	public static boolean waitForElementVisible(WebElement element) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.visibilityOf(element));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean SelectByText(WebElement ele, String objName) {
		System.out.println(objName);
		try {
				Select select = new Select(ele);
				select.selectByVisibleText(objName);
				return true;
		} 
		catch(Exception e){
			return false;
		}
	}

	private static boolean downloadImage(URL url, String downloadPath) throws IOException
	{
		InputStream in = new BufferedInputStream(url.openStream());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int n = 0;
		while (-1!=(n=in.read(buf)))
		{
		out.write(buf, 0, n);
		}
		out.close();
		in.close();
		byte[] response = out.toByteArray();

		FileOutputStream fos = new FileOutputStream(downloadPath);
		fos.write(response);
		fos.close();

		return true;
	}


	/*@Test
	public void test() throws Exception {

		try {
			// Launch the app
			driver.get("https://lambdatest.github.io/sample-todo-app/");

			// Click on First Item
			driver.findElement(By.name("li1")).click();

			// Click on Second Item
			driver.findElement(By.name("li2")).click();

			// Add new item is list
			driver.findElement(By.id("sampletodotext")).clear();
			driver.findElement(By.id("sampletodotext")).sendKeys("Yey, Let's add it to list");
			driver.findElement(By.id("addbutton")).click();

			// Verify Added item
			String item = driver.findElement(By.xpath("/html/body/div/div/div/ul/li[6]/span")).getText();
			AssertJUnit.assertTrue(item.contains("Yey, Let's add it to list"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
	}*/

	@Test
	public void test01() throws Exception {
        SoftAssert sa = new SoftAssert();

		driver.get("https://www.lambdatest.com/automation-demos");
		driver.manage().window().maximize();
		WebElement loginpage = driver.findElement(By.xpath("//*[@id='newapply']/h2"));
		sa.assertEquals(loginpage.getText(), "Login to Selenium Playground");
		WebElement username = driver.findElement(By.xpath("//*[@id='username']"));
		waitForElementVisible(username);
		username.sendKeys("lambda");
		System.out.println ("Username entered");
		sa.assertEquals(username.getAttribute("value"), "lambda");
		WebElement password = driver.findElement(By.xpath("//*[@id='password']"));
		waitForElementVisible(password);
		password.sendKeys("lambda123");
		System.out.println ("password entered");
		sa.assertEquals(password.getAttribute("value"), "lambda123");
		WebElement login = driver.findElement(By.xpath("//*[@id='newapply']/div[3]/button"));
		waitForElementVisible(login);
		login.click();
		System.out.println ("login clicked");
		/*Alert alert = driver.switchTo().alert();
		System.out.println(alert.getText());
		sa.assertEquals(alert.getText(), "Thank You Successfully Login!!");*/
		try {
    		WebDriverWait wait = new WebDriverWait(driver, 2);
    		wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			Thread.sleep(300);
			alert.getText();
		}
		catch(Exception e){
			System.out.println ("In exception!!!");
			System.out.println (e.getMessage());
		}
		Thread.sleep(2000);
		sa.assertAll();
	}

	@Test
	public  void test02() throws Exception {
		SoftAssert sa = new SoftAssert();

		WebElement	EmailLogin = driver.findElement(By.xpath("//*[@id='developer-name']"));
		waitForElementVisible(EmailLogin);
		EmailLogin.sendKeys("nirmalarajagopal16@gmail.com");
		sa.assertEquals(EmailLogin.getAttribute("value"),"nirmalarajagopal16@gmail.com");
		System.out.println ("Email entered");
		WebElement populate = driver.findElement(By.id("populate"));
		waitForElementVisible(populate);
		populate.click();
		System.out.println ("populate clicked");
		/*Alert alert = driver.switchTo().alert();
		alert.accept();*/
		
		try {
    		WebDriverWait wait = new WebDriverWait(driver, 2);
    		wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			Thread.sleep(300);
			alert.accept();
		}
		catch(Exception e){
			System.out.println ("In exception!!!");
			System.out.println (e.getMessage());
		}
		sa.assertAll();
	}

	@Test
	public  void test03() throws Exception {
		SoftAssert sa = new SoftAssert();

        List<WebElement> radio_button = driver.findElements(By.name("os"));
		
		if(!radio_button.get(0).isSelected()){
			radio_button.get(0).click();
		}else{
			System.out.println ("It is already selected");
		}
		System.out.println ("everymonth clicked");

		List<WebElement> checkbox1 = driver.findElements(By.className("mr-10"));
		
		if(!checkbox1.get(0).isSelected()){
			checkbox1.get(0).click();
		}else{
			System.out.println ("It is already selected");
		}
		System.out.println ("customer clicked");

        if(!checkbox1.get(1).isSelected()){
			checkbox1.get(1).click();
		}else{
			System.out.println ("It is already selected");
		}
		System.out.println ("Discounts clicked");

		WebElement select = driver.findElement(By.id("preferred-payment"));
		SelectByText(select, "Wallets");
		System.out.println ("Wallets selected");

		WebElement checkbox2 = driver.findElement(By.xpath("//input[@id='tried-ecom']"));
		checkbox2.click();
		System.out.println ("checkbox2 clicked");

	}


@Test
	public  void test04() throws Exception {

		SoftAssert sa = new SoftAssert();
		WebElement from = driver.findElement(By.xpath("//*[@id='__next']/div[1]/section[2]/div/div/div[4]/div[2]/div/div/div[1]/div/div[12]"));
		WebElement to = driver.findElement(By.xpath("//*[@id='__next']/div[1]/section[2]/div/div/div[4]/div[2]/div/div/div[2]/div[10]"));
        Actions action = new Actions(driver);
		action.clickAndHold(from).moveToElement(to).release(to).build().perform();
		System.out.println ("cursor dragged");
		Thread.sleep(2000);
		sa.assertEquals(to.getText(),"9");
        System.out.println(to.getText());
		WebElement post = driver.findElement(By.xpath("//*[@id='comments']"));
		waitForElementVisible(post);
		post.sendKeys("Feedback");
		sa.assertEquals(post.getAttribute("value"), "Feedback");
		System.out.println (post.getAttribute("value"));
		sa.assertAll();
}

	@Test
	public  void test0() throws MalformedURLException, IOException {

		SoftAssert sa = new SoftAssert();
		driver.get("https://www.lambdatest.com/selenium-automation");
		driver.manage().window().maximize();

		WebElement jenkins = driver.findElement(By.xpath("//*[@id='__next']/div[1]/section[8]/div/div/div/div[1]/div[1]/a/img"));
		waitForElementVisible(jenkins);
		
		String jenkinsLogoSrc = jenkins.getAttribute("src");
		System.out.println(jenkinsLogoSrc);
		URL imageURL = new URL(jenkinsLogoSrc);

		//BufferedImage saveImage = ImageIO.read(imageURL);
		String dir = System.getProperty("user.dir");
		downloadPath = dir + "jenkins-logo-image.png";

		System.out.println("downloadPath:" + downloadPath);

		downloadImage(imageURL, downloadPath);
	}

	@Test
	public  void test05() throws MalformedURLException, IOException, InterruptedException
	{
		SoftAssert sa = new SoftAssert();	

		WebElement upload = driver.findElement(By.xpath("//*[@id='file']"));
		if(waitForElementVisible(upload)){
			upload.sendKeys(downloadPath);
			sa.assertEquals(driver.switchTo().alert().getText(), "your image upload sucessfully!!");
			driver.switchTo().alert().accept();
			WebElement submit = driver.findElement(By.id("submit-button"));
			submit.click();
		}
		System.out.println ("submit clicked");
		WebElement aftersubmit = driver.findElement(By.xpath("//*[@id='__next']/div[1]/section[2]/section/div/div/h1"));
         if(waitForElementVisible(aftersubmit)){
		 sa.assertEquals(aftersubmit.getAttribute("innerHTML"), "Thank you!");
		 }
		 WebElement afs = driver.findElement(By.xpath("//*[@id='__next']/div[1]/section[2]/section/div/div/p"));
         sa.assertEquals(afs.getAttribute("innerHTML"), "You have successfully submitted the form.");
		 sa.assertAll();
		
	}

	
	@AfterTest(alwaysRun = true)
	public void tearDown() throws Exception {
		driver.quit();
	}

}
