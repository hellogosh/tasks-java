# Памятка участника — опоры Java

Короткая шпаргалка для тех, кто раньше не программировал. Держите её открытой на занятиях: здесь то, что легко забыть между встречами. Каждый раздел — одна мысль и крошечный пример.

## Как пишется код

```java
package training.tracker;                 // первая строка: совпадает с папкой

public class SlaCalculator {              // файл называется SlaCalculator.java
    static final int MAX_HOURS = 72;      // константа: БОЛЬШИМИ_БУКВАМИ

    public int responseTimeHours(String priority) {   // метод: camelCase
        int hours = 24;                   // каждая инструкция заканчивается ;
        return hours;
    }                                     // каждая { закрывается своей }
}
```

- Регистр важен: `String` и `string` — разные слова, второго в Java нет.
- Имена: класс — `SlaCalculator`, метод и переменная — `responseTimeHours`, константа — `MAX_HOURS`.
- Комментарий `// ...` — до конца строки, Java его не читает.

## Переменная: тип, имя, значение

```java
int hours = 8;            // целое число
boolean valid = true;     // да или нет
String code = "QA";       // текст, может быть null
char first = 'Q';         // один символ — в одинарных кавычках
```

Строка — в двойных кавычках `"QA"`, символ — в одинарных `'Q'`. Перепутать — ошибка компиляции.

## Область видимости: где переменная существует

Переменная живёт внутри фигурных скобок, в которых объявлена.

```java
int digits = 0;                      // объявлена до цикла — живёт после него
for (int i = 0; i < code.length(); i++) {
    char c = code.charAt(i);         // c и i существуют только внутри цикла
    if (c >= '0' && c <= '9') {
        digits++;
    }
}
return digits;                       // можно: digits объявлена снаружи
```

Если объявить счётчик `int digits = 0;` внутри цикла, он будет обнуляться на каждом шаге. Использовать `c` после цикла нельзя — компилятор скажет `cannot find symbol`.

## Метод: вход, действие, результат

```java
public int responseTimeHours(String priority) { ... }
//     ^ тип результата      ^ параметр: что метод получает
```

Вызов метода — это действие, результат которого нужно сохранить или проверить:

```java
int actualHours = calculator.responseTimeHours("CRITICAL");
```

`void` — метод ничего не возвращает. `return` сразу завершает метод.

## Объект и `new`

Класс — описание, объект — экземпляр. Обычный метод вызывается у объекта:

```java
SlaCalculator calculator = new SlaCalculator();   // создали объект
calculator.responseTimeHours("HIGH");             // вызвали метод объекта
```

## `static`: метод класса, а не объекта

`static`-методу объект не нужен — его вызывают через имя класса:

```java
List.of("OPEN", "DONE");
Math.max(1, 2);
Assertions.assertEquals(2, actual);   // после import static — просто assertEquals(...)
```

В тестах `static` встретится у метода-источника `@MethodSource` и у констант: `static final long SEED = 42;`.

## Примитивы и ссылки: `a == b` или `a.equals(b)`

- Числа, `boolean`, `char` — двойным равно: `hours == 8`.
- Строки и другие объекты — через `equals`: `"OPEN".equals(status)`.
- Проверка на `null` — двойным равно: `code == null`.

Двойное равно `a == b` для объектов проверяет «тот же ли это объект», а не «одинаковое ли содержимое». Строка, прочитанная из файла или собранная во время работы программы, — обычно другой объект, и `code == "QA"` вернёт `false`, хотя текст одинаковый:

```java
String part = "Q";
String code = part + "A";      // строка собрана во время работы: "QA"
code == "QA";                  // false — разные объекты
code.equals("QA");             // true — одинаковый текст
```

Иногда двойное равно для строк случайно даёт `true` (например, `"Q" + "A" == "QA"` — компилятор склеил литералы заранее). Поэтому на двойное равно для строк не опираемся никогда: только `equals`.

`"OPEN".equals(status)` безопасно при `status == null`. `status.equals("OPEN")` при `null` упадёт с `NullPointerException`.

## `null` — «значения нет»

`null` может лежать в любой переменной-ссылке: `String`, `List`, `Task`. Вызов метода у `null` падает:

```java
String code = null;
code.length();   // NullPointerException
```

Поэтому проверка на `null` идёт первой, а тест на `null` — один из первых негативных тестов.

## Коллекции: тип в угловых скобках

```java
List<String> statuses = List.of("OPEN", "DONE");          // список строк, менять нельзя
List<String> copy = new ArrayList<>(statuses);             // изменяемая копия
Set<String> unique = Set.of("OPEN", "DONE");               // без повторов, порядок не гарантирован
Map<String, Integer> counts = Map.of("OPEN", 2);           // ключ → значение

for (String status : statuses) {                           // «для каждого status из statuses»
    System.out.println(status);
}
```

`List.of`, `Set.of`, `Map.of` не принимают `null`: `Set.of("OPEN").contains(null)` бросает `NullPointerException`.

## `Integer` в `Map` и `null`

