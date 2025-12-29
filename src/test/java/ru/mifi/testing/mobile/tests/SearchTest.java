package ru.mifi.testing.mobile.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.mifi.testing.mobile.base.BaseMobileTest;
import ru.mifi.testing.mobile.pages.MainPage;
import ru.mifi.testing.mobile.pages.SearchPage;

/**
 * Тест поиска статьи в мобильном приложении Wikipedia.
 *
 * <p>
 * Проверяем сценарий:
 * <ul>
 *     <li>пропуск онбординга (если есть)</li>
 *     <li>переход на экран поиска</li>
 *     <li>ввод поискового запроса</li>
 *     <li>отображение результатов поиска</li>
 * </ul>
 * </p>
 *
 * <p>
 * Является базовым функциональным тестом поиска.
 * </p>
 */
public class SearchTest extends BaseMobileTest {

    /**
     * Проверка поиска статьи по ключевому слову.
     */
    @Test
    public void shouldSearchArticle() {

        System.out.println("[TEST] SearchTest: start");

        MainPage mainPage = new MainPage(driver, wait);
        SearchPage searchPage = new SearchPage(driver);

        System.out.println("[STEP] Skip onboarding if present");
        mainPage.skipOnboardingIfPresent();

        System.out.println("[STEP] Open search screen");
        mainPage.openSearch();

        System.out.println("[STEP] Enter search query: Java");
        searchPage.enterQuery("Java");

        System.out.println("[STEP] Verify search results are present");
        Assert.assertTrue(
                searchPage.hasResults(),
                "Результаты поиска не найдены"
        );

        System.out.println("[PASS] SearchTest: search results displayed");
    }
}
