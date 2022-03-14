package com.example;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class CourierClient extends ScooterRestClient {
    private static final String CREATE_COURIER ="api/v1/courier" ;
    private static final  String LOGIN_COURIER="api/v1/courier/login";
    private static final String DELETE_COURIER="/api/v1/courier/";
    @Step("Авторизация курьера с {courierCredentials}")
    public ValidatableResponse login(CourierCredentials courierCredentials){
        return given().log().all()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(LOGIN_COURIER)
                .then().log().all();
    }
    @Step("Создание курьера с {courier}")
    public ValidatableResponse create(Courier courier){
        return given().log().all()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(CREATE_COURIER)
                .then().log().all();
    }
    @Step("Удаление курьера с id={courierId}")
    public ValidatableResponse delete(int courierId){
        return given().log().all()
                .spec(getBaseSpec())
                .when()
                .delete(DELETE_COURIER+courierId)
                .then().log().all();
    }
}
