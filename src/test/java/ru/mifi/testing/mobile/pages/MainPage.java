package ru.mifi.testing.mobile.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page Object главного экрана мобильного приложения Wikipedia.
 *
 * <p>
 * Отвечает за:
 * <ul>
 *     <li>пропуск онбординга</li>
 *     <li>проверку открытия главного экрана</li>
 *     <li>переход к экрану поиска</li>
 * </ul>
 * </p>
 */
public class MainPage {

    private final AndroidDriver driver;
    private final WebDriverWait wait;

    /**
     * Контейнер поиска на главном экране
     */
    private final By searchContainer =
            By.id("org.wikipedia.alpha:id/search_container");

    /**
     * Кнопка Skip в онбординге
     */
    private final By skipButton =
            By.id("org.wikipedia.alpha:id/fragment_onboarding_skip_button");

    public MainPage(AndroidDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    /**
     * Пропуск онбординга, если он отображается.
     * <p>
     * Метод безопасен: если онбординга нет — ничего не делает.
     * </p>
     */
    public void skipOnboardingIfPresent() {

        if (!driver.findElements(skipButton).isEmpty()) {
            driver.findElement(skipButton).click();
            System.out.println("[INFO] Onboarding skipped");
        } else {
            System.out.println("[INFO] Onboarding not present");
        }
    }

    /**
     * Проверка, что главный экран открыт
     *
     * @return true — если главный экран успешно загружен
     */
    public boolean isOpened() {

        boolean opened = wait.until(
                ExpectedConditions.presenceOfElementLocated(searchContainer)
        ).isDisplayed();

        if (opened) {
            System.out.println("[INFO] Main page opened");
        }

        return opened;
    }

    /**
     * Открывает экран поиска
     */
    public void openSearch() {

        wait.until(
                ExpectedConditions.presenceOfElementLocated(searchContainer)
        ).click();

        System.out.println("[INFO] Search screen opened");
    }
}
