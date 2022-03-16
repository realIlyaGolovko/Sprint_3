package com.example;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class OrderClient extends ScooterRestClient {
    private final String CREATE_ORDER_PATH ="/api/v1/orders";
    private final String GET_ORDER_PATH="/api/v1/orders/track";
    private final String CANCEL_ORDER_PATH="/api/v1/orders/cancel";

@Step("Запрос на создание заказа с параметрами {order}")
    public ValidatableResponse create(Order order){
       return given().log().all()
               .spec(getBaseSpec())
               .body(order)
               .when()
               .post(CREATE_ORDER_PATH)
               .then().log().all();
    }
@Step("Запрос на получение содержимого заказа по номеру {track}")
    public ValidatableResponse getOrder(int track){
    return given().log().all()
            .spec(getBaseSpec())
            .queryParam("t",track)
            .when()
            .get(GET_ORDER_PATH)
            .then().log().all();
    }
@Step("Запрос на отмену заказа по  номеру  {track}")
    public ValidatableResponse cancel(int track){
        return given().log().all()
                .spec(getBaseSpec())
                .body(track)
                .when()
                .put(CREATE_ORDER_PATH)
                .then().log().all();
    }

}
