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
    private By inputSMS1 = By.xpath("//*[@class=\"phone-input\"]/div/input[1]");
    private By inputSMS2 = By.xpath("//*[@id=\"root\"]/div/div[2]/div/div[2]/div[2]/div/input");
    private By inputSMS3 = By.xpath("//*[@id=\"root\"]/div/div[2]/div/div[2]/div[3]/div/input");
    private By inputSMS4 = By.xpath("//*[@id=\"root\"]/div/div[2]/div/div[2]/div[4]/div/input");
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

    public String getGetTestField() {
        String sms = null;
        try {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            sms = driver.findElement(getTestField).getText();
        } catch (NoSuchElementException e) {
            CustomReporter.log("SMS not Found");
            CustomReporter.captureScreenshot(driver, "sms", "sms");
        }
        return sms;


    }

    public void getCarList() {
        try {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            List<WebElement> links = driver.findElements(carTypeList);
            random = links.get(new Random().nextInt(links.size()));
            random.click();
            waitForContenLoad(Button);
            CustomReporter.log("Main page is passed");
        } catch (NullPointerException e) {
            CustomReporter.captureScreenshot(driver, "cartype", "cartype");
        }

    }

    public void getBudgetList() {
        try {
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            List<WebElement> links1 = driver.findElements(budget);
            random = links1.get(new Random().nextInt(links1.size()));
            random.click();
            waitForContenLoad(Button);
            CustomReporter.log("\n" + "Passed budget Page");
        } catch (NullPointerException e) {
            CustomReporter.log("\n" + "Budget page is failed");
            CustomReporter.captureScreenshot(driver, "budget", "budget");
        }
    }

    public void enableButton() {
        exec.executeScript("arguments[0].removeAttribute('disabled','disabled')", getElement());
    }

    public void useFor() {
        List<WebElement> links1 = driver.findElements(budget);
        random = links1.get(new Random().nextInt(links1.size()));
        random.click();
        waitForContenLoad(Button);
    }

    public WebElement getElement() {
        WebElement yourButton = driver.findElement(By.className("button"));
        return yourButton;
    }

    public void setPhoneNumber() throws InterruptedException {
//        6479469339
        Thread.sleep(500);
        driver.findElement(inputField).sendKeys("(438)448-4228");
        exec.executeScript("arguments[0].removeAttribute('disabled','disabled')", getElement());
        WebElement searchinput = driver.findElement(By.className("input"));
        exec.executeScript("arguments[0].value='(438)448-4228';", searchinput);

        if (getElement().isEnabled())
            getElement().click();
    }

    public void WeatherMessageBody() throws InterruptedException {
        By reciveSMSTITLE = By.xpath("//h1[.=\"Type received code\"]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(reciveSMSTITLE));
        Assert.assertEquals("Type received code", driver.findElement(reciveSMSTITLE).getText());
        try {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            String SMS = getGetTestField();
            String smsArray[] = new String[4];
            Assert.assertTrue(driver.findElement(reciveSMSTITLE)
                    .getText()
                    .contains("Type received code"));

            WebElement yourButton = driver.findElement(By.className("button"));
            exec.executeScript("arguments[0].removeAttribute('disabled','disabled')", yourButton);
            Thread.sleep(500);
            for (int i = 0; i < SMS.length(); i++) {
                smsArray[i] = String.valueOf(SMS.charAt(i));
                Thread.sleep(100);
            }
            driver.findElement(inputSMS1).sendKeys(smsArray[0]);
            Thread.sleep(200);
            driver.findElement(inputSMS2).sendKeys(smsArray[1]);
            Thread.sleep(200);
            driver.findElement(inputSMS3).sendKeys(smsArray[2]);
            Thread.sleep(200);
            driver.findElement(inputSMS4).sendKeys(smsArray[3]);
//        isElementEnabled(Button, "SMS");


            if (yourButton.isEnabled()) {
                yourButton.click();
            } else {
                driver.findElement(Button).click();
            }
            CustomReporter.log("\n Set SMS Page is passed");
        } catch (NoSuchElementException e) {
            CustomReporter.logAction("Button not clicked on the set message page");
            CustomReporter.captureScreenshot(driver, "sms", "sms");
        }


    }

    public void setFullName() throws InterruptedException {
//        Thread.sleep(1000);
        By birthTite = By.xpath("//h1[.=\"What is your full name ?\"]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(birthTite));
        Assert.assertEquals("What is your full name ?", driver.findElement(birthTite).getText());
        try {
            enableButton();
            new Actions(driver).moveToElement(driver.findElement(inputField)).perform();
            CustomReporter.logAction("\n Passed Full Name");
            wait.until(ExpectedConditions.visibilityOfElementLocated(inputField)).sendKeys("k Kobein");
            Thread.sleep(500);
            WebElement ele = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/button"));
            exec.executeScript("arguments[0].click();", ele);
            CustomReporter.log("\n Passed Full name page");
        } catch (NoSuchElementException e) {
            CustomReporter.captureScreenshot(driver, "fullname", "fullname");
            CustomReporter.logAction("\n Full name is failed");
        }

    }


    public void setDayOfBirth() throws InterruptedException {
        By birthTtle = By.xpath("//h1[.=\"Your birthday\"]");
        wait.until(ExpectedConditions.titleIs("Instant car loan approval - Canada"));
        Assert.assertEquals("Your birthday", driver.findElement(birthTtle).getText());
        try {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            enableButton();
            By d1 = By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[1]");
            By d2 = By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[2]");
            By m1 = By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[3]");
            By m2 = By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[4]");
            By y1 = By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[5]");
            By y2 = By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[6]");
            By y3 = By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[7]");
            By y4 = By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div[8]");
            wait.until(ExpectedConditions.visibilityOfElementLocated(d1));
            actions.moveToElement(driver.findElement(d1)).sendKeys("1").perform();
            Thread.sleep(150);
            actions.moveToElement(driver.findElement(d2)).sendKeys("1").perform();
            Thread.sleep(150);
            actions.moveToElement(driver.findElement(m1)).sendKeys("1").perform();
            Thread.sleep(150);
            actions.moveToElement(driver.findElement(m2)).sendKeys("1").perform();
            Thread.sleep(300);
            driver.findElement(y1).click();
            actions.moveToElement(driver.findElement(y1)).sendKeys("1").perform();
            Thread.sleep(200);
            actions.moveToElement(driver.findElement(y2)).sendKeys("9").perform();
            Thread.sleep(200);
            actions.moveToElement(driver.findElement(y3)).sendKeys("8").perform();
            Thread.sleep(175);
            actions.moveToElement(driver.findElement(y4)).sendKeys("8").perform();
            CustomReporter.logAction("Birthday entered");
            Thread.sleep(500);
            WebElement ele = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/button"));
            exec.executeScript("arguments[0].click();", ele);

        } catch (NullPointerException e) {
            CustomReporter.logAction("\n Error on set BithDay page");
            CustomReporter.captureScreenshot(driver, "birthday", "birthdaypage");
        }

    }

    public void setGender() {

//        List<WebElement> links1 = driver.findElements(Button);
//        random = links1.get(new Random().nextInt(links1.size()));
//
//        if (!links1.isEmpty()) {
//            random = links1.get(new Random().nextInt(links1.size()));
//        }
//        wait.until(ExpectedConditions.visibilityOfElementLocated(genderButton));
        try {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            WebElement genderButton = driver.findElement(By.xpath("//*[@class=\"gender-types\"]/button[2]"));
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", genderButton);
            CustomReporter.log("step Set Gender is Passed");
        } catch (NoSuchElementException e) {
            System.out.println("false");
            CustomReporter.logAction("Fail Gander");
        }
    }

    public boolean getWorkType() {

        try {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            WebElement buttonYes = driver.findElement(By.xpath("//*[@class=\"working-types\"]/button[1]"));
            wait.until(ExpectedConditions.visibilityOf(buttonYes));
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", buttonYes);
            CustomReporter.log("Step Set WorkType is Passed");
            return true;

        } catch (NoSuchElementException ex) {
            WebElement buttonNo = driver.findElement(By.xpath("//*[@class=\"working-types\"]/button[2]"));
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", buttonNo);
            return false;
        }

    }

    public void setWorkExpirience() {
//        if(!items.isEmpty()) {
//            inv.setItem(i, items.get(r.nextInt(items.size())));
//        }
        try {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            List<WebElement> links1 = driver.findElements(wExpirience);
            random = links1.get(new Random().nextInt(links1.size()));
            random.click();
            waitForContenLoad(Button);
            CustomReporter.log("\n Passed workExpirience page");

        } catch (NoSuchElementException e) {
            CustomReporter.log("\n Failed Work Expirience");
            CustomReporter.captureScreenshot(driver, "workExp", "worksexp");
        }

    }

    public void setMonthlyIncome() throws InterruptedException {
//        String income = String.format("%1s%s", "", "income?", "%1s%s");
//        By montlyIncomeTitle = By.xpath("//span[.=\" income? \"]");
//        Assert.assertEquals(driver.findElement(montlyIncomeTitle).getText(), income);
        Thread.sleep(1500);
        try {
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            List<WebElement> links1 = driver.findElements(budget);
            random = links1.get(new Random().nextInt(links1.size()));
            random.click();
            waitForContenLoad(Button);
            CustomReporter.log("\n Passed monthlyIncome");
        } catch (NoSuchElementException e) {
            CustomReporter.log("\n Fail monthlyincome");
            CustomReporter.captureScreenshot(driver, "monthlyincome", "monthly");
        }

    }

    public void setNoConfirmationMonthlyIncome() throws InterruptedException {

        Thread.sleep(1000);
//        noConfirmation.click();

        try {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            WebElement confirmIncome = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/button[1]"));
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", confirmIncome);
            CustomReporter.log("\n Monthly income is confirmed");
        } catch (NoSuchElementException e) {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            WebElement confirmIncome = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/button[1]"));
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", confirmIncome);
            CustomReporter.log("\n Monthly income isn't confirmed");
            CustomReporter.captureScreenshot(driver, "confirmation", "confirmationPage");
        }

    }

    public void setUploadLaterLink() {
        try {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            WebElement uplLaterLink = driver.findElement(By.linkText("I will upload documents later"));
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", uplLaterLink);
            CustomReporter.log("\n Upload link click Passed");
        } catch (NoSuchElementException e) {
            driver.findElement(uploadLaterLink).click();
            CustomReporter.captureScreenshot(driver, "uploadlink", "uploadPage");
        }
    }

    public void setDownPayment() throws InterruptedException {
        By spanText = By.xpath("//span[.=\"available?\"]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(spanText));
//        Thread.sleep(1500);
        try {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            List<WebElement> links1 =
                    wait
                            .until(ExpectedConditions
                                    .visibilityOfAllElements(driver.findElements(downPaymant)));
            random = links1.get(new Random().nextInt(links1.size() - 1));
            random.click();
            CustomReporter.logAction("\n" + "Set downpayment" + random.getText());
            String dwPay = random.getText();
            dwPay = dwPay.startsWith("$") ? dwPay.substring(1) : dwPay;
            Data.downPayment = Integer.parseInt(dwPay);
            isElementEnabled(Button, "downpament", "downpayment");
            CustomReporter.log("\n Downpament page passed" + " " + dwPay);
        } catch (NoSuchElementException e) {
            CustomReporter.log("\n Downpayment failed");
            CustomReporter.captureScreenshot(driver, "downPayment", "downpeyment");
        }

    }


    public void getAviableCarList() throws InterruptedException {

        By approvedTitlePage = By.xpath("//span[.=\"approved\"]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(approvedTitlePage));
        Thread.sleep(5000);
        try {
            driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
            List<WebElement> links1 =
                    wait
                            .until(ExpectedConditions
                                    .visibilityOfAllElements(driver.findElements(aviableCarList)));
            random = links1.get(new Random().nextInt(links1.size() - 1));
            random.click();
        } catch (NoSuchElementException e) {
            CustomReporter.captureScreenshot(driver, "carlist", "carlist");
        }
    }

    public void viewCar() {
//        By carBtnText = By.xpath("//button[.=\"Select this car\"]");
        try {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            WebElement selectCar = driver.findElement(By.className("button"));
            new Actions(driver).moveToElement(driver.findElement(inputField)).perform();
            CustomReporter.logAction("Passed car selection");
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", selectCar);
        } catch (NoSuchElementException e) {
            new Actions(driver).moveToElement(driver.findElement(Button)).perform();
            driver.findElement(Button).click();
            CustomReporter.logAction("Failed Car selection");
            CustomReporter.captureScreenshot(driver, "SelectCar", "selectcar");
        }
    }

    public void setDeliveryOptions() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(deliveryOptions));
        Assert.assertTrue(driver.findElement(deliveryOptions)
                .getText()
                .contains("Select delivery options"), "Contain");

        List<WebElement> itemHour = driver.findElements(By.className("item-hour"));
        random = itemHour.get(new Random().nextInt(itemHour.size() - 1));
        random.click();
    }

    public void orderSignatureRecivedPayment() throws InterruptedException {
        By signatureTitle = By.xpath("//p[.=\"Approval\"]");
        By signtatureFullName = By.xpath("//*[@class=\"page-signature\"]/div[6]/input");
        By vehicleCost = By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/ul[1]/li[1]/span");
        By leaseTerm = By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/ul[2]/li[1]/span");
        wait.until(ExpectedConditions.visibilityOfElementLocated(signatureTitle)).getText();
        Assert.assertTrue(driver.findElement(signatureTitle)
                .getText()
                .contains("Approval"), "Okey");
        try {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            /*Get signature data*/
            driver.findElement(signtatureFullName).sendKeys(Keys.ARROW_DOWN, "K Kobein");
            new Actions(driver).moveToElement(driver.findElement(signtatureFullName)).perform();
            wait.until(ExpectedConditions.elementToBeClickable(Button)).click();

        } catch (NoSuchElementException e) {
            CustomReporter.captureScreenshot(driver, "signatureerror", "signature");
            CustomReporter.logAction("\n Signature Page Error Look At screenshot");
        }

    }

    public void orderSignature() throws InterruptedException {
        String carCost;
        String vehCost;
        String lTerm;
        By signatureTitle = By.xpath("//p[.=\"Approval\"]");
        By signtatureFullName = By.xpath("//*[@class=\"page-signature\"]/div[6]/input");
        By vehicleCost = By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/ul[1]/li[1]/span");
        By leaseTerm = By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/ul[2]/li[1]/span");
        wait.until(ExpectedConditions.visibilityOfElementLocated(signatureTitle)).getText();
        Assert.assertTrue(driver.findElement(signatureTitle)
                .getText()
                .contains("Approval"), "Okey");
        try {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            /*Get signature data*/
            driver.findElement(signtatureFullName).sendKeys(Keys.ARROW_DOWN, "K Kobein");
            new Actions(driver).moveToElement(driver.findElement(signtatureFullName)).perform();

        } catch (NoSuchElementException e) {
            CustomReporter.captureScreenshot(driver, "signatureerror", "signature");
            CustomReporter.logAction("\n Signature Page Error Look At screenshot");
        }
        Thread.sleep(1500);
        try {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

            carCost = driver.findElement(vehicleCost).getText();
            /**/
            vehCost = driver.findElement(vehicleCost).getText().replaceAll(",", "");
            vehCost = vehCost.startsWith("$") ? vehCost.substring(1) : vehCost;
            Data.vehicleCost = Double.parseDouble(vehCost);
            lTerm = driver.findElement(leaseTerm).getText();
            lTerm = lTerm.substring(0, 2);
            Data.leaseTerm = Integer.parseInt(lTerm);
            checkOrderSignature();
//            wait.until(ExpectedConditions.elementToBeClickable(Button)).click();
        } catch (NullPointerException e) {

        }

    }

    public void checkOrderSignature() {
        By leaseRate = By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/ul[2]/li[2]/span");
        By taxes = By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/ul[2]/li[3]/span");
        By totalLeasePMT = By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/ul[2]/li[4]/span");
        By capCost = By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/ul[1]/li[3]/span");
        try {
            Assert.assertEquals(Data.getLeaseRate()
                    , DataConverter.parseStringPrice(driver.findElement(leaseRate)
                            .getText()
                            .replaceAll(",", "")));
            CustomReporter.log("\n Lease rate is passed->" +
                    Data.getLeaseRate() +
                    ":" + DataConverter
                    .parseStringPrice(driver.findElement(leaseRate)
                            .getText()
                            .replaceAll(",", "")));
            System.out.print("\n assertion_method_2() -> Part executed");
        } catch (NullPointerException e) {
            CustomReporter.log("\n Lease rate is fail" + Data.getLeaseRate() + ":" + DataConverter.parseStringPrice(driver.findElement(leaseRate).getText()));
        }

        try {
            Assert.assertEquals(Data.getTaxes()
                    , DataConverter.parseStringPrice(driver.findElement(taxes).getText()));
            CustomReporter.log("\n Taxes is passed ->" + Data.getTaxes() +
                    " : " + DataConverter.parseStringPrice(driver.findElement(taxes).getText()));
        } catch (NullPointerException e) {
            CustomReporter.log("\n Taxes is failed - >" + Data.getTaxes() +
                    " : " + DataConverter.parseStringPrice(driver.findElement(taxes).getText()));
        }

        try {
            Assert.assertEquals(Data.getTotalLeasePMT()
                    , DataConverter.parseStringPrice(driver.findElement(totalLeasePMT).getText().replaceAll(",", "")));
            CustomReporter.log("\n Total leasePMT is passed - >" + Data.getTotalLeasePMT() +
                    " : " + DataConverter.parseStringPrice(driver.findElement(totalLeasePMT).getText()));
        } catch (NullPointerException e) {
            CustomReporter.log("\n Total leasePMT is failed ->" + Data.getTotalLeasePMT() +
                    " : " + DataConverter.parseStringPrice(driver.findElement(totalLeasePMT).getText()));
        }

    }

    public void setCardParams() throws InterruptedException {
        Thread.sleep(1500);
        By payDepositTitle = By.xpath("//p[.=\" Pay deposit\"]");
        By payDepositButton = By.xpath("//*[@class=\"page-payments\"]/button[1]");
//        Assert.assertEquals(" Pay deposit", driver.findElement(payDepositTitle).getText());
//        Assert.assertTrue(driver.findElement(payDepositTitle).getText().contains(" Pay deposit"), "Pay deposit page");
        try {
            Random rndNum = new Random();
            for (int nbr = 0; nbr <= 8; nbr++) {
                String rndNum1 = String.valueOf(rndNum.nextInt());
                cardNumber.sendKeys(rndNum1);
                Thread.sleep(150);
            }

            cardHolder.sendKeys("k Kobein");
            vaildTHRU.sendKeys("01/19");
            Thread.sleep(750);
            for (int nbrs = 0; nbrs <= 3; nbrs++) {
                String rndNum1S = String.valueOf(rndNum.nextInt());
                securityCode.sendKeys(rndNum1S);
                Thread.sleep(100);
            }
//            driver.findElement(payDepositButton).click();
            wait.until(ExpectedConditions.elementToBeClickable(payDepositButton)).click();
        } catch (NullPointerException e) {
            CustomReporter.captureScreenshot(driver, "paydeposit", "paydeposit");
            CustomReporter.logAction("Somthing wrong on Pay deposit page");
        }
    }

    public void recivedPayment() throws InterruptedException {
        String vehiclePrice = null;
        double price = 0;
        By priceTitle = By.xpath("//p[.=\"per month\"]");
        By leasePricePerMonth = By.xpath("//*[@class=\"item-price\"]/p[1]");
        By backButton = By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div/div[1]/button");
        wait.until(ExpectedConditions.visibilityOfElementLocated(priceTitle));
        try {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            actions.moveToElement(driver.findElement(backButton)).perform();
            Thread.sleep(1000);
            vehiclePrice = driver.findElement(leasePricePerMonth)
                    .getText().replaceAll(",", "");
            vehiclePrice = vehiclePrice.startsWith("$") ? vehiclePrice.substring(1) : vehiclePrice;
            price = Double.parseDouble(vehiclePrice);
            Assert.assertEquals(price, Data.getTotalLeasePMT(), 0.00);
            CustomReporter.log("Passed received your payment" +
                    "Acual res: " + price + " | " +
                    "Expected res: " + Data.getTotalLeasePMT());
        } catch (NoSuchElementException e) {
            CustomReporter.log("Failed  received your payment");
        }
    }


    public boolean checkUser() {
        Boolean returned = null;
        By returnToHometitle = By.xpath("//h1[.=\"You are logged in as\"]");
        By deleteProfileLink = By.className("button-logout");
        By deleteButton = By.xpath("//*[@class=\"confirmation-buttons\"]/button[2]");

        try {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            wait.until(ExpectedConditions.visibilityOfElementLocated(returnToHometitle));
            wait.until(ExpectedConditions.visibilityOfElementLocated(deleteProfileLink)).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(deleteButton)).click();
            returned = true;
        } catch (NoSuchElementException e) {
            returned = false;
        }

        return returned;
    }

    public void checkLeasePMTOnSelectDeliveryOptions() {
        By deliveryLeasePrice = By.xpath("//*[@class=\"info-price\"]/b");

        wait.until(ExpectedConditions.visibilityOfElementLocated(deliveryOptions));
        Assert.assertTrue(driver.findElement(deliveryOptions)
                .getText()
                .contains("Select delivery options"), "Contain");

        try {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            String vehicleLease = driver.findElement(deliveryLeasePrice).getText().replaceAll(",", "");
            double price = DataConverter.parseStringPrice(vehicleLease);
            Assert.assertEquals(Data.getTotalLeasePMT(), price, 0);
            CustomReporter.log("\n Passed Lease PTM on select delivery option page");
        } catch (NoSuchElementException e) {
            CustomReporter.log("\n Failed Lease PTM on select delivery options page");
            CustomReporter.captureScreenshot(driver, "selectdeliveryprice", "selectdeliveryprice");

        }

    }

    public GeneralActions returnToHome() {
        returnedButton.click();
        return this;
    }

    public void waitForContenLoad(By element) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(element)).click();
    }

    public void profileDeleting() throws InterruptedException {
        By profile = By.className("avatar");
        By deleteProfileLink = By.className("button-logout");
        By confirmDelete = By.xpath("//*[@class=\"confirmation-buttons\"]/button[2]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(profile)).click();
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(deleteProfileLink)).click();
            Thread.sleep(500);
            wait.until(ExpectedConditions.visibilityOfElementLocated(confirmDelete)).click();
            Thread.sleep(500);
            CustomReporter.logAction("User is deleted");
        } catch (NoSuchElementException e) {
            CustomReporter.log("Failed profile deleting");
        }

    }

    public void checkLeaseRateOnCarPtifilePage() {
        By leasePMT = By.className("price-value");

        String price = wait.until(ExpectedConditions
                .visibilityOfElementLocated(leasePMT)).getText()
                .replaceAll(",", "");
        price = price.startsWith("$") ? price.substring(1) : price;
        double leaseCarPMT = Double.parseDouble(price);
        try {
            Assert.assertEquals(leaseCarPMT, Data.getTotalLeasePMT(), 0);
            CustomReporter.log("\n" + "Expected -> " + leaseCarPMT + " | " + "Actual: " + Data.getTotalLeasePMT());
        } catch (NoSuchElementException e) {
            CustomReporter.log("Failed leasePTM on car profile page");

        }

    }

    public void returnToHomePage() {
        By returnButton = By.xpath("//button[.=\"Return to home page\"]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(returnButton)).click();
    }

    public void returnToPreviousPage() {
        By backToPreviousPage = By.className("header-back");
        try {
            actions.moveToElement(driver.findElement(backToPreviousPage)).perform();
//            wait.until(ExpectedConditions.visibilityOfElementLocated(backToPreviousPage)).click();
            WebElement ele = driver.findElement(By.className("header-back"));
            exec.executeScript("arguments[0].click();", ele);
        } catch (NoSuchElementException e) {
            CustomReporter.log("Back button isn't found");
        }
    }

    private double isElementPresentValue(By element, String usName, String pathName) {
        double price = 0;
        try {
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            String label = driver.findElement(element).getText().replaceAll(",", "");
            price = DataConverter.parseStringPrice(label);

        } catch (NoSuchElementException e) {
            CustomReporter.captureScreenshot(driver, usName, pathName);
        }
        return price;
    }

    private boolean isElementEnabled(By element, String screenName, String pathName) {
        try {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            if (driver.findElement(element).isEnabled()) {
                driver.findElement(element).click();
            }
            return true;
        } catch (NoSuchElementException e) {
            CustomReporter.captureScreenshot(driver, screenName, pathName);
            return false;
        }
    }


}
