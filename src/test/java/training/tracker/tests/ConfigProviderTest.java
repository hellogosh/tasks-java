package training.tracker.tests;

import org.junit.jupiter.api.Test;
import training.tracker.config.ConfigProvider;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ConfigProviderTest {
    @Test
    void configMustBeLocalhost() {
        assertThat(ConfigProvider.config().baseUrl()).isEqualTo("http://localhost:8080");

    }
    @Test
    void dbUrlShouldBeH2InMemory() {
        assertThat(ConfigProvider.config().dbUrl().startsWith("jdbc:h2:mem:"));
    }
}
