package ru.mifi.testing.web.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;


/**
 * Page Object для страниц Яндекс.Погоды.
 *
 * Содержит:
 *  - работу с поиском города
 *  - навигацию по меню
 *  - проверки основных UI-элементов страницы
 *
 * Используется в веб-автотестах проекта.
 */
public class WeatherMainPage {

    private final WebDriver driver;

    /* ===================== Constructor ===================== */

    public WeatherMainPage(WebDriver driver) {
        this.driver = driver;
    }

    /* ===================== Locators ===================== */

    // Поисковая строка
    private final By searchInput =
            By.xpath("//input[@type='search' and @aria-label='Найти город или район']");

    // Текущий город (плейсхолдер в строке поиска)
    private final By currentCityLabel =
            By.xpath("//span[contains(@class,'Suggest_placeholder')]");

    // Пункт меню "Карта осадков"
    private final By precipitationMapMenuItem =
            By.xpath("//a[@role='menuitem' and normalize-space()='Карта осадков']");

    // Левое навигационное меню
    private final By leftNavigationMenu =
            By.xpath("//nav");

    // Главное навигационное меню
    private final By headerNavigationMenu =
            By.cssSelector("nav.AppHeaderNavigation_navigationBlock__1OW18");

    // Текущий город (плейсхолдер)
    private final By currentCityPlaceholder =
            By.cssSelector("span.Suggest_placeholder__wgCdr");

    // Кликабельный placeholder (обёртка)
    private final By searchPlaceholderContainer =
            By.cssSelector("p.Suggest_label__container__fJKa9");

    // Первая подсказка в списке
    private final By firstSuggestion =
            By.cssSelector("li.SuggestItem");

    // Основной блок текущей погоды
    private By currentWeatherBlock =
            By.cssSelector("div[class*='AppFact_wrap']");

    // Футтер
    private By footer =
            By.cssSelector("footer[class*='AppFooter_footer']");

    // SEO-блок в футтере
    private By footerSeoText =
            By.cssSelector("div[class*='seoAbout']");

    // Копирайт ЯНДЕКС
    private By footerCopyright =
            By.cssSelector("p[class*='AppFooterCopyright']");



    /* ===================== Open pages ===================== */

    /**
     * Открывает страницу по переданному URL.
     * Используется в базовом сценарии и при переходах.
     */
    public void openPage(String url) {
        System.out.println("[INFO] Opening page: " + url);
        driver.get(url);
    }

    /* ===================== Navigation ===================== */

    /**
     * Переход на страницу "Карта осадков"
     * через получение href из меню.
     */
    public void openPrecipitationMap() {
        System.out.println("[INFO] Opening precipitation map via href");

        WebElement menuItem = driver.findElement(precipitationMapMenuItem);
        String href = menuItem.getAttribute("href");

        if (href == null || href.isBlank()) {
            throw new IllegalStateException(
                    "У пункта меню 'Карта осадков' отсутствует атрибут href"
            );
        }

        driver.get(href);
    }

    /**
     * Альтернативный способ открытия "Карты осадков"
     * через JS-клик (на случай нестабильного DOM).
     */
    public void openPrecipitationMapViaJsClick() {
        System.out.println("[INFO] Opening precipitation map via JS click");

        WebElement menuItem = driver.findElement(precipitationMapMenuItem);
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", menuItem);
    }


    /* ===================== Search ===================== */

    /**
     * Поиск города через строку поиска.
     *
     * Реализован максимально близко к реальному пользовательскому сценарию:
     *  1. Клик по placeholder
     *  2. Ввод текста
     *  3. Выбор первой подсказки
     *
     * @param cityName название города
     */
    public void searchCity(String cityName) {

        System.out.println("[INFO] Searching city: " + cityName);

        // 1. Клик по placeholder (реальный пользовательский клик)
        driver.findElement(searchPlaceholderContainer).click();

        WebElement input = driver.findElement(searchInput);
        input.sendKeys(cityName);

        // 2. Ждём появления подсказок
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement suggestion = wait.until(
                ExpectedConditions.elementToBeClickable(firstSuggestion)
        );

        // 3. Кликаем по первой подсказке
        suggestion.click();

        System.out.println("[INFO] City selected from suggestions");
    }

    /* ===================== UI Checks ===================== */

    /**
     * Проверка отображения навигационного меню
     */
    public boolean isHeaderNavigationMenuDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement menu = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(headerNavigationMenu)
            );
            return menu.isDisplayed();
        } catch (TimeoutException e) {
            System.out.println("[WARN] Header navigation menu not displayed");
            return false;
        }
    }

    /**
     * Проверка отображения строки поиска города
     */
    public boolean isSearchInputDisplayed() {
        return driver.findElement(searchInput).isDisplayed();
    }

    /**
     * Проверка отображения текущего города (placeholder в поиске)
     */
    public boolean isCurrentCityDisplayed() {
        return driver.findElement(currentCityPlaceholder).isDisplayed();
    }

    /**
     * Проверка отображения основного блока температуры
     */
    public boolean isCurrentWeatherBlockDisplayed() {
        return driver.findElement(currentWeatherBlock).isDisplayed();
    }

    /**
     * Проверка отображения футтера
     */
    public boolean isFooterDisplayed() {
        return driver.findElement(footer).isDisplayed();
    }

    /**
     * Проверка отображения SEO-блока в футтере
     */
    public boolean isFooterSeoTextDisplayed() {
        return driver.findElement(footerSeoText).isDisplayed();
    }

    /**
     * Проверка отображения копирайта ЯНДЕКС в футтере
     */
    public boolean isFooterCopyrightDisplayed() {
        return driver.findElement(footerCopyright).isDisplayed();
    }


    /* ===================== Getters ===================== */

    /**
     * Текущий URL страницы
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Заголовок страницы (title)
     */
    public String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Название текущего города,
     * отображаемого в строке поиска
     */
    public String getCurrentCity() {
        return driver.findElement(currentCityPlaceholder)
                .getText()
                .trim();
    }
}