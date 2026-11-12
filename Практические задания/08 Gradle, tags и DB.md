# Практическое задание 08. Gradle, теги и база данных

## Цель

Привести проект к понятной структуре пакетов, настроить группы тестов и написать первую проверку базы данных.

## Стартовая точка

Работайте в `starter-project` со всем кодом модулей 1–7. До этого занятия production-модели и сервисы лежат в корневом пакете `training.tracker`; `stub`, `environment`, `builders` и `assertions` уже в своих пакетах. Библиотеки курса подключены в `build.gradle`, задачи групп тестов добавляете вы. Файл настроек `src/test/resources/test.properties` уже есть (`baseUrl`, `dbUrl`).

## Разминка

Разбираем вместе четыре сломанных теста из задания 3 прошлой встречи. Если ваш файл `Broken…Test.java` ещё красный, исправьте его по разбору или удалите: перед заданием 1 `./gradlew test` должен быть зеленым.

## База

### Задание 1. Разложить проект по пакетам

Перед переносом запомните число тестов в `./gradlew test`. Переносите через IDE: выделите несколько классов → *Refactor → Move* (F6) → пакет. IDE поправит `package` и импорты.

- модели (`Task`, `TaskStatus`, `Priority`, `User`) в `training.tracker.models` — вместе с тренером;
- сервисы (`SlaCalculator`, `ProjectCodeStats`, `PermissionService`, `TaskFilter`, `StatusDictionary`, `StatusCounter`, `TaskService`, `TaskWorkflow`, `TaskFileReader`, `TaskLineParser`) в `training.tracker.services`;
- валидаторы (`ProjectCodeValidator`, `EstimateValidator`) в `training.tracker.validators`;
- тесты в `training.tracker.tests`.

Пакеты `stub`, `environment`, `builders`, `assertions` не трогайте. После переноса `./gradlew clean test` — число тестов то же. Ошибка `... is not public in training.tracker; cannot be accessed from outside package` значит, что классу или методу не хватает `public`.

### Задание 2. Написать DB smoke test

Вместе с тренером создайте `src/test/java/training/tracker/db/TaskDatabaseTest.java`: H2 in-memory таблица `tasks` с колонками `task_key` и `status`, четыре строки с разными статусами, SQL-запрос количества задач `OPEN` (ожидаем 2), `@Tag("db")`, `try-with-resources`. Колонка называется `task_key`, а не `key`: `key` — зарезервированное слово в H2.

Затем самостоятельно, в тот же тест:

- проверьте статус задачи `QA-1`;
- проверьте, что задача `QA-2` существует.

### Задание 3. Конфигурация через OWNER

Создайте в `src/test/java/training/tracker/config/`:

- `TestConfig` — интерфейс с `@Sources("classpath:test.properties")` и методами `baseUrl()`, `dbUrl()`;
- `ConfigProvider.config()` — возвращает `ConfigFactory.create(TestConfig.class)`, созданный один раз.

Затем:

- новый класс `ConfigProviderTest` в пакете `training.tracker.tests`, два теста: `config().baseUrl()` равен `"http://localhost:8080"`; `config().dbUrl()` начинается с `"jdbc:h2:mem:"` (`assertThat(...).startsWith(...)`);
- в `TaskDatabaseTest` (пакет `db`) замените строку `"jdbc:h2:mem:tracker"` в `getConnection(...)` на `config().dbUrl()`.

В обоих классах — `import static training.tracker.config.ConfigProvider.config;`. Готово, когда в `ConfigProviderTest` 2 зелёных теста, `TaskDatabaseTest` зелёный и в нём нет ни одной строки `jdbc:h2`.

На разборе тренер покажет опыт: имя базы `jdbc:h2:mem:tracker2` в `test.properties` — DB-тест остается зеленым **без правок кода**; порт `baseUrl` `8081` — `ConfigProviderTest` краснеет: значение действительно читается из файла. Повторить самим — в Практике+.

