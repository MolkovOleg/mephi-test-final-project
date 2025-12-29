package ru.mifi.testing.web.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.mifi.testing.web.base.BaseWebTest;
import ru.mifi.testing.web.pages.WeatherMainPage;

/**
 * Сценарий 1 — базовый (Smoke test).
 *
 * Цель теста:
 *  - проверить, что главная страница Яндекс.Погоды
 *    корректно открывается по базовому URL
 *
 * Проверяется:
 *  - загрузка страницы
 *  - корректность заголовка (title)
 *
 * Тип сценария:
 *  - smoke
 *  - базовая доступность сервиса
 */
public class OpenWeatherPageTest extends BaseWebTest {

    @Test
    public void shouldOpenYandexWeatherMainPage() {

        System.out.println("[INFO] Starting test: OpenWeatherPageTest");

        WeatherMainPage weatherMainPage = new WeatherMainPage(driver);

        // 1. Открываем главную страницу Яндекс.Погоды
        System.out.println("[STEP] Open Yandex Weather main page");
        weatherMainPage.openPage(baseUrl);

        // 2. Проверяем заголовок страницы
        System.out.println("[CHECK] Verify page title contains 'Погода'");
        String pageTitle = weatherMainPage.getPageTitle();
        System.out.println("[INFO] Page title: " + pageTitle);

        Assert.assertTrue(
                pageTitle.contains("Погода"),
                "Заголовок страницы не содержит слово 'Погода'"
        );

        System.out.println("[INFO] OpenWeatherPageTest finished successfully");
    }
}
