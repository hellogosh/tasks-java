# Практическое задание 06. Builders и reusable assertions

## Цель

Убрать строковые статусы, сократить подготовку данных через builder и вынести повторяющиеся проверки.

## Стартовая точка

Работайте в `starter-project`, продолжая код модуля 5. Заготовок нет. Production-классы лежат в корневом пакете `training.tracker`; builder и assertions сразу создаются в своих пакетах `training.tracker.builders` и `training.tracker.assertions` в `src/test/java`.

## Разминка

Без кода: вопросы с подвохом по модулю 5 — record и его поля, `withStatus`, сравнение задач и списков, `null` в `equals`, `assertAll`. Отвечаем устно, вопросы — на слайде.

## База

### Задание 1. Перевести строки в enum

Создайте в `training.tracker`:

- `TaskStatus`: `OPEN`, `IN_PROGRESS`, `DONE`, `CANCELLED`;
- `Priority`: `CRITICAL`, `HIGH`, `MEDIUM`, `LOW`.

Перепишите модель:

```java
public record Task(String key, String title, TaskStatus status, Priority priority) {
}
```

Затем миграция — по шагам, пока `./gradlew test` не станет зеленым:

1. Создайте `TaskStatus` и `Priority`, поменяйте типы полей `Task`. Компилятор подсветит `withStatus` — сделайте параметр `withStatus(TaskStatus newStatus)`.
2. **Сами, компилятор не подскажет:** `isOpen()` — `return status == TaskStatus.OPEN;`. Старый `"OPEN".equals(status)` компилируется, но всегда возвращает `false`: строка не равна enum.
3. **Сами, компилятор не подскажет:** параметр сервиса — `TaskService.tasksWithStatus(List<Task> tasks, TaskStatus requiredStatus)`, сравнение `task.status() == requiredStatus`. Со строковым параметром фильтр компилируется и молча ничего не находит.
4. Тесты модуля 5 — по ошибкам компиляции: factory method `task(String key, TaskStatus status)`, `new Task(..., TaskStatus.OPEN, Priority.HIGH)`, `"OPEN"` → `TaskStatus.OPEN` в вызовах фильтра.
5. Найдите поиском (Ctrl+Shift+F) оставшиеся `"OPEN"`, `"DONE"`, `"HIGH"` в `src/test`: `assertEquals("OPEN", task.status())` → `assertEquals(TaskStatus.OPEN, task.status())` — меняется только первый аргумент, ожидание. Старый вариант компилируется, но падает с сообщением «`expected: java.lang.String@…<OPEN> but was: training.tracker.TaskStatus@…<OPEN>`» — слева строка, справа enum.
6. Ни одну проверку при миграции не удаляйте — меняются только типы. Число тестов после миграции — не меньше, чем до.
7. `TaskFilter`, `StatusDictionary` и `StatusCounter` из модуля 4 не трогайте: они работают со списками строк, а не с `Task`.


Помните: переменная enum может быть `null`. Enum убирает опечатки, но не отвечает на вопрос «что делать, если статуса нет».

### Задание 2. Создать `TaskBuilder`

В `src/test/java/training/tracker/builders/TaskBuilder.java`. Значения по умолчанию: `"QA-1"`, `"Default task"`, `TaskStatus.OPEN`, `Priority.MEDIUM`. Методы:

- `withKey(String key)`;
- `withTitle(String title)`;
- `withStatus(TaskStatus status)`;
- `withPriority(Priority priority)`;
- `build()`.

Перепишите минимум 3 теста `TaskServiceTest` на builder. Проверка только `result.size()` не допускается: сравнивайте состав результата, например `assertEquals(List.of(firstOpen, secondOpen), result)`.

Если сборка пишет `Task is not public in training.tracker; cannot be accessed from outside package` — builder лежит в другом пакете, и ему нужен `public` перед `record Task`, `enum TaskStatus` и `enum Priority`.

### Задание 3. Создать reusable assertion

В `src/test/java/training/tracker/assertions/TaskAssertions.java`:

```java
public static void assertTask(Task task, String expectedKey, TaskStatus expectedStatus, Priority expectedPriority)
```

Внутри — `assertAll`, у каждой проверки третьим аргументом название поля: `assertEquals(expectedStatus, task.status(), "status")`. Используйте метод минимум в двух тестах.

Проверьте, что при падении видно, какое поле не совпало: в одном тесте замените ожидаемый статус (третий аргумент `assertTask`) на неверный, например `TaskStatus.IN_PROGRESS` → `TaskStatus.DONE`. Запустите тест и разберите сообщение: какое поле не совпало? Верните прежний статус — тест снова зелёный.

### Задание 4. Рефакторинг своих тестов

