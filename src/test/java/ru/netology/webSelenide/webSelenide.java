package ru.netology.webSelenide;

import org.junit.jupiter.api.BeforeEach;
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
    private String serviceUrl = "http://localhost:9999";
    private String cityInputCss = "[data-test-id='city'] input.input__control";
    private String nameInputCss = "[data-test-id='name'] input.input__control";
    private String dateInputCss = "[data-test-id='date'] input.input__control";
    private String phoneInputCss = "[data-test-id='phone'] input.input__control";
    private String checkBoxCss = "[data-test-id=agreement]";
    private String submitButtonTag = "button";
    private String menuCss = ".menu";
    private String calendarCss = ".calendar";
    private String calendarDayCss = ".calendar__day";

    @Test
    @DisplayName("Все поля заполнены верно")
    void shouldTestPositive() {
        Calendar c = new GregorianCalendar();
        c.add(Calendar.DAY_OF_YEAR, 3);
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        String dateOfMeeting = format.format(c.getTime());

        open(serviceUrl);
        $(cityInputCss).setValue("Новосибирск");
        $(dateInputCss).sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $(dateInputCss).setValue(dateOfMeeting);
        $(nameInputCss).setValue("Пупкин Василий");
        $(phoneInputCss).setValue("+79200000000");
        $(checkBoxCss).click();
        $$(submitButtonTag).find(exactText("Забронировать")).click();
        $(withText("Успешно")).waitUntil(visible, 15000);
    }
    @Test
    @DisplayName("Ввод 2 букв в поле город, после чего выбор нужного города из выпадающего списка")
    void shouldTestSelectionCity() throws InterruptedException {
        open(serviceUrl);
        $(cityInputCss).setValue("Но");
        $(menuCss).waitUntil(visible, 5000);
        Thread.sleep(2000);
        $$(menuCss).last().find(withText("Новосибирск")).click();
        Thread.sleep(2000);
    }
    @Test
    @DisplayName("Выбор даты на неделю вперёд (начиная от текущей даты) через инструмент календаря")
    void shouldTestSelectionDateViaCalendar() throws InterruptedException {
        Calendar c1 = new GregorianCalendar();
        c1.add(Calendar.DAY_OF_YEAR, 7);
        SimpleDateFormat format1 = new SimpleDateFormat("d");
        String dateOfMeeting1 = format1.format(c1.getTime());

        open(serviceUrl);
        $(dateInputCss).sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $(calendarCss).waitUntil(visible, 5000);
        Thread.sleep(3000);
        $$(calendarDayCss).find(exactText(dateOfMeeting1)).click();
        Thread.sleep(3000);
    }
}
