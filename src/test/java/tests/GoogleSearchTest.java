package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.*;
import java.net.URL;
import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class GoogleSearchTest {

    WebDriver driver;
    ExtentReports extent;
    ExtentTest test;

    @Parameters("browser")
    @BeforeClass
    public void setUp(@Optional("chrome") String browser) throws Exception {

        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("test-output/ExtentReport_" + browser + ".html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        test = extent.createTest("Google Search Test on " + browser);

        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
        } else if (browser.equalsIgnoreCase("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
        } else {
            throw new IllegalArgumentException("Browser not supported: " + browser);
        }
        driver.manage().window().maximize();
    }

    @Test
    public void googleTest() {
        driver.get("https://www.google.com");
        test.pass("Navigated to Google");
        String title = driver.getTitle();
        test.pass("Page title is: " + title);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        if (extent != null) {
            extent.flush();
        }
    }
}
