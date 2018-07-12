package myprojects.automation.assignment5.tests;

import myprojects.automation.assignment5.BaseTest;
import org.testng.annotations.Test;

public class PerformanceTest extends BaseTest {

    @Test(enabled = true)
    public void performaceSignUp(){
        api.performanceSignUp();
    }

    @Test(enabled = true)
    public void performanceCarList(){
        api.performanceApproveCarList();
    }
}
