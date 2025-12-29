package ru.mifi.testing.web.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.mifi.testing.web.base.BaseWebTest;
import ru.mifi.testing.web.pages.WeatherMainPage;

/**
 * Сценарий 4 — поиск города через форму.
 *
 * Цель сценария:
 *  - продемонстрировать пользовательский флоу поиска города
 *    через строку поиска на Яндекс.Погоде
 *  - проверить, что после поиска открывается корректная страница города
 *
 * ВАЖНО:
 * Тест намеренно отключён (enabled = false), так как
 * SPA-механизм suggest на стороне Яндекс.Погоды
 * работает нестабильно для автотестов (динамический DOM,
 * перерисовка элементов, нестабильные классы).
 *
 * Сценарий сохранён в проекте:
 *  - как пример пользовательского поведения
 *  - как заготовка для будущей стабилизации
 */
public class CitySearchViaFormTest extends BaseWebTest {

    @Test(enabled = false)
    public void shouldOpenSaintPetersburgWeatherViaSearch() {

        System.out.println("[INFO] Starting test: CitySearchViaFormTest (DISABLED)");

        WeatherMainPage page = new WeatherMainPage(driver);

        // 1. Открываем главную страницу Яндекс.Погоды
        System.out.println("[STEP] Open main weather page");
        page.openPage(baseUrl);

        // 2. Выполняем поиск города
        System.out.println("[STEP] Search city: Санкт-Петербург");
        page.searchCity("Санкт-Петербург");

        // 3. Проверяем, что URL соответствует странице города
        System.out.println("[STEP] Verify city page URL");
        Assert.assertTrue(
                page.getCurrentUrl().contains("/pogoda/ru/saint-petersburg"),
                "URL не содержит страницу Санкт-Петербурга"
        );

        // 4. Проверяем, что отображаемый город корректен
        System.out.println("[STEP] Verify displayed city name");
        Assert.assertEquals(
                page.getCurrentCity(),
                "Санкт-Петербург",
                "На странице отображается неверный город"
        );

        System.out.println("[INFO] Test finished successfully");
    }
}
