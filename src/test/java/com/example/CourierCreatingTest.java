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
import static org.junit.Assert.assertTrue;

public class CourierCreatingTest {
    private CourierClient courierClient;
    private int courierId;

    @Before
    public void setUp(){
        courierClient=new CourierClient();
    }
    @After
    public void tearDown(){
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Проверка возможности создания курьера")
    @Description("Эндпоинт api/v1/courier")
    public void courierСanBeCreatedWithValidParameters(){
        //Arrange
        Courier courier=Courier.getRandom();
        //Act
        ValidatableResponse createResponse=courierClient.create(courier);
        boolean isCourierCreated=createResponse.extract().path("ok");
        courierId=courierClient.login(CourierCredentials.from(courier)).extract().path("id");
        //Assert
        createResponse.assertThat().statusCode(SC_CREATED);
        assertTrue("Courier is not created", isCourierCreated);
        assertThat("Courier ID incorrect",courierId, is(not(0)));
    }
    @Test
    @DisplayName("Проверка невозможности создания двух одинаковых курьеров")
    @Description("Эндпоинт api/v1/courier")
    public void courierCannotBeCreatedIfItIsNotUnique(){
        //Arrange
        Courier courier=Courier.getRandom();
        //Act
        courierClient.create(courier);
        courierId=courierClient.login(CourierCredentials.from(courier)).extract().path("id");
        ValidatableResponse createResponse=courierClient.create(courier);
        //Assert
        createResponse.assertThat().statusCode(SC_CONFLICT);
        createResponse.assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }
    @Test
    @DisplayName("Проверка невозможности создания курьера без логина")
    @Description("Эндпоинт api/v1/courier")
    public void courierCannotBeCreatedWithoutLogin(){
        //Arrange
        Courier courier=Courier.builder()
                .password(RandomStringUtils.randomAlphabetic(10))
                .firstName(RandomStringUtils.randomAlphabetic(10))
                .build();
        //Act
        courierClient.create(courier);
        ValidatableResponse createResponse=courierClient.create(courier);
        //Assert
        createResponse.assertThat().statusCode(SC_BAD_REQUEST);
        createResponse.assertThat().body("message",equalTo("Недостаточно данных для создания учетной записи"));
    }
    @Test
    @DisplayName("Проверка невозможности создания курьера без пароля")
    @Description("Эндпоинт api/v1/courier")
    public void courierCannotBeCreatedWithoutPassword(){
        //Arrange
        Courier courier=Courier.builder()
                .login(RandomStringUtils.randomAlphabetic(10))
                .firstName(RandomStringUtils.randomAlphabetic(10))
                .build();
        //Act
        courierClient.create(courier);
        ValidatableResponse createResponse=courierClient.create(courier);
        //Assert
        createResponse.assertThat().statusCode(SC_BAD_REQUEST);
        createResponse.assertThat().body("message",equalTo("Недостаточно данных для создания учетной записи"));
    }

}
