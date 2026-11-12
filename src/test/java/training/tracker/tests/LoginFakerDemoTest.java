package training.tracker.tests;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

class LoginFakerDemoTest {

    private static final long SEED = 42;

    @Test
    void loginsShouldRepeatWithSameSeed() {
        Faker faker = new Faker(Locale.ENGLISH, new Random(SEED));

        List<String> logins = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            logins.add(faker.credentials().username());
        }

        System.out.println("Логины, seed=" + SEED + ": " + logins);

        assertThat(logins)
                .hasSize(5)
                .allSatisfy(login -> assertThat(login).isNotBlank());
    }
}
