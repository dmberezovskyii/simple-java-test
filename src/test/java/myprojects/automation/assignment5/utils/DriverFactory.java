package myprojects.automation.assignment5.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class DriverFactory {
    /**
     * @param browser Driver type to use in tests.
     * @return New instance of {@link WebDriver} object.
     */
    public static WebDriver initDriver(String browser) {
        switch (browser) {
            case "firefox":
                System.setProperty(
                        "webdriver.gecko.driver",
                        new File(DriverFactory.class.getResource("/geckodriver.exe").getFile()).getPath());
                return new FirefoxDriver();
            case "ie":
            case "internet explorer":
                System.setProperty(
                        "webdriver.ie.driver",
                        new File(DriverFactory.class.getResource("/IEDriverServer.exe").getFile()).getPath());
                InternetExplorerOptions ieOptions = new InternetExplorerOptions()
                        .destructivelyEnsureCleanSession();
                ieOptions.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
                return new InternetExplorerDriver(ieOptions);
            case "android":
                System.setProperty(
                        "webdriver.chrome.driver",
                        new File(DriverFactory.class.getResource("/chromedriver.exe").getFile()).getPath());
                Map<String, String> mobileEmulation = new HashMap<>();
                mobileEmulation.put("deviceName", "Nexus 5");
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
                return new ChromeDriver(chromeOptions);
            case "chrome":
            default:
                boolean OS = true;
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--disable-extensions"); // disabling extensions
                options.addArguments("disable-infobars"); // disabling infobars
                options.addArguments("--disable-gpu"); // applicable to windows os only
                options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
                options.addArguments("--no-sandbox"); // Bypass OS security model
                options.addArguments("--headless"); // Bypass OS security model
                String os = System.getProperty("os.name").toLowerCase();
                if (os.contains("win")) {
                    System.setProperty(
                            "webdriver.chrome.driver",
                            new File(DriverFactory.class.getResource("/chromedriver.exe").getFile()).getPath());

//                    return new ChromeDriver();
                } else if (os.contains("nux") || os.contains("nix")) {
                    File file = new File("chromedriver");
                    file.setReadable(true, true);
                    file.setWritable(true, true);
                    file.setExecutable(true, true);


//                ChromeDriverService chromeService = new Builder().withVerbose(true)
//                        .withLogFile(new File(DriverFactory.class.getResource("/driver.log")
//                                .getFile()).getPath().build();
                    System.setProperty(
                            "webdriver.chrome.driver",
                            ("chromedriver"));
                }

                if (OS == true)
                    return new ChromeDriver(options);
                else {
                    return new ChromeDriver(options);
                }

        }

    }

    /**
     * @param browser Remote driver type to use in tests.
     * @param gridUrl URL to Grid.
     * @return New instance of {@link RemoteWebDriver} object.
     */
    public static WebDriver initDriver(String browser, String gridUrl) {
        // TODO prepare capabilities for required browser and return RemoteWebDriver instance
        switch (browser) {
            case "android":
                Map<String, String> mobileEmulation = new HashMap<>();
                mobileEmulation.put("deviceName", "iPhone 5");
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
                return new ChromeDriver(chromeOptions);

            case "remote":

                try {
                    ChromeOptions options = new ChromeOptions();
                    return new RemoteWebDriver(new URL(gridUrl), DesiredCapabilities.chrome());
                } catch (MalformedURLException e) {
                    System.out.println("Error init driver");
                }
            case "chrome":
            default:
                System.setProperty(
                        "webdriver.chrome.driver",
                        new File(DriverFactory.class.getResource("/chromedriver.exe").getFile()).getPath());
                return new ChromeDriver();
        }
    }
}
