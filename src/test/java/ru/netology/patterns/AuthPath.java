package ru.netology.patterns;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.patterns.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.patterns.DataGenerator.Registration.getUser;

public class AuthPath {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSuccessIfUserRegisteredAndLoginPasswordCorrect() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id=login] input").setValue(registeredUser.getLogin());
        $("[data-test-id=password] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("h2").shouldHave(Condition.exactText("Личный кабинет")).shouldBe(Condition.visible);
    }

    @Test
    void shouldErrorIfUserNotRegistered() {
        var notRegisteredUser = getUser("active");
        $("[data-test-id=login] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id=password] input").setValue(notRegisteredUser.getPassword());
        $("button.button").click();
        $(".notification__content").shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }

    @Test
    void shouldErrorIfUserRegisteredButPasswordIncorrect() {
        var registeredUser = getRegisteredUser("active");
        var notRegisteredUser = getUser("active");
        $("[data-test-id=login] input").setValue(registeredUser.getLogin());
        $("[data-test-id=password] input").setValue(notRegisteredUser.getPassword());
        $("button.button").click();
        $(".notification__content").shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }

    @Test
    void shouldErrorIfUserRegisteredButLoginIncorrect() {
        var registeredUser = getRegisteredUser("active");
        var notRegisteredUser = getUser("active");
        $("[data-test-id=login] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id=password] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $(".notification__content").shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }

    @Test
    void shouldErrorIfUserBlocked() {
        var blockedUser = getRegisteredUser("blocked");
        $("[data-test-id=login] input").setValue(blockedUser.getLogin());
        $("[data-test-id=password] input").setValue(blockedUser.getPassword());
        $("button.button").click();
        $(".notification__content").shouldHave(Condition.exactText("Ошибка! Пользователь заблокирован")).shouldBe(Condition.visible);
    }
}