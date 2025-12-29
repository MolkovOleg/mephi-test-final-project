package ru.mifi.testing.mobile.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.mifi.testing.mobile.base.BaseMobileTest;
import ru.mifi.testing.mobile.pages.MainPage;

/**
 * Тест запуска мобильного приложения Wikipedia.
 *
 * <p>
 * Проверяет базовый сценарий:
 * <ul>
 *     <li>приложение успешно запускается</li>
 *     <li>онбординг пропускается (если присутствует)</li>
 *     <li>открывается главный экран с поиском</li>
 * </ul>
 * </p>
 *
 * <p>
 * Является smoke-тестом мобильного приложения.
 * </p>
 */
public class AppLaunchTest extends BaseMobileTest {

    /**
     * Проверка открытия главного экрана приложения Wikipedia.
     */
    @Test
    public void shouldOpenMainScreen() {

        System.out.println("[TEST] AppLaunchTest: start");

        MainPage mainPage = new MainPage(driver, wait);

        System.out.println("[STEP] Skip onboarding if present");
        mainPage.skipOnboardingIfPresent();

        System.out.println("[STEP] Check main screen is opened");
        Assert.assertTrue(
                mainPage.isOpened(),
                "Главный экран Wikipedia не открыт"
        );

        System.out.println("[PASS] AppLaunchTest: main screen opened successfully");
    }
}
