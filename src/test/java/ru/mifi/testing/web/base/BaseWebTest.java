package ru.mifi.testing.web.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import ru.mifi.testing.web.config.WebTestConfig;

/**
 * Базовый класс для всех веб-автотестов.
 *
 * Отвечает за:
 *  - инициализацию WebDriver
 *  - выбор браузера
 *  - настройку ожиданий
 *  - корректное завершение браузера после теста
 *
 * Используется как родительский класс
 * для всех веб-тестов проекта.
 */
public abstract class BaseWebTest {

    /**
     * Экземпляр WebDriver
     */
    protected WebDriver driver;

    /**
     * Явные ожидания для работы с элементами
     */
    protected WebDriverWait wait;

    /**
     * Базовый URL тестируемого сайта
     */
    protected String baseUrl;

    /**
     * Инициализация браузера перед каждым тестом.
     */
    @BeforeMethod
    public void setUp() {

        baseUrl = WebTestConfig.getBaseUrl();
        String browser = WebTestConfig.getBrowser();

        System.out.println("[INFO] Starting web test");
        System.out.println("[INFO] Browser: " + browser);
        System.out.println("[INFO] Base URL: " + baseUrl);

        if ("chrome".equalsIgnoreCase(browser)) {

            WebDriverManager.safaridriver().setup();
            driver = new SafariDriver();

            System.out.println("[INFO] ChromeDriver initialized");

        } else {
            throw new IllegalArgumentException(
                    "Неподдерживаемый браузер: " + browser
            );
        }

        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, WebTestConfig.getTimeout());

        System.out.println("[INFO] Browser window maximized");
        System.out.println("[INFO] WebDriverWait timeout: "
                + WebTestConfig.getTimeout().toSeconds() + " seconds");
    }

    /**
     * Завершение работы браузера после теста.
     */
    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        if (driver != null) {
            System.out.println("[INFO] Closing browser");
            driver.quit();
        }
    }
}
