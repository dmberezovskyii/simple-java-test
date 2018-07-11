package myprojects.automation.assignment5;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import myprojects.automation.assignment5.parse.Parser;
import myprojects.automation.assignment5.utils.logging.CustomReporter;

import java.io.File;
import java.util.Random;

import static com.jayway.restassured.RestAssured.given;

public class APIActions extends BaseTest {

    private String carId;
    private final String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0ZWxlcGhvbmVOdW1iZXIiOiIrMTQzODQ0ODQyMjgiLCJ1c2VySUQiOiI1YjA2N2Y3NGMwMzIwYzExZmM3M2U1NzAiLCJpYXQiOjE1MzExNDEwMzQsImV4cCI6MTUzMTc0NTgzNH0.5KCCyI8GUu1P2ayLkTCqKLaHHiVILiJZ5mVyZoZEWL8";


    public APIActions(Response response) {
        this.response = response;
    }

    public void postSignUP() {


        response = given()
                .body("{\"telephoneNumber\": \"+14384484228\"}")
                .when()
                .contentType(ContentType.JSON)
                .post("https://demo.instantcarloanapproval.ca/api/sign-up")
                .then().assertThat().statusCode(200).extract().response();
        CustomReporter.log(response.asString());
        System.out.println(response.asString());


        JsonPath jPath = Parser.rawToJSON(response);
        int code = jPath.get("message.activationCode");
        //TOdo checkig activation code
        response = given()
                .header("token", token)
                .when()
                .body("{\n" +
                        "\"telephoneNumber\": \"+14384484228\",\n" +
                        "  \"activationCode\": " + code + ",\n" +
                        "  \"typeOfCar\": \"SUV\",\n" +
                        "  \"userMonthlyBudget\": \"401-600\",\n" +
                        "  \"whyUserNeedCar\":[\"to_drive_from_work\"],\n" +
                        "  \"0\": \"to_drive_from_work\"\n" +
                        "}")
                .contentType(ContentType.JSON)
                .post("https://demo.instantcarloanapproval.ca/api/sign-up-check-activation-code")
                .then().assertThat().statusCode(200)
                .extract().response();
        System.out.println(response.asString());

    }


    public void postUsrName() {

        Response response = given()
                .header("token", token)
                .body("{\"userName\": \"k Kobein\"}")
                .when()
                .contentType(ContentType.JSON)
                .post("https://demo.instantcarloanapproval.ca/api/get-user-name")
                .then().assertThat().statusCode(200).extract().response();
        CustomReporter.log(response.asString());
        System.out.println(response.asString());
    }


    public void postUserNameBirthDay() {
        response = given()
                .header("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0ZWxlcGhvbmVOdW1iZXIiOiIrMTQzODQ0ODQyMjgiLCJ1c2VySUQiOiI1YjA2N2Y3NGMwMzIwYzExZmM3M2U1NzAiLCJpYXQiOjE1MzExNDEwMzQsImV4cCI6MTUzMTc0NTgzNH0.5KCCyI8GUu1P2ayLkTCqKLaHHiVILiJZ5mVyZoZEWL8")
                .when()
                .body("{\"birthDay\": \"11111988\"}")
                .contentType(ContentType.JSON)
                .post("https://demo.instantcarloanapproval.ca/api/add-birth-day-to-user-model")
                .then().assertThat().statusCode(200).extract().response();
        System.out.println(response.asString());

    }


    public void postGenderAPIRes() {
        response = given()
                .header("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0ZWxlcGhvbmVOdW1iZXIiOiIrMTQzODQ0ODQyMjgiLCJ1c2VySUQiOiI1YjA2N2Y3NGMwMzIwYzExZmM3M2U1NzAiLCJpYXQiOjE1MzExNTY4MzIsImV4cCI6MTUzMTc2MTYzMn0.H_pSsnzWAeQWl_ZDgoxiljTVo5VZb-85IU4WRfH1pCk")
                .when()
                .body("{\n" +
                        "\t\"gender\": 1\n" +
                        "}")
                .contentType(ContentType.JSON)
                .post("https://demo.instantcarloanapproval.ca/api/male-or-female")
                .then().assertThat().statusCode(200).extract().response();
        System.out.println(response.asString());
        CustomReporter.log(response.getStatusCode() + " | " + response.asString());
    }


    public void postWorkingStatus() {
        response = given()
                .header("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0ZWxlcGhvbmVOdW1iZXIiOiIrMTQzODQ0ODQyMjgiLCJ1c2VySUQiOiI1YjA2N2Y3NGMwMzIwYzExZmM3M2U1NzAiLCJpYXQiOjE1MzExNTY4MzIsImV4cCI6MTUzMTc2MTYzMn0.H_pSsnzWAeQWl_ZDgoxiljTVo5VZb-85IU4WRfH1pCk")
                .when()
                .body("{\n" +
                        "\t\"workingStatus\": 1\n" +
                        "}")
                .contentType(ContentType.JSON)
                .post("https://demo.instantcarloanapproval.ca/api/get-working-status")
                .then().assertThat().statusCode(200).extract().response();
        CustomReporter.log(response.getStatusCode() + " | " + response.asString());
        System.out.println(response.asString());
    }


    public void postWorkExpirience() {
        Random random = new Random();
        int experience = 0 + (int) (Math.random() * 2);

        response = given()
                .header("token", token)
                .when()
                .body("{\"workingExperience\":" + experience + "}")
                .contentType(ContentType.JSON)
                .post("https://demo.instantcarloanapproval.ca/api/work-experience")
                .then().assertThat().statusCode(200)
                .extract().response();
        CustomReporter.log(response.asString());
        System.out.println(response.asString());
    }


