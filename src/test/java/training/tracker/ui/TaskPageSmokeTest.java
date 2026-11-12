package training.tracker.ui;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import training.tracker.stub.TrackerStubExtension;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static training.tracker.config.ConfigProvider.config;

//@Tag("ui")
@ExtendWith(TrackerStubExtension.class)
public class TaskPageSmokeTest {

    @Test
    void taskPageShouldShowTaskKey() {
        try (Playwright playwright = Playwright.create();
             Browser browser = playwright.chromium().launch()) {

            Page page = browser.newPage();
            page.navigate(config().baseUrl() + "/tasks/QA-1");

            assertThat(page.locator("[data-testid='task-key']")).hasText("QA-1");
        }
    }
    
}