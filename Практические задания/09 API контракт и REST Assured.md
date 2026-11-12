# Практическое задание 09. API и REST Assured

## Цель

Написать первые HTTP-тесты через REST Assured (200 и 404), null-safe валидатор контракта ответа с тестами и первый учебный UI smoke на Playwright по шаблону.

## Стартовая точка

Работайте в `starter-project` после модуля 8: пакеты разложены, задача `apiTest` есть, `ConfigProvider.config().baseUrl()` читает адрес из `test.properties`.

API отдаёт учебный стаб трекера из пакета `training.tracker.stub`:

| Запрос | Ответ |
|---|---|
| `GET /tasks/QA-1` | `200`, JSON задачи `OPEN`, `HIGH`, `ivanov` |
| `GET /tasks/QA-2` | `200`, JSON задачи `DONE`, `MEDIUM`, `petrov` |
| `GET /tasks/QA-999` | `404`, `{"error":"Task not found","key":"QA-999"}` |
| `/tasks/QA-1` в браузере | HTML-страница задачи с `data-testid` |

Стаб поднимается сам, если над тестовым классом стоит `@ExtendWith(TrackerStubExtension.class)`. Посмотреть ответ руками: `./gradlew runStub`, затем `curl http://localhost:8080/tasks/QA-1` или тот же адрес в браузере.

```json
{
  "key": "QA-1",
  "title": "Write tests",
  "status": "OPEN",
  "priority": "HIGH",
  "assigneeLogin": "ivanov"
}
```

REST Assured, Gson и Playwright уже подключены в `build.gradle`. Браузер Playwright скачан до курса (`./gradlew installBrowsers`).

## База

### Задание 1. Первый API smoke test

`TaskApiSmokeTest` в `src/test/java/training/tracker/api` с `@Tag("api")` и `@ExtendWith(TrackerStubExtension.class)` для `GET /tasks/QA-1`:

- `baseUri(config().baseUrl())` — адрес из конфигурации, не literal;
- `statusCode(200)`, `contentType("application/json")`;
- поля `key`, `status`, `priority`.

Увидьте тест красным, поменяв ожидание.

### Задание 2. Негативный сценарий 404

- `GET /tasks/QA-999`: `404`, `application/json`, `error` = `Task not found`, `key` = `QA-999`.

Теги для групп запуска модуля 8: `@Tag("smoke")` — на тест `QA-1` и на UI smoke (он всё равно идёт только в `uiTest`); `@Tag("regression")` — на тест `404` и на `@MethodSource`-тест невалидных наборов контракта. После этого `./gradlew smokeTest` — 4 теста, `regressionTest` — больше, чем после модуля 8.

### Задание 3. Описать `TaskResponse`

В `src/main/java/training/tracker/api/` создайте record с полями `key`, `title`, `status`, `priority`, `assigneeLogin` (все `String`).

### Задание 4. Null-safe валидатор контракта и его тесты

`TaskResponseContract.isValid(TaskResponse)` в том же пакете. Проверяет:

- `key`, `title`, `assigneeLogin` — не `null` и не blank;
- `status` — из `OPEN`, `IN_PROGRESS`, `DONE`, `CANCELLED`;
- `priority` — из `CRITICAL`, `HIGH`, `MEDIUM`, `LOW`.

На `null` в любом поле метод возвращает `false`, а не падает. Подсказка: `Set.of(...).contains(null)` выбрасывает `NullPointerException` — сначала проверьте, что значение есть.

`TaskResponseContractTest` (без тега `api`, HTTP не нужен):

- валидный ответ;
- каждый допустимый статус — `@ValueSource`;
- каждый допустимый приоритет — `@ValueSource`. Значения выпишите из требования, а не берите из константы валидатора: иначе тест не заметит, если из константы пропадёт значение;
- невалидные наборы — `@MethodSource`: для каждого обязательного поля отсутствие (`null`), пустая строка (`""`) и строка из пробелов (`"  "`); для `status` и `priority` ещё неизвестное значение (`UNKNOWN`, `BLOCKER`). В каждом наборе остальные поля валидны.

### Задание 5. Связать HTTP и контракт

Оба новых файла — в `src/test/java/training/tracker/api/`:

