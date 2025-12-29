package ru.mifi.testing.web.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.mifi.testing.web.base.BaseWebTest;
import ru.mifi.testing.web.pages.WeatherMainPage;

/**
 * Сценарий 3 — навигация через главное меню Яндекс.Погоды.
 *
 * Цель теста:
 *  - проверить корректность навигации по пункту меню «Карта осадков»
 *  - убедиться, что пользователь попадает на соответствующую страницу
 *
 * Проверяется:
 *  - переход по меню (реальный пользовательский сценарий)
 *  - корректность URL
 *  - корректность заголовка страницы
 *
 * Сценарий считается стабильным и используется
 * как основной навигационный тест.
 */
public class NavigationMenuTest extends BaseWebTest {

    @Test
    public void shouldOpenPrecipitationMapFromMenu() {

        System.out.println("[INFO] Starting test: NavigationMenuTest");

        WeatherMainPage page = new WeatherMainPage(driver);

        // 1. Открываем страницу погоды для Москвы
        System.out.println("[STEP] Open weather page for Moscow");
        page.openPage(baseUrl + "/ru/moscow");

        // 2. Переходим в раздел «Карта осадков» через меню
        System.out.println("[STEP] Navigate to precipitation map via menu");
        page.openPrecipitationMap();

        // 3. Проверяем URL страницы
        System.out.println("[CHECK] Verify URL contains precipitation map path");
        Assert.assertTrue(
                page.getCurrentUrl().contains("/maps/nowcast"),
                "URL не содержит путь карты осадков"
        );

        // 4. Проверяем заголовок страницы
        System.out.println("[CHECK] Verify page title");
        Assert.assertTrue(
                page.getPageTitle().contains("Карта осадков")
                        || page.getPageTitle().contains("Осадки"),
                "Заголовок страницы не соответствует карте осадков"
        );

        System.out.println("[INFO] NavigationMenuTest finished successfully");
    }
}
