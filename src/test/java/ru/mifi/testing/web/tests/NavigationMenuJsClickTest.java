package ru.mifi.testing.web.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.mifi.testing.web.base.BaseWebTest;
import ru.mifi.testing.web.pages.WeatherMainPage;

/**
 * Сценарий — навигация через меню с использованием JavaScript-клика.
 *
 * Назначение теста:
 *  - продемонстрировать альтернативный способ клика по элементам SPA
 *    через JavaScript (JavascriptExecutor)
 *  - сравнить поведение JS-клика и обычного Selenium click()
 *
 * ВАЖНО:
 * Тест намеренно отключён (enabled = false), так как:
 *  - Яндекс.Погода является SPA-приложением
 *  - меню может динамически пересоздаваться
 *  - JS-клик может приводить к нестабильным результатам
 *
 * Тест оставлен в проекте:
 *  - в учебных целях
 *  - как reference-подход для сложных DOM-сценариев
 */
public class NavigationMenuJsClickTest extends BaseWebTest {

    @Test(enabled = false)
    public void shouldOpenPrecipitationMapViaJsClick() {

        System.out.println("[INFO] Starting test: NavigationMenuJsClickTest (DISABLED)");

        WeatherMainPage page = new WeatherMainPage(driver);

        // 1. Открываем страницу погоды для Москвы
        System.out.println("[STEP] Open weather page for Moscow");
        page.openPage(baseUrl + "/ru/moscow");

        // 2. Переход в «Карту осадков» через JS-клик
        System.out.println("[STEP] Open precipitation map via JavaScript click");
        page.openPrecipitationMapViaJsClick();

        // 3. Проверка URL
        System.out.println("[STEP] Verify URL contains precipitation map path");
        Assert.assertTrue(
                page.getCurrentUrl().contains("/maps/nowcast"),
                "URL не содержит путь карты осадков"
        );

        // 4. Проверка заголовка страницы
        System.out.println("[STEP] Verify page title");
        Assert.assertTrue(
                page.getPageTitle().contains("Карта осадков")
                        || page.getPageTitle().contains("Осадки"),
                "Заголовок страницы не соответствует карте осадков"
        );

        System.out.println("[INFO] Test finished successfully");
    }
}
