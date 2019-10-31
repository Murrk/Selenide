package ru.netology.webSelenide;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class webSelenide {
    @Test
    @DisplayName("Все поля заполнены верно")
    void shouldTestPositive() {
        Calendar c = new GregorianCalendar();
        c.add(Calendar.DAY_OF_YEAR, 3);
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        String dateOfMeeting = format.format(c.getTime());

        open("http://localhost:9999");
        $("[data-test-id='city'] input.input__control").setValue("Новосибирск");
        $("[data-test-id='date'] input.input__control").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id='date'] input.input__control").setValue(dateOfMeeting);
        $("[data-test-id='name'] input.input__control").setValue("Пупкин Василий");
        $("[data-test-id='phone'] input.input__control").setValue("+79200000000");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Успешно")).waitUntil(visible, 15000);
    }
    @Test
    @DisplayName("Ввод 2 букв в поле город, после чего выбор нужного города из выпадающего списка")
    void shouldTestSelectionCity() throws InterruptedException {
        open("http://localhost:9999");
        $("[data-test-id='city'] input.input__control").setValue("Но");
        $(".menu").waitUntil(visible, 5000);
        Thread.sleep(3000);
        $$(".menu").last().find(withText("Новосибирск")).click();
        Thread.sleep(3000);
    }
    @Test
    @DisplayName("Выбор даты на неделю вперёд (начиная от текущей даты) через инструмент календаря")
    void shouldTestSelectionDateViaCalendar() throws InterruptedException {
        Calendar c1 = new GregorianCalendar();
        c1.add(Calendar.DAY_OF_YEAR, 7);
        SimpleDateFormat format1 = new SimpleDateFormat("d");
        String dateOfMeeting1 = format1.format(c1.getTime());

        open("http://localhost:9999");
        $("[data-test-id='date'] input.input__control").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $(".calendar").waitUntil(visible, 15000);
        Thread.sleep(3000);
        $$(".calendar__day").find(exactText(dateOfMeeting1)).click();
        Thread.sleep(3000);
    }
}
