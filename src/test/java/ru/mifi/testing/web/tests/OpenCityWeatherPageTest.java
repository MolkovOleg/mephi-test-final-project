package ru.mifi.testing.web.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.mifi.testing.web.base.BaseWebTest;
import ru.mifi.testing.web.pages.WeatherMainPage;

/**
 * Сценарий 2 — прямое открытие страницы города.
 *
 * Цель теста:
 *  - проверить, что страница погоды конкретного города
 *    корректно открывается по прямому URL
 *
 * Проверяется:
 *  - корректность URL страницы
 *  - корректное отображение текущего города
 *
 * Тип сценария:
 *  - smoke / navigation
 *  - быстрый и стабильный тест без SPA-интеракций
 */
public class OpenCityWeatherPageTest extends BaseWebTest {

    @Test
    public void shouldOpenMoscowWeatherPage() {

        System.out.println("[INFO] Starting test: OpenCityWeatherPageTest");

        WeatherMainPage weatherMainPage = new WeatherMainPage(driver);

        // 1. Переход напрямую по URL страницы погоды Москвы
        System.out.println("[STEP] Open Moscow weather page via direct URL");
        weatherMainPage.openPage(baseUrl + "/ru/moscow");

        // 2. Проверка URL
        System.out.println("[CHECK] Verify current URL");
        Assert.assertTrue(
                weatherMainPage.getCurrentUrl().contains("/pogoda/ru/moscow"),
                "URL не соответствует странице Москвы"
        );

        // 3. Проверка отображаемого города
        System.out.println("[CHECK] Verify displayed city name");
        Assert.assertEquals(
                weatherMainPage.getCurrentCity(),
                "Москва",
                "Текущий город отображается некорректно"
        );

        System.out.println("[INFO] OpenCityWeatherPageTest finished successfully");
    }
}