`Map` хранит объекты, поэтому вместо `int` там `Integer`. Для отсутствующего ключа `get` вернёт `null`:

```java
Map<String, Integer> counts = Map.of("OPEN", 2);

Integer done = counts.get("DONE");   // null — ключа нет
int broken = counts.get("DONE");     // NullPointerException: null нельзя положить в int
int safe = counts.getOrDefault("DONE", 0);   // 0
```

## Строки: пустая и пробельная

```java
"".isEmpty();       // true
"".isBlank();       // true
"   ".isEmpty();    // false — пробелы тоже символы
"   ".isBlank();    // true
"QA".isBlank();     // false
// у null оба метода падают с NullPointerException
```

`""` — пустая строка, `"   "` — пробельная. Это разные тестовые случаи. Строки не меняются: `code.trim()` возвращает новую строку, исходная остаётся прежней.

## Стрелка `->`

В `switch` стрелка значит «для этого варианта — такой результат»:

```java
return switch (role) {
    case "ADMIN", "MANAGER", "TESTER" -> true;   // несколько вариантов через запятую
    case "VIEWER" -> false;
    default -> false;
};
```

В проверках стрелка — лямбда, «кусок кода, который выполнят позже». Разбирать её не нужно, достаточно узнавать шаблон:

```java
assertAll(
    () -> assertEquals("QA-1", task.key()),
    () -> assertEquals("Write tests", task.title()));
assertThrows(IllegalArgumentException.class, () -> workflow.close(task));
```

## Класс, объект и record

```java
public class Task {
    private final String key;             // поле: private — видно только внутри класса
                                          //       final — после конструктора не меняется
    public Task(String key) {             // конструктор: имя = имя класса, без типа результата
        this.key = key;                   // this.key — поле, key — параметр
    }
}

public record User(String login, String name) { }   // класс для данных — одной строкой
```

- Поле record читается методом с тем же именем: `user.login()`, `task.key()`.
- У обычного класса `equals`, если его не написать самим, сравнивает ссылки: два `Task` с одинаковыми полями не равны. Record сравнивает значения полей.

## `enum`: закрытый список значений

```java
public enum TaskStatus { OPEN, IN_PROGRESS, DONE, CANCELLED }

TaskStatus status = TaskStatus.OPEN;
if (status == TaskStatus.DONE) { ... }          // enum сравнивают через ==
TaskStatus parsed = TaskStatus.valueOf("DONE"); // строка → enum; неизвестная строка — IllegalArgumentException
```

Переменная enum может быть `null`, и `switch` на ней упадёт с `NullPointerException`.

## Исключения: `throw`, `throws`, `try`/`catch`

```java
throw new IllegalArgumentException("Task title is required");   // сообщить: дальше нельзя

public List<String> readLines(Path path) throws IOException {   // предупредить вызывающего
    return Files.readAllLines(path);
}

try {
    reader.readLines(path);
} catch (IOException e) {                                       // обработать
    throw new IllegalStateException("Cannot read " + path, e);
}
```

- Checked (`IOException`) — компилятор требует `throws` или `try`/`catch`. Unchecked (`IllegalArgumentException`, `NullPointerException`) — не требует.
- Пустой `catch {}` прячет ошибку: тест станет зелёным там, где должен упасть.

## `try` с ресурсом

То, что нужно закрыть (соединение с БД, браузер), объявляют в скобках после `try` — Java закроет его сама, даже если внутри упадёт проверка:

```java
try (Connection connection = DriverManager.getConnection(url)) {
    // работа с базой
}
```

## Пакет и импорт

`package training.tracker;` в начале файла должен совпадать с папкой `src/.../training/tracker/`. Класс из другого пакета подключается импортом:

```java
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;   // импорт метода
```

IDE подсказывает импорт по Alt+Enter (на macOS — Option+Enter). Если предлагает несколько вариантов `assertThat` — для AssertJ выбирайте `org.assertj.core.api.Assertions`.

## Как читать ошибку компиляции

Код не собрался — тесты даже не запускались. Ищите в выводе путь к файлу и номер строки:

```text
SlaCalculatorTest.java:12: error: cannot find symbol
        int actual = calculator.responceTimeHours("HIGH");
                               ^
  symbol:   method responceTimeHours(String)
```

- `cannot find symbol` — опечатка в имени, нет импорта или переменная вне области видимости.
- `incompatible types` — не тот тип: строка вместо числа, `int` вместо `boolean`.
- `missing return statement` — метод с результатом не дошёл до `return`.
- `';' expected` — пропущена точка с запятой в конце указанной строки, `^` показывает место.
- `class X is public, should be declared in a file named X.java` — имя файла не совпадает с именем класса.

Исправляйте первую ошибку в списке: следующие часто вызваны ею.

## Три вида красного

- `expected: <2> but was: <3>` — тест запустился, проверка не прошла.
- `error: cannot find symbol` — код не скомпилировался, тестов не было.
- `NullPointerException`, `IllegalArgumentException` — код упал во время выполнения. Смотрим stack trace: первая строка с пакетом `training.tracker`.
