package training.tracker.tests;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

class TaskDataFakerTest {

    private static final long SEED = 42;

    @Test
    void generatedTaskTitlesShouldBeUsableAsTestData() {
        Faker faker = new Faker(Locale.ENGLISH, new Random(SEED));

        List<String> titles = List.of(
                faker.lorem().sentence(3),
                faker.lorem().sentence(4),
                faker.lorem().sentence(5)
        );

        assertThat(titles)
                .as("Заголовки %s, seed=%d", titles, SEED)
                .hasSize(3)
                .allSatisfy(title -> assertThat(title)
                        .isNotBlank()
                        .hasSizeGreaterThan(5));
    }
}
