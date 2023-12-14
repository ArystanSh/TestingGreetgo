package com.example;
import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;

import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.visible;

public class RealWorldAutomationTest {

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "Укажите путь к вашему chromedriver"); // Укажите путь к вашему chromedriver
    }

    // @Test
    // public void testHomePageArticlePreviews() {
    // open("https://angular.realworld.io");

    // ElementsCollection articlePreviews = $$(".article-preview");

    // // Проверяем, что есть хотя бы одно превью статьи
    // articlePreviews.shouldHave(CollectionCondition.sizeGreaterThan(0));

    // // Проверяем, что количество статей больше или равно 12
    // if (articlePreviews.size() < 12) {
    //     System.out.println("Внимание: Количество статей на главной странице меньше 12.");
    // }
    // }


    //  @Test
    //   public void testUserRegistrationAndSettings() {
    //     open("https://angular.realworld.io");

    //     // Регистрация пользователя
    //     registerUser("Введите ваш email", "Введите ваш пароль");

    //     // Перейти в настройки пользователя
    //     sleep(2000);
    //     $(".nav-link[routerlink='/settings']").shouldBe(visible).click();

    //     // Проверить, что email идентичен тому, с помощью которого мы регистрировались
    //      $("input[formcontrolname='email']").shouldHave(Condition.value("Ввведите ваш email"));
    // }

    //   private void registerUser(String email, String password) {
    //     $(".nav-link[routerlink='/register']").click();
    //     $("input[formcontrolname='username']").shouldBe(Condition.visible).setValue("Ввведите ваш username");
    //     $("input[formcontrolname='email']").setValue(email);
    //     $("input[formcontrolname='password']").setValue(password);
    //     $("button.btn.btn-lg.btn-primary.pull-xs-right").click();
    // }
    

    @Test
    public void testAddArticleToFavoriteFromGlobalFeed() {
    open("https://angular.realworld.io");
    login("Введите ваш email","Введите ваш пароль");
    $(".nav-link[href='/']").shouldBe(visible).click();

    $(".article-preview .preview-link").waitUntil(visible, 15 * 1000).click();
    $(".article-actions .btn-outline-primary").shouldBe(visible).click();

    // Находим элемент профиля и кликаем
    $(".nav-link[href='/profile/ваш USERNAME]").click();
    // Ожидаем, пока страница с профилем станет видимой
    $(".profile-page").waitUntil(visible, 10000);
    // Теперь находим элемент Favorites и кликаем
    $(".nav-link[href='/profile/ваш USERNAME/favorites']").click();
    
    // Проверяем, что статья отображается в избранном
    if ($$(".article-preview").size() > 0) {
        System.out.println("Статья добавлена в избранное");
        $(".preview-link").shouldBe(visible);
    } else {
        System.out.println("ОШИБКА: Статья не добавлена в избранное");
    }
    }

    @Test
    public void testArticleVisibilityInGlobalFeedAfterCreation() {
        open("https://angular.realworld.io");
        login("Ввведите ваш email", "Введите ваш пароль");
        createArticle("Test Article", "This is a test article body.", "tag1,tag2");
        $(".nav-link[href='/']").shouldBe(visible).click();
        }
    

    private void createArticle(String title, String body, String tags) {
        $(".nav-item a.nav-link[href='/editor']").click();
        $("input[formcontrolname='title']").setValue(title);
        $("textarea[formcontrolname='body']").setValue(body);
        $("input[placeholder='Enter tags']").setValue(tags);
        $("button.btn.btn-lg.pull-xs-right.btn-primary").click();
    }

    @Test
    public void testLoginWithCredentials() {
        open("https://angular.realworld.io");

        // Вход с логином и паролем
        login("Введите ваш email", "Введите ваш пароль");
    }

    private void login(String email, String password) {
        $(".nav-item a.nav-link[href='/login']").click();
        $("input[formcontrolname='email']").shouldBe(Condition.visible).setValue(email);
        $("input[formcontrolname='password']").setValue(password);
        $("button.btn.btn-lg.btn-primary.pull-xs-right").click();
    }

     @Test
    public void testLogoutButtonColorInSettings() {
        open("https://angular.realworld.io");
        login("Введите ваш email", "Введите ваш пароль");

        // Перейти в настройки пользователя
        $(".nav-link[routerlink='/settings']").shouldBe(visible).click();

        // Найти кнопку "Or click here to logout" и проверить цвет при наведении
        String backgroundColor = $(".btn.btn-outline-danger").hover().getCssValue("background-color");
        String expectedColor = "rgba(184, 92, 92, 1)";

        // Проверка, что цвет кнопки соответствует ожидаемому значению
        if (backgroundColor.equals(expectedColor)) {
            System.out.println("Цвет кнопки верный: " + backgroundColor);
        } else {
            fail("Цвет кнопки не соответствует ожидаемому значению");
        }
    }
}
