package training.tracker.stub;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.IOException;
import java.io.InputStream;
import java.net.BindException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Properties;

/**
 * Поднимает TrackerStub перед API- и UI-тестами.
 * baseUrl берется из src/test/resources/test.properties, по умолчанию http://localhost:8080.
 * Если порт уже занят, проверяет, что там отвечает учебный стаб (например, запущенный ./gradlew runStub),
 * и только тогда использует его. Любое другое приложение на порту — понятная ошибка, а не странные падения тестов.
 */
public class TrackerStubExtension implements BeforeAllCallback {

    @Override
    public void beforeAll(ExtensionContext context) {
        context.getRoot().getStore(ExtensionContext.Namespace.GLOBAL)
                .getOrComputeIfAbsent(TrackerStubExtension.class, k -> startStub());
    }

    private ExtensionContext.Store.CloseableResource startStub() {
        String baseUrl = baseUrl();
        int port = URI.create(baseUrl).getPort();
        try {
            TrackerStub stub = new TrackerStub(port);
            stub.start();
            return stub::stop;
        } catch (BindException portIsBusy) {
            if (trainingStubAnswers(baseUrl)) {
                return () -> { };
            }
            throw new IllegalStateException("Порт " + port + " занят другим приложением, это не учебный стаб. "
                    + "Остановите его или поменяйте baseUrl в src/test/resources/test.properties", portIsBusy);
        } catch (IOException exception) {
            throw new IllegalStateException("Не удалось запустить стаб трекера на порту " + port, exception);
        }
    }

    private boolean trainingStubAnswers(String baseUrl) {
        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(
                    HttpRequest.newBuilder(URI.create(baseUrl + "/tasks/QA-1"))
                            .timeout(Duration.ofSeconds(2))
                            .build(),
                    HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 200 && response.body().contains("\"assigneeLogin\":\"ivanov\"");
        } catch (IOException | InterruptedException | RuntimeException exception) {
            return false;
        }
    }

    private String baseUrl() {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("test.properties")) {
            if (input != null) {
                properties.load(input);
            }
        } catch (IOException ignored) {
            // нет файла — используем адрес по умолчанию
        }
        return properties.getProperty("baseUrl", "http://localhost:8080");
    }
}
