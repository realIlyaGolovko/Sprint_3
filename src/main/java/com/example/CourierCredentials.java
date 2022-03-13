package com.example;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourierCredentials {
    private  String login;
    private  String password;
    public CourierCredentials() {
    }

    public CourierCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }
    public static CourierCredentials from (Courier courier){
        return new CourierCredentials(courier.getLogin(), courier.getPassword());
    }

}
