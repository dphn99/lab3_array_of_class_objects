import java.nio.charset.Charset;
import java.time.LocalTime;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

/**
 * Лабораторна робота №3. Робота з масивами об'єктів класів. Обробка виключень у Java. Варіант 8.
 * <p>
 * Програма формує масив поїздів із випадковими даними, виводить його у вигляді таблиці
 * і виконує два види пошуку: поїзди із загальними місцями та поїзди до заданого
 * пункту призначення, які відправляються після заданого часу.
 */
public class Main {

    private static final int MIN_TRAINS = 5;
    private static final int MAX_TRAINS = 10;

    private static final String[] DESTINATIONS = {"Львів", "Одеса", "Харків", "Дніпро"};

    private static final String TABLE_LINE =
            "+----+-------------------+-------+--------------+----------+-------+----------+------+";
    private static final String TABLE_ROW =
            "| %2s | %-17s | %-5s | %-12s | %8s | %5s | %8s | %4s |%n";

    public static void main(String[] args) {
        System.out.println("Розробник: Ярмак Ю. О.");
        System.out.println();

        Charset inputCharset = Charset.forName(System.getProperty("stdin.encoding", "UTF-8"));
        ConsoleInput input = new ConsoleInput(new Scanner(System.in, inputCharset));

        try {
            int count = input.readInt("Введіть кількість поїздів (від " + MIN_TRAINS + " до " + MAX_TRAINS + "): ",
                    MIN_TRAINS, MAX_TRAINS);
            TrainSchedule schedule = new TrainSchedule(createRandomTrains(count, new Random()));

            System.out.println();
            System.out.println("Масив поїздів (дані згенеровано випадково):");
            printTable(schedule.getTrains());

            int choice;
            do {
                printMenu();
                choice = input.readInt("Ваш вибір: ", 0, 2);
                if (choice == 1) {
                    showResult("Поїзди, які мають загальні місця:",
                            schedule.findWithGeneralSeats(), "поїзди із загальними місцями");
                } else if (choice == 2) {
                    searchByDestinationAndTime(schedule, input);
                }
            } while (choice != 0);
            System.out.println("Роботу програми завершено.");
        } catch (NoSuchElementException e) {
            System.out.println();
            System.out.println("Введення даних перервано. Роботу програми завершено.");
        }
    }

    /**
     * Створює масив із count поїздів з випадковими даними.
     * Приблизно половина поїздів отримує 0 загальних місць.
     */
    static Train[] createRandomTrains(int count, Random random) {
        Train[] trains = new Train[count];
        int number = 1 + random.nextInt(50);
        for (int i = 0; i < count; i++) {
            String destination = DESTINATIONS[random.nextInt(DESTINATIONS.length)];
            LocalTime time = LocalTime.of(random.nextInt(24), 5 * random.nextInt(12));
            int generalSeats = random.nextBoolean() ? 0 : 10 + random.nextInt(71);
            trains[i] = new Train(destination, String.format("%03d", number), time,
                    generalSeats, random.nextInt(37), random.nextInt(55), random.nextInt(19));
            // Номер лише зростає, тому номери поїздів у масиві не повторюються.
            number += 1 + random.nextInt(30);
        }
        return trains;
    }

    /** Запитує параметри пошуку за завданням 2, виконує пошук і виводить результат. */
    private static void searchByDestinationAndTime(TrainSchedule schedule, ConsoleInput input) {
        String destination = input.readText("Введіть пункт призначення: ");
        LocalTime time = input.readTime("Введіть час у форматі ГГ:ХХ (поїзд має відправлятися пізніше): ");
        String timeText = time.format(Train.TIME_FORMAT);

        Train[] found = schedule.findByDestinationAfter(destination, time);
        showResult("Поїзди, які слідують до пункту «" + destination + "» та відправляються після " + timeText + ":",
                found, "пункт призначення «" + destination + "», відправлення після " + timeText);
    }

    // Виводить знайдені поїзди таблицею або повідомлення, якщо нічого не знайдено.
    private static void showResult(String title, Train[] found, String criterion) {
        System.out.println();
        if (found.length == 0) {
            System.out.println("Немає даних за заданим критерієм пошуку: " + criterion + ".");
            return;
        }
        System.out.println(title);
        printTable(found);
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("Оберіть дію:");
        System.out.println("  1 - поїзди, які мають загальні місця");
        System.out.println("  2 - поїзди до заданого пункту призначення після заданого часу");
        System.out.println("  0 - завершити роботу");
    }

    // Виводить масив поїздів у вигляді таблиці.
    static void printTable(Train[] trains) {
        System.out.println(TABLE_LINE);
        System.out.printf(TABLE_ROW, "№", "Пункт призначення", "Номер", "Відправлення",
                "Загальні", "Купе", "Плацкарт", "СВ");
        System.out.println(TABLE_LINE);
        for (int i = 0; i < trains.length; i++) {
            Train t = trains[i];
            System.out.printf(TABLE_ROW, i + 1, t.getDestination(), t.getNumber(),
                    t.getDepartureTime().format(Train.TIME_FORMAT), t.getGeneralSeats(),
                    t.getCoupeSeats(), t.getPlatzkartSeats(), t.getSvSeats());
        }
        System.out.println(TABLE_LINE);
    }
}
