package training.tracker.tests;

import org.junit.jupiter.api.Test;

import static training.tracker.config.ConfigProvider.config;

public class ConfigDemoTest {
    @Test
    void showConfig() {
        System.out.println("baseUrl = " + config().baseUrl());
        System.out.println("dbUrl = " + config().dbUrl());
    }
}
