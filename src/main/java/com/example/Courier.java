package com.example;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

@Data
@Builder
public class Courier {
    private String login;
    private String password;
    private String firstName;

    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }
    @Step("Создание рандомного курьера")
    public static Courier getRandom(){
        final String login= RandomStringUtils.randomAlphabetic(10);
        final String password=RandomStringUtils.randomAlphabetic(10);
        final String firstName=RandomStringUtils.randomAlphabetic(10);
        Allure.addAttachment("login", login);
        Allure.addAttachment("password", password);
        Allure.addAttachment("firstname", firstName);
        return new Courier(login,password,firstName);
    }

}
