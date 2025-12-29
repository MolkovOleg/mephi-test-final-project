package ru.mifi.testing.mobile.tests;

import org.testng.annotations.Test;
import ru.mifi.testing.mobile.base.BaseMobileTest;
import ru.mifi.testing.mobile.pages.ArticlePage;
import ru.mifi.testing.mobile.pages.MainPage;
import ru.mifi.testing.mobile.pages.SearchPage;

import static org.testng.Assert.assertTrue;

/**
 * Тест прокрутки статьи в мобильном приложении Wikipedia.
 *
 * <p>
 * Сценарий теста:
 * <ul>
 *     <li>запуск приложения</li>
 *     <li>пропуск онбординга (если есть)</li>
 *     <li>поиск статьи</li>
 *     <li>открытие первой статьи из результатов</li>
 *     <li>прокрутка экрана статьи вниз</li>
 *     <li>проверка, что контент статьи изменился после прокрутки</li>
 * </ul>
 * </p>
 *
 * <p>
 * Проверка прокрутки выполняется через сравнение DOM-состояния
 * (page source) до и после жеста scroll.
 * Такой подход позволяет убедиться, что экран действительно
 * отреагировал на жест, даже если якоря остаются на экране.
 * </p>
 */
public class ScrollArticleTest extends BaseMobileTest {

    /**
     * Проверка прокрутки статьи вниз.
     */
    @Test
    void shouldScrollArticle() {

        System.out.println("[TEST] ScrollArticleTest: start");

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

        System.out.println("[STEP] Close article popups if present");
        articlePage.closePopupsIfPresent();

        System.out.println("[STEP] Capture page source before scroll");
        String beforeScroll = driver.getPageSource();

        System.out.println("[STEP] Perform scroll down gesture");
        articlePage.scrollDown();

        System.out.println("[STEP] Capture page source after scroll");
        String afterScroll = driver.getPageSource();

        System.out.println("[STEP] Verify article content changed after scroll");
        assertTrue(
                !beforeScroll.equals(afterScroll),
                "Контент статьи не изменился после прокрутки"
        );

        System.out.println("[PASS] ScrollArticleTest: article successfully scrolled");
    }
}
