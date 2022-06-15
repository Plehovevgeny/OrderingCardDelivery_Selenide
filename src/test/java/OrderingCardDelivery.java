import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class OrderingCardDelivery {

    public String dateOfCreation(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @BeforeEach
    void setUp() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
    }

    @Test
    void shouldUsualDay() {
        String dateOfMeet = dateOfCreation(3);
        $x("//input[@placeholder=\"Город\"]").val("Екатеринбург");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $x("//input[@placeholder=\"Дата встречи\"]").val(dateOfMeet);
        $x("//input[@name=\"name\"]").val("Плеханова Александра");
        $x("//input[@name=\"phone\"]").val("+79221341234");
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//span[@class=\"button__text\"]").click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + dateOfMeet), Duration.ofSeconds(15));
    }

    @Test
    void shouldNextDay() {
        String dateOfMeet = dateOfCreation(4);
        $x("//input[@placeholder=\"Город\"]").val("Екатеринбург");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $x("//input[@placeholder=\"Дата встречи\"]").val(dateOfMeet);
        $x("//input[@name=\"name\"]").val("Плеханова Александра");
        $x("//input[@name=\"phone\"]").val("+79221341234");
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//span[@class=\"button__text\"]").click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + dateOfMeet), Duration.ofSeconds(15));
    }

    @Test
    void shouldPrevDayBadDay() {
        String dateOfMeet = dateOfCreation(2);
        $x("//input[@placeholder=\"Город\"]").val("Екатеринбург");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $x("//input[@placeholder=\"Дата встречи\"]").val(dateOfMeet);
        $x("//input[@name=\"name\"]").val("Плеханова Александра");
        $x("//input[@name=\"phone\"]").val("+79221341234");
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//span[@class=\"button__text\"]").click();
        $x("//span[text()=\"Заказ на выбранную дату невозможен\"]")
                .should(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldNoDay() {
        String dateOfMeet = dateOfCreation(2);
        $x("//input[@placeholder=\"Город\"]").val("Екатеринбург");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $x("//input[@placeholder=\"Дата встречи\"]").val();
        $x("//input[@name=\"name\"]").val("Плеханова Александра");
        $x("//input[@name=\"phone\"]").val("+79221341234");
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//span[@class=\"button__text\"]").click();
        $x("//span[(text()=\"Неверно введена дата\")]")
                .should(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldBadCity() {
        String dateOfMeet = dateOfCreation(3);
        $x("//input[@placeholder=\"Город\"]").val("огурец");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $x("//input[@placeholder=\"Дата встречи\"]").val(dateOfMeet);
        $x("//input[@name=\"name\"]").val("Плеханова Александра");
        $x("//input[@name=\"phone\"]").val("+79221341234");
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//span[@class=\"button__text\"]").click();
        $x("//span[text()=\"Доставка в выбранный город недоступна\"]").should(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldBadName() {
        String dateOfMeet = dateOfCreation(3);
        $x("//input[@placeholder=\"Город\"]").val("Екатеринбург");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $x("//input[@placeholder=\"Дата встречи\"]").val(dateOfMeet);
        $x("//input[@name=\"name\"]").val("Plehanova Alex");
        $x("//input[@name=\"phone\"]").val("+79221341234");
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//span[@class=\"button__text\"]").click();
        $x("//span[text()=\"Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.\"]")
                .should(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldBadPhone() {
        String dateOfMeet = dateOfCreation(3);
        $x("//input[@placeholder=\"Город\"]").val("Екатеринбург");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $x("//input[@placeholder=\"Дата встречи\"]").val(dateOfMeet);
        $x("//input[@name=\"name\"]").val("Плеханова Александра");
        $x("//input[@name=\"phone\"]").val("+7922134123");
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//span[@class=\"button__text\"]").click();
        $x("//span[text()=\"Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.\"]")
                .should(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldNoCity() {
        String dateOfMeet = dateOfCreation(3);
        $x("//input[@placeholder=\"Город\"]").val("");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $x("//input[@placeholder=\"Дата встречи\"]").val(dateOfMeet);
        $x("//input[@name=\"name\"]").val("Плеханова Александра");
        $x("//input[@name=\"phone\"]").val("+79221341234");
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//span[@class=\"button__text\"]").click();
        $x("//span[(text()=\"Поле обязательно для заполнения\")]").should(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldBNoName() {
        String dateOfMeet = dateOfCreation(3);
        $x("//input[@placeholder=\"Город\"]").val("Екатеринбург");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $x("//input[@placeholder=\"Дата встречи\"]").val(dateOfMeet);
        $x("//input[@name=\"name\"]").val("");
        $x("//input[@name=\"phone\"]").val("+79221341234");
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//span[@class=\"button__text\"]").click();
        $x("//span[(text()=\"Поле обязательно для заполнения\")]").should(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldNoPhone() {
        String dateOfMeet = dateOfCreation(3);
        $x("//input[@placeholder=\"Город\"]").val("Екатеринбург");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $x("//input[@placeholder=\"Дата встречи\"]").val(dateOfMeet);
        $x("//input[@name=\"name\"]").val("Плеханова Александра");
        $x("//input[@name=\"phone\"]").val("");
        $x("//span[@class=\"checkbox__box\"]").click();
        $x("//span[@class=\"button__text\"]").click();
        $x("//span[(text()=\"Поле обязательно для заполнения\")]").should(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldNoCheckbox() {
        String dateOfMeet = dateOfCreation(3);
        $x("//input[@placeholder=\"Город\"]").val("Екатеринбург");
        $x("//input[@placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.BACK_SPACE);
        $x("//input[@placeholder=\"Дата встречи\"]").val(dateOfMeet);
        $x("//input[@name=\"name\"]").val("Плеханова Александра");
        $x("//input[@name=\"phone\"]").val("+79221341234");
        $x("//span[@class=\"button__text\"]").click();
        $x("//span[(text()=\"Я соглашаюсь с условиями обработки и использования моих персональных данных\")]")
                .should(visible, Duration.ofSeconds(15));
    }
}
