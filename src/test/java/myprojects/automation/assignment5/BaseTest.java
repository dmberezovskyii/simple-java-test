package myprojects.automation.assignment5;

import com.jayway.restassured.response.Response;
import myprojects.automation.assignment5.model.Data;
import myprojects.automation.assignment5.utils.DriverFactory;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

/**
 * Base script functionality, can be used for all Selenium scripts.
 */
public abstract class BaseTest {
    protected APIActions api;
    protected Response response;
    protected EventFiringWebDriver driver;
    protected GeneralActions actions;
    protected fileUploadActions fileUpload;
    protected Data data;
    protected boolean isMobileTesting;


    @BeforeClass
    @Parameters({"selenium.browser", "selenium.grid"})
    public void setUp(@Optional("chrome") String browser, @Optional("http://localhost:4444/wd/hub") String gridUrl) {
        // TODO create WebDriver instance according to passed parameters
        driver = new EventFiringWebDriver(DriverFactory.initDriver(browser));
//        driver.register(new EventHandler());

        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        // unable to maximize window in mobile mode
        if (!isMobileTesting(browser))
            driver.manage().window().maximize();

        isMobileTesting = isMobileTesting(browser);


        actions = PageFactory.initElements(driver, GeneralActions.class);
        fileUpload = PageFactory.initElements(driver, fileUploadActions.class);
        data = new Data(driver);
        api = new APIActions(response);

    }

    /**
     * Closes driver instance after test class execution.
     */
    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * @return Whether required browser displays content in mobile mode.
     */
    private boolean isMobileTesting(String browser) {
        switch (browser) {
            case "android":
                return true;
            case "firefox":
            case "ie":
            case "internet explorer":
            case "ubuntu":
            default:
                return false;
        }
    }
}
