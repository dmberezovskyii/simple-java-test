package myprojects.automation.assignment5;


import myprojects.automation.assignment5.model.Data;
import myprojects.automation.assignment5.utils.DataConverter;
import myprojects.automation.assignment5.utils.logging.CustomReporter;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Contains main script actions that may be used in scripts.
 */
public class GeneralActions {
    private WebDriver driver;
    private Actions actions;
    private WebDriverWait wait;
    private WebElement random;
    private JavascriptExecutor exec;

    By carTypeList = By.className("cartype-item");
    By budget = By.className("checkable-input");
    By wExpirience = By.className("checkable-input");
    By downPaymant = By.className("checkable-input");
    By Button = By.className("button");/*All buttons*/
    By inputField = By.className("input");/*phoneNumber,Full Name*/
    By birthdayList = By.xpath("//*[@class=\"phone-input\"]/div/input");/*birthdate*/
    By aviableCarList = By.className("car-item");
    private By uploadLaterLink = By.className("upload-sent");
    private By getTestField = By.className("fieldForTest");
    private By deliveryOptions = By.xpath("//p[.=\"Select delivery options\"]");
    @FindBy(className = "returned-button")
    private WebElement returnedButton;
    private By pageTitle = By.className("page-title");

    /*Card params*/
    @FindBy(id = "input1")
    private WebElement cardNumber;
    @FindBy(id = "input2")
    private WebElement cardHolder;
    @FindBy(name = "valid")
    private WebElement vaildTHRU;
    @FindBy(name = "code")
    private WebElement securityCode;

    public GeneralActions(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 60);
        exec = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

 

}