    public void postSelectMonthlyIncome() {
        response = given()
                .header("token", token)
                .when()
                .body("{\"monthlyIncome\": \"1\"}")
                .contentType(ContentType.JSON)
                .post("https://demo.instantcarloanapproval.ca/api/monthly-income")
                .then().assertThat().statusCode(200)
                .extract().response();
        CustomReporter.log(response.asString());
    }


    public void postMonthlyIncomeConfirm() {
        response = given().header("token", token)
                .body("{\n" +
                        "   \"confirmMonthlyIncome\": 1\n" +
                        "}")
                .contentType(ContentType.JSON)
                .when()
                .post("https://demo.instantcarloanapproval.ca/api/confirm-monthly-income")
                .then().assertThat().statusCode(200)
                .extract().response();
        System.out.println(response.asString());
        CustomReporter.log(response.asString());

    }


    public void postFileUpload() {

        File filePath = new File(System.getProperty("user.dir") + "/src/test/resources/jenkins.jpg");
        response = given()
                .header("token", token)
                .multiPart("file", filePath, "image/jpeg")
                .multiPart("type", "1")
                .when()
                .contentType("multipart/form-data")
                .post("https://demo.instantcarloanapproval.ca/api/files_restore")
                .then().assertThat()
                .extract().response();
        System.out.println(response.asString());
        CustomReporter.log(response.asString());

    }


    public void getApprovedCarListInfo() {
        response = given()
                .header("token", token)
                .when()
                .get("https://demo.instantcarloanapproval.ca/api/approved-cars")
                .then().assertThat().statusCode(200)
                .and().contentType(ContentType.JSON).extract().response();
        CustomReporter.log(response.asString());
        JsonPath jPath = Parser.rawToJSON(response);
        int length = jPath.get("listOfCars.size()");
        System.out.println(length);
        int carIdNumber = 0 + (int) (Math.random() * length);
        carId = jPath.get("listOfCars[" + carIdNumber + "].id");
        CustomReporter.log("Car id is -> " + carId);

        response = given()
                .header("token", token)
                .when()
                .get("https://demo.instantcarloanapproval.ca/api/search-cars/" + carId)
                .then()
                .and().contentType(ContentType.JSON)
                .extract().response();
        System.out.println(response.asString());
        CustomReporter.log(response.asString());
        System.out.println(carId);

        response = given()
                .header("token", token)
                .when()
                .body("{\n" +
                        "\t\"carId\": \"" + carId + "\"\n" +
                        "}")
                .contentType(ContentType.JSON)
                .post("https://demo.instantcarloanapproval.ca/api/select_car/")
                .then().assertThat().statusCode(200)
                .and()
                .extract().response();
        CustomReporter.log(response.asString());
        System.out.println(response.asString());

        response = given()
                .header("token", token)
                .when()
                .get("https://demo.instantcarloanapproval.ca/api/select_car/list")
                .then().assertThat().statusCode(200)
                .and().contentType(ContentType.JSON)
                .extract().response();
        CustomReporter.log(response.asString());
        System.out.println(response.asString());


//        for (int i = 0; i < length; i++) {
//            System.out.println((String) jPath.get("listOfCars[" + i + "].name") + " | " + jPath.get("listOfCars[" + i + "].id"));
//        }
    }

    public void postAcceptDelivery() {
        response = given()
                .header("token", token)
                .when()
                .body("")
                .contentType(ContentType.JSON)
                .post("https://demo.instantcarloanapproval.ca/api/accept-delivery")
                .then().assertThat().statusCode(200)
                .extract().response();

        System.out.println(response.asString());

    }

    public void postSignatureAccept() {
        response = given()
                .header("token", token)
                .when()
                .body("{\n" +
                        "        \"isEmail\": \"false\",\n" +
                        "        \"name\": \"dd\"\n" +
                        "    }")
                .contentType(ContentType.JSON)
                .post("https://demo.instantcarloanapproval.ca/api/signature-accept")
                .then().assertThat().statusCode(200)
                .extract().response();
        CustomReporter.log("Signature Page -> " + response.asString());
        System.out.println(response.asString());
    }


    public void postUserDelete() {
        response = given()
                .header("token", token)
                .when()
                .contentType(ContentType.JSON)
                .post("https://demo.instantcarloanapproval.ca/api/user-delete")
                .then().assertThat().statusCode(200)
                .extract().response();
        CustomReporter.log(response.asString());
        System.out.println(response.asString());
    }

    public void postCardPaymentParams() {
        response = given()
                .header("token", token)
                .when()
                .body("{\n" +
                        "  \"card\":{\n" +
                        "        \"brand\": \"k Kobik\",\n" +
                        "        \"code\": \"123\",\n" +
                        "        \"number\": \"1111 1111 1111 1111\",\n" +
                        "        \"valid\": \"01/19\"\n" +
                        "  },\n" +
                        "   \"payment_method_nonce\": \"fake-valid-nonce\"\n" +
                        "}")
                .contentType(ContentType.JSON)
                .post("https://demo.instantcarloanapproval.ca/api/get-payment-method-nonce-from-client-side-creating-transaction")
                .then().assertThat().statusCode(200)
                .extract().response();
        CustomReporter.log("Set card values ->" + response.asString());
        System.out.println(response.asString());

    }
}
