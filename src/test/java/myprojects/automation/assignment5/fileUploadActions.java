package myprojects.automation.assignment5;

import myprojects.automation.assignment5.utils.DriverFactory;
import myprojects.automation.assignment5.utils.logging.CustomReporter;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class fileUploadActions extends BaseTest {
    private Actions actions;
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor jsExecutor;

    /*Initial elements*/
    private By pageTitle = By.className("page-title");
    private By Button = By.className("button");
    @FindBy(xpath = "//*[@class=\"upload-list\"]/div[1]")
    private WebElement driverLicense;
    @FindBy(xpath = "//*[@class=\"upload-list\"]/div[2]")
    private WebElement payStabPhoto;
    @FindBy(xpath = "//*[@class=\"upload-list\"]/div[3]")
    private WebElement bankActivity;
    By uplButton = By.xpath("//button[.=\"Upload from gallery\"]");
    private By returnButton = By.className("upload-button");

    public fileUploadActions(WebDriver driver) {
        this.actions = new Actions(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 30);
        jsExecutor = (JavascriptExecutor) driver;
    }

    //TODO Testing  documents upload
    public void getUploadLinkList(String path) {
        try {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

            List<WebElement> uploadLinks = driver.findElements(By.className("checkable-input"));
            for (int i = 0; i < uploadLinks.size(); i++) {
                if (i == 0) {
                    uploadLinks.get(i).click();
                    isElementEnabled(Button, "driverlicense");

                    try {
                        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
                        setDriverLicense(path);
                    } catch (NoSuchElementException e) {
                        CustomReporter.log("Failed getting driver license");
                        CustomReporter.captureScreenshot(driver, "driverlicense"
                                , "driverlicense");
                    }
                } else if (i == 1) {
                    uploadLinks.get(i).click();
                    try {
                        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
                        setPayStabPhoto();
                    } catch (NullPointerException e) {
                        CustomReporter.log("Failed setting payStabPhoto");
                    }
                } else if (i == 2) {
                    uploadLinks.get(i).click();
                    try {
                        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
                        setBankActivity();
                    } catch (NullPointerException e) {
                        CustomReporter.log("Failed setting payStabPhoto");
                    }
                }

            }
        } catch (NoSuchElementException e) {
            CustomReporter.captureScreenshot(driver, "uploadocuments", "uploaddocumets");
        }
    }

    public void setDriverLicense(String fileName) {
        By licenseTitle = By.xpath("//h1[.=\"Upload your driver’s license\"]");
        Assert.assertEquals("Upload your driver’s license"
                , driver.findElement(licenseTitle).getText());

        try {
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            clickUsingJs(uplButton);
            String filePath = new File(DriverFactory.class.getResource("/invalid_pictures/").getFile()).getPath();
            driver.findElement(uplButton).sendKeys(filePath + fileName);
            isElementDisplay(returnButton, "driver_license");
            CustomReporter.log("Driver license downloaded");
        } catch (NoSuchElementException e) {
            CustomReporter.log("\n" + "Download driver license photo is failed");
        }

    }

    public void setPayStabPhoto() {
        By paystabTitle = By.xpath("//h1[.=\"Upload a paystub photo\"]");
        Assert.assertEquals("Upload a paystub photo"
                , driver.findElement(paystabTitle).getText());

        try {
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
//            uplButton.click();
            CustomReporter.log("\n" + "Pay stab photo is added");
        } catch (NoSuchElementException e) {

            CustomReporter.captureScreenshot(driver, "paystab", "paystab");
        }
    }

    public void setBankActivity() {
        By payActivityMonth = By.xpath("//h1[.=\" Last 3 months of bank account activity \"]");
        Assert.assertEquals(" Last 3 months of bank account activity "
                , driver.findElement(payActivityMonth).getText());

        try {
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
//            uplButton.click();

        } catch (NoSuchElementException e) {

        }
    }


    public void isElementDisplay(By element, String pageName) {
        try {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            if (driver.findElement(element).isDisplayed())
                driver.findElement(element).click();

        } catch (NoSuchElementException e) {
            CustomReporter.log("Button isn't found");
            CustomReporter.captureScreenshot(driver, "uploadbuttn", pageName);
        }
    }

    public void isElementEnabled(By element, String pageName) {
        try {
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            Assert.assertTrue(driver.findElement(element).isEnabled());
            driver.findElement(element).click();
        } catch (NoSuchElementException e) {
            CustomReporter.log("Upload button isDisabled");
            CustomReporter.captureScreenshot(driver, "buttondisable", pageName);
        }
    }

    public void clickUsingJs(By element) {
        driver.findElement(element);
        jsExecutor.executeScript("arguments[0].click();", element);
    }
}