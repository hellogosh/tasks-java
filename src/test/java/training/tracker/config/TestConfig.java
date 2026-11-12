package training.tracker.config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:test.properties")
public interface TestConfig extends Config{
    @Key("baseUrl")
    String baseUrl();

    @Key("dbUrl")
    String dbUrl();

}