Зачем это: в модуле 9 API-тесты возьмут адрес из того же `baseUrl`, а учебный стаб читает его из `test.properties` сам. Смена порта в одном файле перенесет и стаб, и тесты.

### Задание 4. Tags и Gradle tasks

Вместе с тренером добавьте в `build.gradle` задачи:

```groovy
tasks.register('smokeTest', Test) {
    useJUnitPlatform {
        includeTags 'smoke'
        excludeTags 'ui'
    }
}
```

и по тому же образцу `regressionTest` (`regression`), `dbTest` (`db`), `apiTest` (`api`, пригодится в модуле 9), `fastTest` (`excludeTags 'slow', 'ui'`). `excludeTags 'ui'`: UI-тест из модуля 9 может быть помечен `smoke`, но ему нужен браузер.

Скопируйте блок `testLogging` из `test` в `tasks.withType(Test).configureEach { ... }`: без него новые задачи не печатают строки `PASSED`, и три пройденных теста выглядят как ноль.

Затем самостоятельно: `@Tag("smoke")` для 3 быстрых критичных тестов и подсчет тестов в группах.

| Команда | Ожидаемое число тестов после задания |
|---|---|
| `./gradlew smokeTest` | 3 |
| `./gradlew dbTest` | 1 |
| `./gradlew apiTest` | 0 до модуля 9 — это ожидаемо |
| `./gradlew regressionTest` | 0 до модуля 9 или до Практики+ — это ожидаемо |
| `./gradlew fastTest` | все, кроме `slow` и `ui` (пока столько же, сколько `test`) |

Число тестов смотрите в выводе (`testLogging`) или в отчете `build/reports/tests/<задача>/index.html`. Зеленая задача с нулем тестов ничего не проверила. Если у задачи стоит `UP-TO-DATE`, Gradle не запускал тесты повторно: `./gradlew smokeTest --rerun`.

## Практика+

Для тех, кто закончил задание 4.

- `@Tag("regression")` для 5 расширенных тестовых методов; `regressionTest` — 5 (больше, если среди помеченных есть параметризованные: каждая строка данных — отдельный запуск).
- Повторите сами опыт с `test.properties`: `jdbc:h2:mem:tracker2` — DB-тест зеленый, порт `8081` — `ConfigProviderTest` красный. Верните оба значения.
- Вынесите создание таблицы в `src/test/resources/schema.sql` и выполняйте его из теста перед вставкой данных (файл читается как ресурс, как в модуле 7).

## Challenge

Два DB-теста создают таблицу `tasks` в одной базе: что произойдет, если в `dbUrl` добавить `;DB_CLOSE_DELAY=-1`? Найдите способ изолировать тесты друг от друга.

## Выходная мини-задача

Пометьте один тест `@Tag("slow")` и докажите по отчету, что `fastTest` его не запускает, а `test` — запускает.

## Команды проверки

```bash
./gradlew clean test
./gradlew smokeTest
./gradlew regressionTest   # 0 до модуля 9 или до Практики+
./gradlew dbTest
./gradlew apiTest     # 0 до модуля 9
./gradlew fastTest
```

## Критерии приемки

- Проект разложен по пакетам, число тестов не уменьшилось, все тесты проходят.
- Написана DB-проверка через JDBC/H2 с `try-with-resources`: количество `OPEN`, статус `QA-1`, существование `QA-2`; адрес базы берется из `ConfigProvider`.
- Есть `TestConfig`, `ConfigProvider` и тест на них; смена имени базы в `test.properties` не требует правок кода.
- Использован тег `smoke`; задачи `smokeTest`, `regressionTest`, `dbTest`, `apiTest`, `fastTest` есть, каждая запускает ожидаемое число тестов (`apiTest` и `regressionTest` — 0 до модуля 9).
- Участник объясняет блоки `settings.gradle` и `build.gradle`.
