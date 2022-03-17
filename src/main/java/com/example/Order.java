package com.example;
import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import lombok.Builder;
import lombok.Data;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Data
@Builder
public class Order {
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private Date deliveryDate;
    private String comment;
    private List<String> color;
    //добавил библиотеку  https://github.com/DiUS/java-faker  для проверки функционала по генерации рандомных значений
    public static Faker faker=new Faker(new Locale("ru"));

    public Order(String firstName, String lastName, String address, String metroStation,String phone, int rentTime, Date deliveryDate, String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }
    @Step("Создание нового заказа с рандомными данными за исключением цвета")
    public static Order getRandomOrder(List<String> colors){
        String firstName=faker.name().firstName();
        String lastName=faker.name().lastName();
        String address=faker.address().streetAddress();
        String metroStation=faker.address().streetName();
        String phone=faker.phoneNumber().phoneNumber();
        int rentTime=faker.number().numberBetween(0,7);
        Date deliveryDate=faker.date().future(7, TimeUnit.DAYS);
        String comment=faker.programmingLanguage().name();
        List<String> color=colors;
        return new Order(firstName,lastName,address,metroStation,phone,rentTime,deliveryDate,comment,color);


    }
}
