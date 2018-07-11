package myprojects.automation.assignment5.tests;

import com.jayway.restassured.response.Response;
import myprojects.automation.assignment5.BaseTest;
import org.testng.annotations.Test;

public class clienPartAPITEST extends BaseTest {

    private String carId;
    private final String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0ZWxlcGhvbmVOdW1iZXIiOiIrMTQzODQ0ODQyMjgiLCJ1c2VySUQiOiI1YjA2N2Y3NGMwMzIwYzExZmM3M2U1NzAiLCJpYXQiOjE1MzExNDEwMzQsImV4cCI6MTUzMTc0NTgzNH0.5KCCyI8GUu1P2ayLkTCqKLaHHiVILiJZ5mVyZoZEWL8";
    private Response response;


    @Test(enabled = true)
    public void checkAPISignUP() {
        api.postSignUP();
    }

    @Test(enabled = true)
    public void checkGetUsrName() {
        api.postUsrName();
    }

    @Test(enabled = true)
    public void checkUserNameBirthDayStatus() {
        api.postUserNameBirthDay();
    }

    @Test(enabled = true)
    public void checkGender() {
        api.postGenderAPIRes();
    }

    @Test(enabled = true)
    public void checkWorkingStatus() {
        api.postWorkingStatus();
    }

    @Test(enabled = true)
    public void checkWorkExpirience() {
        api.postWorkExpirience();
    }

    @Test(enabled = true)
    public void selectMonthlyIncome() {
        api.postSelectMonthlyIncome();
    }

    @Test(enabled = true)
    public void monthlyIncomeConfirm() {
        api.postMonthlyIncomeConfirm();

    }

    @Test(enabled = true)
    public void fileUpload() {
        api.postFileUpload();
    }

    @Test(enabled = true)
    public void getApprovedCarList() {
        api.getApprovedCarListInfo();
    }

    @Test(enabled = true)
    public void getSignatureAccept(){
        api.postSignatureAccept();
    }

    @Test(enabled = true)
    public void userDelete() {
        api.postUserDelete();
    }


}


