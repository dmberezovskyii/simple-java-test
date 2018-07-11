package myprojects.automation.assignment5.utils.logging;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import java.io.File;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Custom reporting wrapper for TestNG.
 */
public class CustomReporter {


    /**
     * Logs action step that will be highlighted in test execution report.
     */
    public static void logAction(String message) {
        Reporter.log(String.format("[%-12s] ACTION: %s", LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME), message));
    }

    /**
     * Logs simple step.
     */
    public static void log(String message) {
        Reporter.log(String.format("[%-12s] %s", LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME), message));
    }

    public static void captureScreenshot(WebDriver driver, String screenshotName, String pathName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(source, new File(pathName + "/ " + screenshotName
                    + "_" + ".png"));
            String ESCAPE_PROPERTY = "org.uncommons.reportng.escape-output";
            System.setProperty(ESCAPE_PROPERTY, "false");
            URL path = new File("screenshot" + "/ " + screenshotName + "_"
                    + ".png").toURI().toURL();
            String test = "<a href=" + path + "> click to open screenshot of "
                    + screenshotName + "</a>";
            Reporter.log(screenshotName + test + "<br>");
            Reporter.log("<br>");
        } catch (Exception e) {
            System.out.println("Exception while taking screenshot "
                    + e.getMessage());
        }
    }



}
