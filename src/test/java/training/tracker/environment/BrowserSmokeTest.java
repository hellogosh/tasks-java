package training.tracker.environment;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Проверка, что браузер Playwright скачан и запускается: ./gradlew installBrowsers, затем ./gradlew uiTest.
 * Страница собирается прямо в памяти, сеть и стаб не нужны.
 */
@Tag("ui")
class BrowserSmokeTest {

    @Test
    void chromiumShouldOpenPage() {
        try (Playwright playwright = Playwright.create();
             Browser browser = playwright.chromium().launch()) {
            Page page = browser.newPage();

            page.setContent("<title>Java для тестировщиков</title><h1>Браузер готов</h1>");

            assertEquals("Java для тестировщиков", page.title());
        }
    }
}
