package training.tracker.stub;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Учебный стаб трекера задач для модулей 9 и 10.
 * GET /tasks/{key} отдает JSON задачи, а браузеру (Accept: text/html) — страницу задачи.
 * Неизвестный ключ — 404 и JSON {"error":"Task not found","key":"<ключ>"}.
 * Запуск отдельно: ./gradlew runStub, адрес http://localhost:8080/tasks/QA-1
 *
 * Упрощения, о которых стоит помнить: стаб отдает фиксированные данные (не TaskService и не H2),
 * не различает HTTP-методы (POST/DELETE ответят так же, как GET),
 * а HTML выбирает простым поиском "text/html" в заголовке Accept.
 */
public class TrackerStub {

    private static final Map<String, String[]> TASKS = Map.of(
            "QA-1", new String[]{"QA-1", "Write tests", "OPEN", "HIGH", "ivanov"},
            "QA-2", new String[]{"QA-2", "Fix bug", "DONE", "MEDIUM", "petrov"}
    );

    private final HttpServer server;

    public TrackerStub(int port) throws IOException {
        server = HttpServer.create(new InetSocketAddress("localhost", port), 0);
        server.createContext("/tasks/", this::handleTask);
    }

    public void start() {
        server.start();
    }

    public void stop() {
        server.stop(0);
    }

    private void handleTask(HttpExchange exchange) throws IOException {
        String key = exchange.getRequestURI().getPath().substring("/tasks/".length());
        String[] task = TASKS.get(key);
        String accept = exchange.getRequestHeaders().getFirst("Accept");
        boolean browser = accept != null && accept.contains("text/html");

        if (task == null) {
            send(exchange, 404, "application/json", "{\"error\":\"Task not found\",\"key\":\"%s\"}".formatted(key));
        } else if (browser) {
            send(exchange, 200, "text/html; charset=utf-8", page(task));
        } else {
            send(exchange, 200, "application/json", json(task));
        }
    }

    private String json(String[] task) {
        return """
                {"key":"%s","title":"%s","status":"%s","priority":"%s","assigneeLogin":"%s"}"""
                .formatted((Object[]) task);
    }

    private String page(String[] task) {
        return """
                <!doctype html>
                <html lang="ru"><head><meta charset="utf-8"><title>%1$s</title></head>
                <body>
                  <h1><span data-testid="task-key">%1$s</span> %2$s</h1>
                  <p>Статус: <span data-testid="task-status">%3$s</span></p>
                  <p>Приоритет: <span data-testid="task-priority">%4$s</span></p>
                  <p>Исполнитель: <span data-testid="task-assignee">%5$s</span></p>
                </body></html>
                """.formatted((Object[]) task);
    }

    private void send(HttpExchange exchange, int status, String contentType, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", contentType);
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream output = exchange.getResponseBody()) {
            output.write(bytes);
        }
    }

    public static void main(String[] args) throws IOException {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 8080;
        new TrackerStub(port).start();
        System.out.println("Стаб трекера запущен: http://localhost:" + port + "/tasks/QA-1 (Ctrl+C — остановить)");
    }
}
