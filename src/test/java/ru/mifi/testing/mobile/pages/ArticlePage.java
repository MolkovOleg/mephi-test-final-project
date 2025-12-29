package ru.mifi.testing.mobile.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Page Object экрана статьи мобильного приложения Wikipedia.
 *
 * <p>
 * Отвечает за:
 * <ul>
 *     <li>закрытие всплывающих окон</li>
 *     <li>проверку открытия статьи</li>
 *     <li>прокрутку содержимого статьи</li>
 *     <li>проверку доступности статьи после скролла</li>
 * </ul>
 * </p>
 */
public class ArticlePage {

    private final AndroidDriver driver;
    private final WebDriverWait wait;

    // ===== POPUPS =====

    /** Кнопка закрытия всплывающего окна Wikipedia Games */
    private final By gamesPopupCloseButton =
            By.id("org.wikipedia.alpha:id/closeButton");

    /** Кнопка "Got it" в окне Customize Toolbar */
    private final By customizeToolbarGotItButton =
            By.id("org.wikipedia.alpha:id/positiveButton");

    // ===== ARTICLE ANCHORS =====

    /** Заголовок статьи */
    private final By articleTitle =
            By.id("org.wikipedia.alpha:id/view_page_title_text");

    /** Верхняя панель статьи */
    private final By articleToolbar =
            By.id("org.wikipedia.alpha:id/view_page_header");

    /** Кнопка навигации "Назад" */
    private final By navigateUpButton =
            AppiumBy.accessibilityId("Navigate up");

    /** WebView с содержимым статьи */
    private final By articleWebView =
            By.id("org.wikipedia.alpha:id/page_web_view");

    /**
     * Конструктор страницы статьи.
     * Сигнатура сохранена для совместимости с тестами.
     */
    public ArticlePage(AndroidDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    // ===== POPUPS =====

    /**
     * Закрывает всплывающие окна, если они отображаются.
     * <p>
     * Метод безопасный — при отсутствии popup просто продолжается выполнение.
     * </p>
     */
    public void closePopupsIfPresent() {

        try {
            new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(ExpectedConditions.visibilityOfElementLocated(gamesPopupCloseButton))
                    .click();
            System.out.println("[INFO] Wikipedia Games popup closed");
        } catch (Exception ignored) {
            // popup может отсутствовать — это допустимо
        }

        try {
            new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(ExpectedConditions.visibilityOfElementLocated(customizeToolbarGotItButton))
                    .click();
            System.out.println("[INFO] Customize Toolbar popup closed");
        } catch (Exception ignored) {
            // popup может отсутствовать — это допустимо
        }
    }

    // ===== ARTICLE OPEN CHECK =====

    /**
     * Проверяет, что статья успешно открыта.
     *
     * <p>
     * Проверка выполняется по нескольким "якорям":
     * заголовок, toolbar или кнопка Navigate Up.
     * </p>
     *
     * @return true — если хотя бы один якорь найден
     */
    public boolean isOpened() {

        closePopupsIfPresent();

        if (isElementPresent(articleTitle)) {
            System.out.println("[INFO] Article opened: TITLE visible");
            return true;
        }

        if (isElementPresent(articleToolbar)) {
            System.out.println("[INFO] Article opened: TOOLBAR visible");
            return true;
        }

        if (isElementPresent(navigateUpButton)) {
            System.out.println("[INFO] Article opened: NAVIGATE UP button visible");
            return true;
        }

        System.out.println("[WARN] Article anchors not found");
        return false;
    }

    // ===== AFTER SCROLL CHECK =====

    /**
     * Проверяет, что статья доступна после прокрутки.
     *
     * @return true — если статья по-прежнему доступна
     */
    public boolean isArticleAvailable() {

        if (isElementPresent(articleWebView)) {
            System.out.println("[INFO] Article available: WebView present");
            return true;
        }

        if (isElementPresent(navigateUpButton)) {
            System.out.println("[INFO] Article available: Navigate Up visible");
            return true;
        }

        if (isElementPresent(articleTitle)) {
            System.out.println("[INFO] Article available: Title visible");
            return true;
        }

        System.out.println("[WARN] Article not available after scroll");
        return false;
    }

    /**
     * Универсальная проверка наличия элемента на экране.
     */
    private boolean isElementPresent(By locator) {
        List<WebElement> elements = driver.findElements(locator);
        return !elements.isEmpty();
    }

    // ===== SCROLL =====

    /**
     * Выполняет вертикальную прокрутку статьи вниз.
     */
    public void scrollDown() {

        int height = driver.manage().window().getSize().height;
        int width  = driver.manage().window().getSize().width;

        int startX = width / 2;
        int startY = (int) (height * 0.7);
        int endY   = (int) (height * 0.3);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence scroll = new Sequence(finger, 1);

        scroll.addAction(finger.createPointerMove(
                Duration.ZERO,
                PointerInput.Origin.viewport(),
                startX, startY
        ));
        scroll.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        scroll.addAction(finger.createPointerMove(
                Duration.ofMillis(800),
                PointerInput.Origin.viewport(),
                startX, endY
        ));
        scroll.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(List.of(scroll));

        System.out.println("[INFO] Article scrolled down");
    }
}
