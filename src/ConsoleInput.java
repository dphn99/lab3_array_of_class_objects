import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Scanner;

// Читає дані з консолі з перевіркою.
public class ConsoleInput {

    private final Scanner scanner;

    public ConsoleInput(Scanner scanner) {
        this.scanner = scanner;
    }

    public int readInt(String prompt, int min, int max) {
        while (true) {
            String text = readLine(prompt);
            try {
                return parseInt(text, min, max);
            } catch (NumberFormatException e) {
                System.out.println("Помилка типу даних: «" + text + "» не є цілим числом.");
            } catch (InvalidInputException e) {
                System.out.println("Помилка діапазону: " + e.getMessage());
            }
        }
    }

    // Перетворює текст на число і перевіряє діапазон.
    static int parseInt(String text, int min, int max) throws InvalidInputException {
        int value = Integer.parseInt(text);
        if (value < min || value > max) {
            throw new InvalidInputException("потрібно число від " + min + " до " + max + ".");
        }
        return value;
    }

    // Читає непорожній текст. Пробіли на початку та в кінці відкидаються.
    public String readText(String prompt) {
        while (true) {
            try {
                return requireNotEmpty(readLine(prompt));
            } catch (InvalidInputException e) {
                System.out.println("Помилка введення: " + e.getMessage());
            }
        }
    }

    static String requireNotEmpty(String text) throws InvalidInputException {
        if (text.isEmpty()) {
            throw new InvalidInputException("значення не може бути порожнім.");
        }
        return text;
    }

    // Шаблон H:mm приймає і 9:30, і 09:30. Режим STRICT відхиляє 24:00,
    // яке звичайний режим перетворив би на 00:00.
    private static final DateTimeFormatter TIME_INPUT =
            DateTimeFormatter.ofPattern("H:mm").withResolverStyle(ResolverStyle.STRICT);

    // Читає час у форматі ГГ:ХХ, наприклад 14:30.
    public LocalTime readTime(String prompt) {
        while (true) {
            String text = readLine(prompt);
            try {
                // DateTimeParseException виникає і для неправильного формату (abc),
                // і для неіснуючого часу (25:70).
                return LocalTime.parse(text, TIME_INPUT);
            } catch (DateTimeParseException e) {
                System.out.println("Помилка формату часу: «" + text
                        + "» не є часом від 00:00 до 23:59 у форматі ГГ:ХХ.");
            }
        }
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
