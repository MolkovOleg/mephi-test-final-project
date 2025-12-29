package ru.mifi.testing.mobile.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Page Object экрана поиска мобильного приложения Wikipedia.
 *
 * <p>
 * Отвечает за:
 * <ul>
 *     <li>ввод поискового запроса</li>
 *     <li>получение результатов поиска</li>
 *     <li>переход к статье из результатов</li>
 * </ul>
 * </p>
 */
public class SearchPage {

    private final AndroidDriver driver;
    private final WebDriverWait wait;

    /**
     * Поле ввода поискового запроса
     */
    private final By searchInput =
            By.id("org.wikipedia.alpha:id/search_src_text");

    /**
     * Заголовки найденных статей
     */
    private final By searchResults =
            By.id("org.wikipedia.alpha:id/page_list_item_title");

    public SearchPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    /**
     * Ввод поискового запроса
     *
     * @param query текст запроса
     */
    public void enterQuery(String query) {

        wait.until(
                ExpectedConditions.presenceOfElementLocated(searchInput)
        ).sendKeys(query);

        System.out.println("[INFO] Search query entered: " + query);
    }

    /**
     * Получение списка результатов поиска
     *
     * @return список элементов результатов
     */
    public List<WebElement> getResults() {

        wait.until(
                ExpectedConditions.presenceOfElementLocated(searchResults)
        );

        List<WebElement> results = driver.findElements(searchResults);

        System.out.println("[INFO] Search results found: " + results.size());

        return results;
    }

    /**
     * Проверка наличия результатов поиска
     *
     * @return true — если результаты присутствуют
     */
    public boolean hasResults() {

        boolean hasResults = !getResults().isEmpty();

        if (hasResults) {
            System.out.println("[INFO] Search results are present");
        } else {
            System.out.println("[WARN] No search results found");
        }

        return hasResults;
    }

    /**
     * Открывает первый результат поиска
     */
    public void openFirstResult() {

        getResults().get(0).click();

        System.out.println("[INFO] First search result opened");
    }
}
