package com.example;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class CourierClient extends ScooterRestClient {
    private static final String CREATE_COURIER_PATH ="api/v1/courier" ;
    private static final  String LOGIN_COURIER_PATH ="api/v1/courier/login";
    private static final String DELETE_COURIER_PATH ="/api/v1/courier/";

    @Step("Запрос для авторизации курьера с {courierCredentials}")
    public ValidatableResponse login(CourierCredentials courierCredentials){
        return given().log().all()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(LOGIN_COURIER_PATH)
                .then().log().all();
    }
    @Step("Запрос на создание курьера с {courier}")
    public ValidatableResponse create(Courier courier){
        return given().log().all()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(CREATE_COURIER_PATH)
                .then().log().all();
    }
    @Step("Запрос на удаление курьера с id={courierId}")
    public ValidatableResponse delete(int courierId){
        return given().log().all()
                .spec(getBaseSpec())
                .when()
                .delete(DELETE_COURIER_PATH +courierId)
                .then().log().all();
    }
}