1. Класс `TaskResponseAssertions`: метод `public static void assertMatchesTaskContract(TaskResponse task)`. Внутри пять проверок, по одному полю с `.as("поле в %s", task)`: `key`, `title`, `assigneeLogin` — `isNotBlank()`; `status`, `priority` — `isIn(...)` с множествами из `TaskResponseContract` (сделайте их `public static final`). При падении сообщение называет поле и показывает весь ответ.
2. Класс `TaskApiContractTest` с `@Tag("api")` и `@ExtendWith(TrackerStubExtension.class)`, тест `taskResponseShouldMatchContract()`: получить ответ через REST Assured (`.extract().response()`), собрать `TaskResponse task` из `response.path("key")` и остальных полей и проверить через `assertMatchesTaskContract(task)`.
3. Запустите `./gradlew apiTest` — тест зелёный.
4. Проверьте сообщение: временно замените последнюю строку теста на `assertMatchesTaskContract(new TaskResponse("QA-1", "Write tests", null, "HIGH", "ivanov"))` и запустите. В сообщении должно быть `status в TaskResponse[...status=null...]`. Верните строку.

Готово, когда `TaskApiContractTest` зелёный, а сломанный вариант называет поле `status` и показывает весь ответ.


### Задание 6. Первый UI smoke на Playwright (по шагам с тренером)

`TaskPageSmokeTest` в `src/test/java/training/tracker/ui` с `@Tag("ui")` и `@ExtendWith(TrackerStubExtension.class)`:

- `Playwright` → `Browser` (Chromium) → `Page` в `try-with-resources`;
- открыть `config().baseUrl() + "/tasks/QA-1"`;
- `PlaywrightAssertions.assertThat(page.locator("[data-testid='task-key']")).hasText("QA-1")`;
- увидеть падение: неверное ожидание `QA-2`, затем неверный локатор; вернуть;
- запуск `./gradlew uiTest`.

## Практика+

Для тех, кто закрыл базу (в блоке 7 база — в приоритете).

- Второй ключ: `GET /tasks/QA-2` — `200`, `application/json`, `status` = `DONE`, `priority` = `MEDIUM`, `assigneeLogin` = `petrov`.
- Ответ, сохранённый строкой, проверьте без сервера: `JsonPath.from(json)` и тот же контракт.
- **Новое требование учебного проекта QA:** ключ задачи начинается с `QA-`. Это правило конкретного проекта, а не общий контракт трекера. Добавьте отдельный метод (например, `isValidForQaProject`) и тесты: `QA-1`, `DEV-1`, `null`.
- Второй контракт: `ProjectResponse(String code, String name, String ownerLogin, boolean archived)` и `ProjectContractValidator` для трёх строковых полей. Подумайте, почему валидатор не может проверить наличие `archived` в JSON и на каком уровне это проверяется.

## Challenge

Получите `TaskResponse` одной строкой через `response.as(TaskResponse.class)`. Выясните, какая библиотека делает преобразование и откуда она приходит в проект:

```bash
./gradlew dependencyInsight --dependency gson --configuration testRuntimeClasspath
```

Подсказка: в POM REST Assured Gson — optional-зависимость. Что сломается в отдельном API-проекте без Playwright, если не подключить Gson явно?

## Команды проверки

```bash
./gradlew test       # всё, кроме UI: unit-тесты (включая TaskResponseContractTest) и API
./gradlew apiTest    # только HTTP-тесты с тегом api
./gradlew uiTest     # UI smoke
```

Проверьте в выводе, что каждая задача запустила **не ноль** тестов: задача с опечаткой в теге проходит «зелёной» пустой.

## Критерии приемки

- Создан record `TaskResponse` из 5 полей.
- REST Assured тесты проверяют `200` и `404`: статус, тип ответа и тело.
- `TaskResponseContract` не падает на `null`; тесты покрывают все допустимые статусы и приоритеты, а для каждого обязательного поля — `null`, пустую строку и строку из пробелов; а также неизвестные `status`/`priority`.
- Есть reusable contract assertion с информативным сообщением.
- UI smoke проходит через `./gradlew uiTest` и был увиден красным.
- `./gradlew test`, `./gradlew apiTest`, `./gradlew uiTest` проходят и запускают ненулевое число тестов.
