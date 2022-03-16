package com.example;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import  static  org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;

public class OrderGettingTest {
    private OrderClient orderClient=new OrderClient();
    //Дефолтное значение размера  возвращаемого списка заказов
    private final int expectedListSize=30;
@Test
@DisplayName("Проверка возможности получения списка имеющихся в системе заказов")
@Description("Эндпоинт /api/v1/orders")
    public void orderListCanBeObtained() {
    ValidatableResponse getOrderResponse = orderClient.getOrderList();
    List<String> actualOrder = getOrderResponse.extract().jsonPath().getList("orders");

    getOrderResponse.statusCode(SC_OK);
    assertThat("Courier ID incorrect", actualOrder, is(not(nullValue())));
    assertEquals("Order list size does not match the default value",expectedListSize,actualOrder.size());

}
}
