# Автоматизированные UI-тесты: Яндекс.Погода + Wikipedia (Android)

Проект автотестов, разработанный в рамках итогового зачётного проекта МИФИ по дисциплине «Тестирование сервисов и приложений».

В репозитории реализованы:
- **Web UI автотесты** для сайта **Яндекс.Погода** (Selenium + TestNG)
- **Mobile UI автотесты** для Android-приложения **Wikipedia (alpha)** (Appium + TestNG)

---

## Реализованные возможности

### Web (Selenium)
- **Стабильные сценарии**:
   - открытие главной страницы Яндекс.Погоды
   - открытие страницы города по прямому URL
   - навигация через меню на «Карту осадков»
   - проверка наличия основных UI-элементов на главной странице
- **Page Object Model**: логика действий вынесена в `WeatherMainPage`
- **Явные ожидания**: `WebDriverWait` там, где это критично для стабильности
- **Конфигурация** через `src/test/resources/web-test.properties`

### Mobile (Appium)
- **Стабильные сценарии**:
   - запуск приложения и проверка главного экрана
   - поиск статьи
   - открытие статьи из результатов поиска
   - прокрутка статьи (проверка изменения контента через PageSource)
- **Page Object Model**: `MainPage`, `SearchPage`, `ArticlePage`
- **Обработка всплывающих окон Wikipedia** (например, *Wikipedia Games*, *Customize Toolbar*) без клика по лишним кнопкам
- **Конфигурация** через `src/test/resources/mobile-test.properties`

---

## Технологический стек

- **Язык**: Java 23
- **Сборка**: Maven 4.0.0
- **Тестовый фреймворк**: TestNG 7.9.0
- **Web UI**: Selenium WebDriver 4.20.0 + WebDriverManager 5.7.0
- **Mobile UI**: Appium Java Client 9.2.2 + UiAutomator2
- **IDE**: IntelliJ IDEA

---

## Структура проекта

```
src/test/java/ru/mifi/testing/
├── mobile/
│   ├── base/                    # BaseMobileTest (Appium driver lifecycle)
│   ├── config/                  # MobileTestConfig (чтение mobile-test.properties)
│   ├── pages/                   # Page Objects (MainPage, SearchPage, ArticlePage)
│   └── tests/                   # AppLaunchTest, SearchTest, OpenArticleTest, ScrollArticleTest
└── web/
    ├── base/                    # BaseWebTest (WebDriver lifecycle)
    ├── config/                  # WebTestConfig (чтение web-test.properties)
    ├── pages/                   # WeatherMainPage
    └── tests/                   # OpenWeatherPageTest и др.

src/test/resources/
├── mobile-test.properties
└── web-test.properties
```

---

## Требования к окружению

### Web (Selenium)
- JDK 11+ или выше
- Maven 4.0.0
- Safari
- WebDriver настраивается автоматически через **WebDriverManager**

### Mobile (Appium + Android Emulator)
- JDK 11+
- Maven 4.0.0
- **Android Studio** + Android SDK (SDK Platforms, Platform-Tools)
- **Android Emulator (AVD)** (рекомендуется Pixel 7a / API 36+)
- **Node.js + npm**
- **Appium Server 2.x** + драйвер **UiAutomator2**
- Приложение **Wikipedia (org.wikipedia.alpha)** установлено на эмуляторе

---

## Быстрый запуск

### 1) Клонирование репозитория
```bash
git clone https://github.com/MolkovOleg/mephi-test-final-project
cd mephi-test-final-project
```

### 2) Установка зависимостей
```bash
mvn clean install "-DskipTests"
```

---

## Web UI тестирование

### Конфигурация
Файл: `src/test/resources/web-test.properties`

Пример:
```properties
base.url=https://yandex.ru/pogoda
browser=safari
timeout.seconds=10
```

### Запуск
Запустить **только web-тесты** (без mobile):
```bash
mvn test "-Dtest=OpenWeatherPageTest,OpenCityWeatherPageTest,NavigationMenuTest,WeatherPageUiElementsTest"
```

Запуск конкретного теста:
```bash
mvn test "-Dtest=OpenWeatherPageTest"
```

## Mobile UI тестирование

### Конфигурация
Файл: `src/test/resources/mobile-test.properties`

Пример:
```properties
mobile.appium.server.url=http://127.0.0.1:4723
mobile.platformName=Android
mobile.platformVersion=16
mobile.deviceName=emulator-5554
mobile.automationName=UiAutomator2

mobile.appPackage=org.wikipedia.alpha
mobile.appActivity=org.wikipedia.main.MainActivity

mobile.noReset=false
mobile.newCommandTimeout=180
mobile.autoGrantPermissions=true
```

### Установка Appium 2 и драйвера UiAutomator2
```bash
npm i -g appium
appium driver install uiautomator2
appium --version
```

### Запуск Appium Server
```bash
appium --address 127.0.0.1 --port 4723
```

### Запуск эмулятора и проверка устройства
1. Запустить AVD в Android Studio (Device Manager)
2. Проверить, что устройство видно:
```bash
adb devices
```

### Установка Wikipedia APK (если требуется)
Если приложение не установлено:
```bash
adb install -r wikipedia.apk
```

### Запуск тестов
Запустить **только mobile-тесты**:
```bash
mvn test "-Dtest=AppLaunchTest,SearchTest,OpenArticleTest,ScrollArticleTest"
```

Запуск конкретного теста:
```bash
mvn test "-Dtest=OpenArticleTest"
```

> Примечание: предупреждение `SLF4J(W): No SLF4J providers were found` не влияет на выполнение тестов — логирование в проекте выполняется через `System.out.println`.

---

## Реализованные тестовые сценарии

### Web (Яндекс.Погода)
Активные стабильные тесты:
- `OpenWeatherPageTest` — открытие главной страницы
- `OpenCityWeatherPageTest` — открытие страницы погоды Москвы по URL
- `NavigationMenuTest` — переход на «Карту осадков» через меню
- `WeatherPageUiElementsTest` — проверка основных UI-элементов

Демонстрационные (отключены `enabled = false` из-за нестабильности SPA-suggest/JS-кликов):
- `CitySearchViaFormTest`
- `NavigationMenuJsClickTest`

### Mobile (Wikipedia alpha)
- `AppLaunchTest` — приложение запускается, главный экран доступен
- `SearchTest` — поиск статьи по запросу `Java` даёт результаты
- `OpenArticleTest` — открытие первой найденной статьи
- `ScrollArticleTest` — прокрутка статьи, проверка изменения PageSource

---

## Чек по критериям оценивания

### 1) Web-тесты
- **Покрытие тестами основных сценариев**: реализовано 4 стабильных ключевых сценария
- **Качество реализации тестов и техника автоматизации**: Page Object, ожидания, локаторы вынесены
- **Настройка окружения и запуск тестов**: конфиг + инструкции в README

### 2) Mobile-тесты
- **Покрытие мобильных сценариев**: реализовано 4 сценария (поиск, открытие, скролл, запуск)
- **AКорректность настройки Appium и окружения**: capabilities через `mobile-test.properties`, описаны шаги запуска
- **Качество кода мобильных тестов**: Page Object, минимальные дубли, безопасное закрытие попапов

### 3) Качество проекта, структура, архитектура
- Пакеты разделены по зонам ответственности, единый стиль, Page Object

### 4) Документация (README + инструкции)
- README содержит инструкции по Web и Mobile, конфиги и примеры запуска