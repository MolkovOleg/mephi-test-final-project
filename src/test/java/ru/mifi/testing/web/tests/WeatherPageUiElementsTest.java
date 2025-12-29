package ru.mifi.testing.web.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.mifi.testing.web.base.BaseWebTest;
import ru.mifi.testing.web.pages.WeatherMainPage;

/**
 * Сценарий 5 — UI-тест главной страницы Яндекс.Погоды.
 *
 * Цель теста:
 *  - проверить наличие основных UI-элементов главной страницы
 *
 * Проверяется:
 *  - заголовок страницы
 *  - строка поиска города
 *  - отображение текущего города
 *  - навигационное меню
 *  - основной блок погоды
 *  - футтер и SEO-блок
 *
 * Тип сценария:
 *  - UI smoke / regression
 *  - визуальная целостность страницы
 */
public class WeatherPageUiElementsTest extends BaseWebTest {

    @Test
    public void shouldDisplayMainUiElementsOnWeatherPage() {

        System.out.println("[INFO] Starting test: WeatherPageUiElementsTest");

        WeatherMainPage page = new WeatherMainPage(driver);

        // 1. Открываем главную страницу
        System.out.println("[STEP] Open Yandex Weather main page");
        page.openPage(baseUrl);

        // 2. Проверяем title
        System.out.println("[CHECK] Verify page title contains 'Погода'");
        String title = page.getPageTitle();
        System.out.println("[INFO] Page title: " + title);

        Assert.assertTrue(
                title.contains("Погода"),
                "Заголовок страницы не содержит слово 'Погода'"
        );

        // 3. Проверяем строку поиска
        System.out.println("[CHECK] Verify search input is displayed");
        Assert.assertTrue(
                page.isSearchInputDisplayed(),
                "Строка поиска не отображается"
        );

        // 4. Проверяем текущий город
        System.out.println("[CHECK] Verify current city is displayed");
        Assert.assertTrue(
                page.isCurrentCityDisplayed(),
                "Текущий город не отображается"
        );

        // 5. Проверяем навигационное меню
        System.out.println("[CHECK] Verify header navigation menu is displayed");
        Assert.assertTrue(
                page.isHeaderNavigationMenuDisplayed(),
                "Верхнее навигационное меню не отображается"
        );

        // 6. Проверяем основной блок погоды
        System.out.println("[CHECK] Verify current weather block is displayed");
        Assert.assertTrue(
                page.isCurrentWeatherBlockDisplayed(),
                "Блок текущей погоды не отображается"
        );

        // 7. Проверяем футтер
        System.out.println("[CHECK] Verify footer is displayed");
        Assert.assertTrue(
                page.isFooterDisplayed(),
                "Футтер страницы не отображается"
        );

        // 8. Проверяем SEO-блок в футтере
        System.out.println("[CHECK] Verify footer SEO block is displayed");
        Assert.assertTrue(
                page.isFooterSeoTextDisplayed(),
                "SEO-блок страницы не отображается"
        );

        // 9. Проверяем копирайт
        System.out.println("[CHECK] Verify footer copyright is displayed");
        Assert.assertTrue(
                page.isFooterCopyrightDisplayed(),
                "Копирайт страницы не отображается"
        );

        System.out.println("[INFO] WeatherPageUiElementsTest finished successfully");
    }
}
