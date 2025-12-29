package ru.mifi.testing.mobile.tests;

import org.testng.annotations.Test;
import ru.mifi.testing.mobile.base.BaseMobileTest;
import ru.mifi.testing.mobile.pages.ArticlePage;
import ru.mifi.testing.mobile.pages.MainPage;
import ru.mifi.testing.mobile.pages.SearchPage;

import static org.testng.Assert.assertTrue;

/**
 * Тест открытия статьи в мобильном приложении Wikipedia.
 *
 * <p>
 * Проверяем сценарий:
 * <ul>
 *     <li>запуск приложения</li>
 *     <li>пропуск онбординга (если отображается)</li>
 *     <li>переход к поиску</li>
 *     <li>поиск статьи</li>
 *     <li>открытие первой найденной статьи</li>
 *     <li>проверка, что экран статьи действительно открыт</li>
 * </ul>
 * </p>
 *
 * <p>
 * Проверка открытия статьи выполняется через несколько якорей
 * (заголовок, тулбар, кнопка Navigate up),
 * что делает тест более устойчивым.
 * </p>
 */
public class OpenArticleTest extends BaseMobileTest {

    /**
     * Проверка открытия статьи из результатов поиска.
     */
    @Test
    void shouldOpenArticle() {

        System.out.println("[TEST] OpenArticleTest: start");

        MainPage mainPage = new MainPage(driver, wait);
        SearchPage searchPage = new SearchPage(driver);
        ArticlePage articlePage = new ArticlePage(driver, wait);

        System.out.println("[STEP] Skip onboarding if present");
        mainPage.skipOnboardingIfPresent();

        System.out.println("[STEP] Open search screen");
        mainPage.openSearch();

        System.out.println("[STEP] Enter search query: Java");
        searchPage.enterQuery("Java");

        System.out.println("[STEP] Open first search result");
        searchPage.openFirstResult();

        // критический шаг для стабильности теста
        System.out.println("[STEP] Close article popups if present");
        articlePage.closePopupsIfPresent();

        System.out.println("[STEP] Verify article is opened");
        assertTrue(
                articlePage.isOpened(),
                "Статья не открылась"
        );

        System.out.println("[PASS] OpenArticleTest: article successfully opened");
    }
}
