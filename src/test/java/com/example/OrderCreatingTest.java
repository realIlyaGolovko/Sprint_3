package com.example;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import  static  org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class OrderCreatingTest {
    private final OrderClient orderClient=new OrderClient();
    private Order order;
    private int track;
    private  final List<String> expectedColor;

    public OrderCreatingTest(List<String> expectedColor){
        this.expectedColor=expectedColor;
    }
    @After
    public void tearDown(){orderClient.cancel(track);}
    //Arrange
    @Parameterized.Parameters
    public static Object[][] getOrderData(){
        return new Object[][]{
                {List.of("BLACK")},{List.of("GREY")},{List.of("BLACK,GREY")},null};
    }
@Test
@DisplayName("Проверка возможности создания заказа и использования различных цветов в запросе ")
@Description("Эндпоинт /api/v1/orders")
    public void orderCanBeCreatedWithDifferentColors(){
        //Act
        order=Order.getRandomOrder(expectedColor);
        ValidatableResponse createdResponse=orderClient.create(order);
        track=createdResponse.extract().path("track");
        ValidatableResponse getOrderResponse=orderClient.getOrder(track);
        List<String> actualColors=getOrderResponse.extract().jsonPath().getList("order.color");
        //Assert
        createdResponse.statusCode(SC_CREATED);
        assertThat("Courier ID incorrect", track, is(not(0)));
        assertEquals("Not expected colors",expectedColor,actualColors);}
}
