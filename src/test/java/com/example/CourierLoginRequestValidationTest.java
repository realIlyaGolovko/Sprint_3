package com.example;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import  static  org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CourierLoginRequestValidationTest {
    private static final CourierClient courierClient=new CourierClient();
    private static final Courier courier=Courier.getRandom();
    private final   CourierCredentials courierCredentials;
    private int courierId;
    private final int expectedStatusCode;
    private final String expectedErrorMsg;

    public CourierLoginRequestValidationTest(CourierCredentials courierCredentials, int expectedStatusCode,String expectedErrorMsg){
        this.courierCredentials=courierCredentials;
        this.expectedStatusCode=expectedStatusCode;
        this.expectedErrorMsg=expectedErrorMsg;}
    //Arrange
    @Parameterized.Parameters
    public static Object[][] getCredentialsData(){
        return new Object[][] {
                {CourierCredentials.builder()
                        .login(courier.getLogin())
                        .build(),SC_BAD_REQUEST,"Недостаточно данных для входа"},
                {CourierCredentials.builder()
                        .password(courier.getPassword())
                        .build(),SC_BAD_REQUEST,"Недостаточно данных для входа"},
                {CourierCredentials.builder()
                        .login(RandomStringUtils.randomAlphabetic(10))
                        .password(courier.getPassword())
                        .build(),SC_NOT_FOUND,"Учетная запись не найдена"},
                {CourierCredentials.builder()
                        .login(courier.getLogin())
                        .password(RandomStringUtils.randomAlphabetic(10))
                        .build(),SC_NOT_FOUND,"Учетная запись не найдена"},
        };
    }
    @Test
    @DisplayName("Проверка невозможности авторизации курьера c корректным логином/паролем")
    @Description("Эндпоинт /api/v1/courier/login")
    public void courierCannotLoginWithIncorectParameters(){
        //Act
        courierClient.create(courier);
        ValidatableResponse loginResponseForDelete = courierClient.login(CourierCredentials.from(courier));
        courierId=loginResponseForDelete.extract().path("id");
        ValidatableResponse loginResponse=new CourierClient().login(courierCredentials);
        int actualStatusCode=loginResponse.extract().statusCode();
        String actualErrorMsg=loginResponse.extract().path("message");
        //Assert
        assertEquals("Status code is incorrect",expectedStatusCode, actualStatusCode);
        assertEquals ("Error message is  incorrect", expectedErrorMsg, actualErrorMsg);
    }
    @After
    public void tearDown(){courierClient.delete(courierId);}
}

