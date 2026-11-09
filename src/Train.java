import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Поїзд: пункт призначення, номер поїзда, час відправлення
 * та кількість місць кожного типу (загальних, купе, плацкарт, СВ).
 * <p>
 * Об'єкт незмінний: значення полів задаються один раз у конструкторі.
 */
public class Train {

    // Формат часу для виводу, наприклад 07:05.
    public static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    private final String destination;
    private final String number;
    private final LocalTime departureTime;
    private final int generalSeats;
    private final int coupeSeats;
    private final int platzkartSeats;
    private final int svSeats;

    // Створює поїзд і перевіряє його дані.
    public Train(String destination, String number, LocalTime departureTime,
                 int generalSeats, int coupeSeats, int platzkartSeats, int svSeats) {
        if (destination == null || destination.isBlank()) {
            throw new IllegalArgumentException("Пункт призначення не може бути порожнім.");
        }
        if (number == null || number.isBlank()) {
            throw new IllegalArgumentException("Номер поїзда не може бути порожнім.");
        }
        if (departureTime == null) {
            throw new IllegalArgumentException("Час відправлення не задано.");
        }
        this.destination = destination.trim();
        this.number = number.trim();
        this.departureTime = departureTime;
        this.generalSeats = requireNotNegative(generalSeats, "загальних");
        this.coupeSeats = requireNotNegative(coupeSeats, "купе");
        this.platzkartSeats = requireNotNegative(platzkartSeats, "плацкарт");
        this.svSeats = requireNotNegative(svSeats, "СВ");
    }

    // Кількість місць не може бути від'ємною, тому таке значення відхиляємо одразу.
    private static int requireNotNegative(int seats, String seatType) {
        if (seats < 0) {
            throw new IllegalArgumentException("Кількість місць (" + seatType + ") не може бути від'ємною.");
        }
        return seats;
    }

    // Поїзд має загальні місця, якщо їх кількість більша за нуль.
    public boolean hasGeneralSeats() {
        return generalSeats > 0;
    }

    // Перевіряє пункт призначення без урахування регістру
    public boolean goesTo(String place) {
        return destination.equalsIgnoreCase(place.trim());
    }

    public boolean departsAfter(LocalTime time) {
        return departureTime.isAfter(time);
    }

    public String getDestination() {
        return destination;
    }

    public String getNumber() {
        return number;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public int getGeneralSeats() {
        return generalSeats;
    }

    public int getCoupeSeats() {
        return coupeSeats;
    }

    public int getPlatzkartSeats() {
        return platzkartSeats;
    }

    public int getSvSeats() {
        return svSeats;
    }
}
