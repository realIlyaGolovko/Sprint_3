package com.example;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import  static  org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;

public class OrderGettingTest {
    private final OrderClient orderClient=new OrderClient();

    @Test
@DisplayName("Проверка возможности получения списка имеющихся в системе заказов")
@Description("Эндпоинт /api/v1/orders")
    public void orderListCanBeObtained() {
    //Arrange
    //Дефолтное значение размера  возвращаемого списка заказов
    int expectedListSize = 30;
    //Act
    ValidatableResponse getOrderResponse = orderClient.getOrderList();
    List< Object> actualOrder = getOrderResponse.extract().jsonPath().getList("orders");
    //Assert
    getOrderResponse.statusCode(SC_OK);
    getOrderResponse.assertThat().body("orders.track",is(not(nullValue())));
    assertThat(actualOrder.isEmpty(),is(false));
    assertEquals(actualOrder.size(),expectedListSize);


}
}
