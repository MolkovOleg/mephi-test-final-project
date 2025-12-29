package ru.mifi.testing.mobile.config;

import java.io.InputStream;
import java.util.Properties;

/**
 * Конфигурационный класс для мобильных автотестов.
 *
 * <p>
 * Отвечает за загрузку параметров Appium и Android-окружения
 * из файла {@code mobile-test.properties}, расположенного в {@code resources}.
 * </p>
 *
 * <p>
 * Используется базовыми тестами для:
 * <ul>
 *     <li>настройки Appium-сессии</li>
 *     <li>указания устройства / эмулятора</li>
 *     <li>параметров запуска приложения</li>
 * </ul>
 * </p>
 *
 * <p>
 * Реализован как утилитарный класс со статическими методами,
 * что упрощает использование в тестах и базовых классах.
 * </p>
 */
public class MobileTestConfig {

    /**
     * Загруженные свойства мобильного тестового окружения
     */
    private static final Properties properties = new Properties();

    /**
     * Статический блок загрузки конфигурации.
     * Выполняется один раз при первом обращении к классу.
     */
    static {
        try (InputStream is = MobileTestConfig.class
                .getClassLoader()
                .getResourceAsStream("mobile-test.properties")) {

            if (is == null) {
                throw new RuntimeException("mobile-test.properties not found in resources");
            }

            properties.load(is);
            System.out.println("[INFO] Mobile test configuration loaded successfully");

        } catch (Exception e) {
            throw new RuntimeException("Failed to load mobile-test.properties", e);
        }
    }

    /**
     * @return URL Appium-сервера (например, http://127.0.0.1:4723)
     */
    public static String getAppiumServerUrl() {
        return properties.getProperty("mobile.appium.server.url");
    }

    /**
     * @return Название платформы (обычно ANDROID)
     */
    public static String getPlatformName() {
        return properties.getProperty("mobile.platformName");
    }

    /**
     * @return Версия Android (например, 13.0)
     */
    public static String getPlatformVersion() {
        return properties.getProperty("mobile.platformVersion");
    }

    /**
     * @return Имя устройства или эмулятора
     */
    public static String getDeviceName() {
        return properties.getProperty("mobile.deviceName");
    }

    /**
     * @return Название automation framework (UiAutomator2)
     */
    public static String getAutomationName() {
        return properties.getProperty("mobile.automationName");
    }

    /**
     * @return Package name тестируемого приложения
     */
    public static String getAppPackage() {
        return properties.getProperty("mobile.appPackage");
    }

    /**
     * @return Activity, с которой стартует приложение
     */
    public static String getAppActivity() {
        return properties.getProperty("mobile.appActivity");
    }

    /**
     * @return true — если Appium не должен сбрасывать состояние приложения
     */
    public static boolean isNoReset() {
        return Boolean.parseBoolean(
                properties.getProperty("mobile.noReset", "true")
        );
    }

    /**
     * @return true — если разрешения приложения выдаются автоматически
     */
    public static boolean isAutoGrantPermissions() {
        return Boolean.parseBoolean(
                properties.getProperty("mobile.autoGrantPermissions", "true")
        );
    }

    /**
     * @return таймаут ожидания новых команд Appium (в секундах)
     */
    public static int getNewCommandTimeout() {
        return Integer.parseInt(
                properties.getProperty("mobile.newCommandTimeout", "180")
        );
    }
}
