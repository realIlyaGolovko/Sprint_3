package com.example;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
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
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));
        courierId = loginResponse.extract().path("id");
        //Assert
        loginResponse.statusCode(SC_OK);
        assertThat("Courier ID incorrect", courierId, is(not(0)));
    }
    @Test
    @DisplayName("Проверка невозможности авторизации курьера без логина")
    @Description("Эндпоинт /api/v1/courier/login")
    public void courierCannotLoginWithoutLogin(){
        //Arrange
        courierClient.create(courier);
        CourierCredentials courierCredentials=CourierCredentials.builder()
                .password(courier.getPassword())
                .build();
        //Act
        ValidatableResponse loginResponse=courierClient.login(courierCredentials);
        //Авторизация и получение id, для последующего удаления созданного курьера
        ValidatableResponse loginResponseForDelete = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));
        courierId = loginResponseForDelete.extract().path("id");
        //Assert
        loginResponse.assertThat().statusCode(SC_BAD_REQUEST);
        loginResponse.assertThat().body("message", equalTo("Недостаточно данных для входа"));
        }
    @Test
    @DisplayName("Проверка невозможности авторизации курьера без пароля")
    @Description("Эндпоинт /api/v1/courier/login")
    public void courierCannotLoginWithoutPassword(){
        //Arrange
        courierClient.create(courier);
        CourierCredentials courierCredentials=CourierCredentials.builder()
                .login(courier.getLogin())
                .build();
        //Act
        ValidatableResponse loginResponse=courierClient.login(courierCredentials);
        //Авторизация и получение id, для последующего удаления созданного курьера
        ValidatableResponse loginResponseForDelete = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));
        courierId = loginResponseForDelete.extract().path("id");
        //Assert
        loginResponse.assertThat().statusCode(SC_BAD_REQUEST);
        loginResponse.assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }
    @Test
    @DisplayName("Проверка невозможности авторизации курьера c некорректным логином ")
    @Description("Эндпоинт /api/v1/courier/login")
    public void courierCannotLoginWithIncorrectLogin(){
        //Arrange
        String loginTest= RandomStringUtils.randomAlphabetic(10);
        courierClient.create(courier);
        //Act
        ValidatableResponse loginResponse=courierClient.login(new CourierCredentials(loginTest,courier.getPassword()));
        //Авторизация и получение id, для последующего удаления созданного курьера
        ValidatableResponse loginResponseForDelete = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));
        courierId = loginResponseForDelete.extract().path("id");
        //Assert
        loginResponse.assertThat().statusCode(SC_NOT_FOUND);
        loginResponse.assertThat().body("message", equalTo("Учетная запись не найдена"));
    }
    @Test
    @DisplayName("Проверка невозможности авторизации курьера c некорректным паролем")
    @Description("Эндпоинт /api/v1/courier/login")
    public void courierCannotLoginWithIncorrectPassword(){
        //Arrange
        String passwordTest= RandomStringUtils.randomAlphabetic(10);
        courierClient.create(courier);
        //Act
        ValidatableResponse loginResponse=courierClient.login(new CourierCredentials(courier.getLogin(),passwordTest));
        //Авторизация и получение id, для последующего удаления созданного курьера
        ValidatableResponse loginResponseForDelete = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));
        courierId = loginResponseForDelete.extract().path("id");
        //Assert
        loginResponse.assertThat().statusCode(SC_NOT_FOUND);
        loginResponse.assertThat().body("message", equalTo("Учетная запись не найдена"));
    }
    @Test
    @DisplayName("Проверка невозможности авторизации с несуществуюим курьером")
    @Description("Эндпоинт /api/v1/courier/login")
    public void courierCannotLoginWithINonExistentParameters(){
        //Arrange
     String loginTest=RandomStringUtils.randomAlphabetic(10);
     String passwordTest=RandomStringUtils.randomAlphabetic(10);
     courierClient.create(courier);
        //Act
     ValidatableResponse loginResponse=courierClient.login(new CourierCredentials(loginTest,passwordTest));
        //Авторизация и получение id, для последующего удаления созданного курьера
     ValidatableResponse loginResponseForDelete = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));
     courierId = loginResponseForDelete.extract().path("id");
        //Assert
     loginResponse.assertThat().statusCode(SC_NOT_FOUND);
     loginResponse.assertThat().body("message", equalTo("Учетная запись не найдена"));

    }
}
