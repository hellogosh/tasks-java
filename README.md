# Java для тестировщиков: проект участника

Рабочий проект курса **«Программирование на Java для тестировщиков»**. Учебных тестов в нём пока нет: их вы напишете сами, занятие за занятием. Есть только два технических теста, которые проверяют, что окружение готово.

Стек курса: **JDK 25**, **Gradle 9.6.1** (скачивается сам через `gradlew`), JUnit 5, Playwright с браузером Chromium.

## Подготовка до первого занятия

1. Установите JDK 25. Надежный способ — Temurin 25 с [adoptium.net](https://adoptium.net/temurin/releases/?version=25). Проверка: `java --version` показывает 25.
2. Установите OpenIDE с [openide.ru](https://openide.ru), откройте в ней папку проекта (*Open* → папка, где лежит `build.gradle`) и дождитесь загрузки Gradle. Проверьте *Settings → Build, Execution, Deployment → Build Tools → Gradle → Gradle JVM*: должна быть выбрана JDK 25. *Project Structure → SDK* — тоже 25.
3. В терминале из папки проекта выполните по очереди (Windows: `.\gradlew.bat` вместо `./gradlew`):

   ```bash
   ./gradlew --version        # Gradle 9.6.1, Launcher JVM: 25
   ./gradlew test             # ровно 1 тест: EnvironmentSmokeTest — PASSED
   ./gradlew installBrowsers  # скачать Chromium для Playwright, несколько сотен МБ
   ./gradlew uiTest           # ровно 1 тест: BrowserSmokeTest — PASSED
   ```

   Первый запуск скачивает Gradle и библиотеки курса, это займет несколько минут.

**Готово, если** `./gradlew test` и `./gradlew uiTest` показали по одному зелёному тесту (`... PASSED`, затем `BUILD SUCCESSFUL`). Пришлите тренеру скриншот вывода `./gradlew --version`, `./gradlew test` и `./gradlew uiTest`.

**Повторный запуск без `PASSED` — это нормально.** Если код не менялся, Gradle не прогоняет тесты заново: в выводе `test UP-TO-DATE` (или `up-to-date` в итоговой строке), `BUILD SUCCESSFUL` и ни одной строки `PASSED`. Окружение при этом в порядке. Чтобы увидеть свежий прогон, запустите с очисткой: `./gradlew clean test` и `./gradlew clean uiTest` — строки `PASSED` появятся снова. Браузер `clean` не удаляет.

Тревожный случай другой: строки `PASSED` нет и после `clean`, а отчёт `build/reports/tests/test/index.html` показывает 0 тестов. Значит, открыт не тот проект или нет папки `environment/` — напишите тренеру.

| Что видно | Что сделать |
|---|---|
| `Тесты запущены на Java 21, а курсу нужна Java 25` | в IDE и в `JAVA_HOME` выбрана не та JDK |
| `Cannot find a Java installation ... languageVersion=25` | JDK 25 не установлена или Gradle её не видит |
| `Could not resolve ...` / ошибка загрузки дистрибутива Gradle | нет интернета или сеть блокирует `services.gradle.org`/Maven Central — повторите в другой сети (дома), иначе напишите тренеру |
| `BrowserSmokeTest` падает с `Executable doesn't exist` | не выполнен `./gradlew installBrowsers` |

## Что внутри

```text
Памятка участника — опоры Java.md                    — шпаргалка по синтаксису, держите открытой на занятиях
Практические задания/                                — карточки встреч 1–9; карточка финала выдаётся 11.11
src/main/java/training/tracker/SlaCalculator.java   — код первого занятия
src/test/java/training/tracker/environment/          — проверка окружения перед курсом, не трогать
src/test/java/training/tracker/stub/                 — учебный стаб трекера (модули 9–10), не трогать
src/test/resources/tasks.txt                         — данные для модуля 7
src/test/resources/test.properties                   — настройки (baseUrl, dbUrl) для модулей 8–10
заготовки/                                           — код, который выдается готовым на занятиях 2, 3, 4 и 7
```

## Полезные команды

| Команда | Что делает |
|---|---|
| `./gradlew test` | запустить все тесты, кроме UI |
| `./gradlew uiTest` | запустить UI-тесты (тег `ui`) |
| `./gradlew installBrowsers` | скачать браузер Playwright |
| `./gradlew runStub` | поднять учебный стаб: `http://localhost:8080/tasks/QA-1` |

Отчет о запуске: `build/reports/tests/test/index.html`.

Если тесты пишут «Порт 8080 занят другим приложением» — остановите то, что занимает порт, или поменяйте `baseUrl` в `src/test/resources/test.properties`.