- Тесты, где `Task` создается через конструктор, перепишите на `TaskBuilder`.
- Где проверяются `key`, `status` и `priority` — замените эти три проверки на `assertTask(...)`. Остальные проверки оставьте рядом: `assertTask` не смотрит на `title`. Например, тест из модуля 5, который проверял четыре поля:

```java
Task actualTask = service.tasksWithStatus(List.of(task), TaskStatus.OPEN).getFirst();

assertTask(actualTask, "QA-1", TaskStatus.OPEN, Priority.HIGH);
assertEquals("Write tests", actualTask.title(), "title");
```

- После рефакторинга сломайте одно значение в тесте и убедитесь, что тест падает. Верните. Отдельно сломайте ожидаемый `title`: тест должен падать и до, и после рефакторинга.

## Если модуль 6 не закончен

Запасной вариант — `starter-project/заготовки/06-если-не-успели`. Сначала попробуйте дописать свое — это и есть решение задач модуля.

Что в заготовке и куда копировать:

- `Task` (уже с `isOpen()` и `withStatus(TaskStatus)` из модуля 5), `TaskStatus`, `Priority`, `TaskService` (`tasksWithStatus` на `TaskStatus`) → `src/main/java/training/tracker/`; `User` — туда же, если своего record из модуля 5 нет (его использует `tests/TaskModelTest`);
- `builders/TaskBuilder` → `src/test/java/training/tracker/builders/`;
- `tests/TaskModelTest`, `tests/TaskServiceTest` — тесты модуля 5, переведённые на enum **со всеми проверками** → `src/test/java/training/tracker/`, только если свои не получается перевести.

Порядок:

1. Скопируйте модели, `TaskService` и builder. Если в вашем `TaskService` были свои дополнительные методы — перенесите их в скопированный.
2. Свои тесты модуля 5 переведите сами: `"OPEN"` → `TaskStatus.OPEN`, `"HIGH"` → `Priority.HIGH`. Компилятор покажет все места. Не успеваете — замените свой тестовый класс на одноимённый из `tests/`.
3. Ни одну проверку не удаляйте и не комментируйте. Если тестовый класс пришлось временно отключить — это незавершённая работа: запишите его в список «вернуть» и скажите тренеру. Критерий «тесты модуля 5 переведены без удаления проверок» с отключённым классом не выполнен.
4. Проверка: `./gradlew test` — BUILD SUCCESSFUL, и тестов не меньше, чем было до миграции.

## Практика+

Для тех, кто закончил задания раньше времени.

- Добавьте в `TaskService` метод `boolean isFinal(TaskStatus status)`: `DONE` и `CANCELLED` — финальные, `OPEN` и `IN_PROGRESS` — нет. Используйте `switch`. Напишите параметризованный тест на все четыре статуса (`@CsvSource({"OPEN, false", ...})` — JUnit сам превратит строку в enum).
- Тест значений по умолчанию: builder без вызовов `with...` создает задачу `QA-1`, `Default task`, `OPEN`, `MEDIUM`.
- Создайте `TaskAssertJAssertions.assertTask(...)` на `assertThat(...).as("status").isEqualTo(...)`. Сломайте ожидание в одном тесте и сравните сообщения падения JUnit- и AssertJ-вариантов.
- Добавьте в builder методы-ярлыки `open()`, `done()`, `critical()`, `high()` и используйте их в тесте: `new TaskBuilder().done().high().build()`.

## Challenge

Расширьте `TaskAssertions.assertTask`, чтобы проверять и `title`, не ломая существующие вызовы (перегрузка метода). После этого отдельную проверку заголовка из задания 4 можно заменить вызовом перегрузки.

## Выходная мини-задача

Поменяйте в `TaskBuilder` приоритет по умолчанию на `Priority.LOW` и запустите тесты. Какие тесты покраснели — и почему? Тесты, которым важен приоритет, должны задавать его явно через `withPriority` — поправьте их. Если вы написали тест значений по умолчанию (Практика+), он покраснеет честно. Затем верните `MEDIUM` и убедитесь, что сборка зеленая.

## Команды проверки

```bash
./gradlew test
```

## Критерии приемки

- Созданы enum `TaskStatus` и `Priority`, `Task` и `TaskService` используют их; тесты модуля 5 переведены без удаления проверок.
- Написан `TaskBuilder`, он используется минимум в 3 тестах `TaskServiceTest`; ни один не проверяет только `size()`.
- Повторяющаяся проверка вынесена в `TaskAssertions`, при падении отчет называет поле.
- Рефакторинг не ослабил проверки: тест, который проверял `title`, по-прежнему падает при неверном заголовке.
- Участник объясняет разницу между `assertAll` и AssertJ в reusable assertion.
- Все тесты проходят через `./gradlew test`.
