package ru.netology.test;

import com.codeborne.selenide.Condition;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataGenerator.Registration.*;
import static ru.netology.data.DataGenerator.getRandomLogin;
import static ru.netology.data.DataGenerator.getRandomPassword;

class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Successfully login with active user")
    void shouldSuccessfullyLoginIfRegisteredUserActive() {
        val registeredUser = getRegisteredUser("active");
        $("[type='text']").setValue(registeredUser.getLogin());
        $("[type='password']").setValue(registeredUser.getPassword());
        $("[type='button']").click();
        $(withText("Личный кабинет")).shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    @DisplayName("Error if user is unregistered")
    void shouldGetErrorIfUserNotRegistered() {
        $("[type='text']").setValue(DataGenerator.getRandomLogin());
        $("[type='password']").setValue(DataGenerator.getRandomPassword());
        $("[type='button']").click();
        $(withText("Ошибка")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    @DisplayName("Error if user is blocked")
    void shouldGetErrorIfUserBlocked() {
        val blockedUser = getUser("blocked");
        $("[type='text']").setValue(blockedUser.getLogin());
        $("[type='password']").setValue(blockedUser.getPassword());
        $("[type='button']").click();
        $(withText("Ошибка")).shouldBe(visible, Duration.ofSeconds(15));

    }

    @Test
    @DisplayName("Error message if wrong login data")
    void shouldGetErrorIfWrongLoginData() {
        val registeredUser = getRegisteredUser("active");
        val wrongLogin = getRandomLogin();
        $("[type='text']").setValue(wrongLogin);
        $("[type='password']").setValue(registeredUser.getPassword());
        $("[type='button']").click();
        $(withText("Ошибка")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    @DisplayName("Error if wrong password data")
    void shouldGetErrorIfWrongPasswordData() {
        val registeredUser = getRegisteredUser("active");
        val wrongPassword = getRandomPassword();
        $("[type='text']").setValue(registeredUser.getLogin());
        $("[type='password']").setValue(wrongPassword);
        $("[type='button']").click();
        $(withText("Ошибка")).shouldBe(visible, Duration.ofSeconds(15));
    }
}

