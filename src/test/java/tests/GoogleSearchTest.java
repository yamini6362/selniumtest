package tests;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;

public class GoogleSearchTest {
    WebDriver driver;
    ExtentReports extent;
    ExtentTest test;

    @Parameters({"browser"})
    @BeforeClass
    public void setUp(String browser) throws MalformedURLException {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("test-output/ExtentReports/ExtentReport_" + browser + ".html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(browser);
        driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
    }

    @Test
    public void googleTest() {
        test = extent.createTest("Google Search Test", "Verify Google title");
        driver.get("https://www.google.com");
        String title = driver.getTitle();
        test.info("Page title is: " + title);
        Assert.assertTrue(title.contains("Google"));
        test.pass("Title verified successfully");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        extent.flush();
    }
}
