package com.example;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import  static  org.apache.http.HttpStatus.*;

public class CourierLoginTest {
    private CourierClient courierClient;
    private int courierId;
    private final Courier courier=Courier.getRandom();

    @Before
    public void setUp(){
        courierClient=new CourierClient();
    }
    @After
    public void tearDown(){
            courierClient.delete(courierId);}

    @Test
    @DisplayName("Проверка возможности авторизации курьера с валидными значениями")
    @Description("Эндпоинт /api/v1/courier/login")
    public void courierСanLoginWithValidParameters() {
        //Arrange
        courierClient.create(courier);
        //Act
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        courierId = loginResponse.extract().path("id");
        //Assert
        loginResponse.statusCode(SC_OK);
        assertThat("Courier ID incorrect", courierId, is(not(0)));
    }
}
