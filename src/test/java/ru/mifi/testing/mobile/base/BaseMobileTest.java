package ru.mifi.testing.mobile.base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import ru.mifi.testing.mobile.config.MobileTestConfig;

import java.net.URL;
import java.time.Duration;

/**
 * Базовый класс для всех мобильных автотестов.
 *
 * <p>
 * Отвечает за:
 * <ul>
 *     <li>инициализацию AndroidDriver через Appium</li>
 *     <li>настройку UiAutomator2 capabilities</li>
 *     <li>создание WebDriverWait</li>
 *     <li>корректное завершение сессии после теста</li>
 * </ul>
 * </p>
 *
 * <p>
 * Все мобильные тесты должны наследоваться от этого класса.
 * </p>
 */
public abstract class BaseMobileTest {

    /**
     * AndroidDriver — основной объект взаимодействия с мобильным приложением
     */
    protected AndroidDriver driver;

    /**
     * Явные ожидания для мобильных элементов
     */
    protected WebDriverWait wait;

    /**
     * Инициализация Appium-сессии перед каждым тестом
     */
    @BeforeMethod
    public void setUp() throws Exception {

        System.out.println("[INFO] Starting mobile test setup");

        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName(MobileTestConfig.getPlatformName())
                .setAutomationName(MobileTestConfig.getAutomationName())
                .setDeviceName(MobileTestConfig.getDeviceName())
                .setPlatformVersion(MobileTestConfig.getPlatformVersion())
                .setAppPackage(MobileTestConfig.getAppPackage())
                .setAppActivity(MobileTestConfig.getAppActivity())
                .setNoReset(MobileTestConfig.isNoReset())
                .setAutoGrantPermissions(MobileTestConfig.isAutoGrantPermissions())
                .setNewCommandTimeout(Duration.ofSeconds(
                        MobileTestConfig.getNewCommandTimeout()
                ));

        System.out.println("[INFO] Appium capabilities configured");

        driver = new AndroidDriver(
                new URL(MobileTestConfig.getAppiumServerUrl()),
                options
        );

        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        System.out.println("[INFO] Appium session started successfully");
    }

    /**
     * Завершение Appium-сессии после каждого теста
     */
    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        System.out.println("[INFO] Tearing down mobile test");

        if (driver != null) {
            driver.quit();
            System.out.println("[INFO] Appium session closed");
        }
    }
}
