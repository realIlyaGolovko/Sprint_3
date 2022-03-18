package com.example;
import io.qameta.allure.Step;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourierCredentials {
    private  String login;
    private  String password;

    public CourierCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }
    @Step("Получение нового логина-пароля из курьера")
    public static CourierCredentials from (Courier courier){
        return new CourierCredentials(courier.getLogin(), courier.getPassword());
    }

}
