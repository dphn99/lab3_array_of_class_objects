import java.time.LocalTime;
import java.util.Arrays;

public class TrainSchedule {

    private final Train[] trains;

    // Зберігає копію, щоб зміни у вихідному масиві не впливали на розклад
    public TrainSchedule(Train[] trains) {
        this.trains = trains.clone();
    }

    public Train[] getTrains() {
        return trains.clone();
    }

    /**
     * Завдання 1. Повертає список поїздів, які мають загальні місця.
     * Розмір результату наперед невідомий, тому знайдені поїзди спочатку
     * записуються в тимчасовий масив, а потім масив обрізається до їх кількості.
     */
    public Train[] findWithGeneralSeats() {
        Train[] found = new Train[trains.length];
        int count = 0;
        for (Train train : trains) {
            if (train.hasGeneralSeats()) {
                found[count++] = train;
            }
        }
        return Arrays.copyOf(found, count);
    }

    /**
     * Завдання 2. Повертає список поїздів, які слідують до заданого пункту
     * призначення та відправляються після заданого часу.
     */
    public Train[] findByDestinationAfter(String destination, LocalTime time) {
        Train[] found = new Train[trains.length];
        int count = 0;
        for (Train train : trains) {
            if (train.goesTo(destination) && train.departsAfter(time)) {
                found[count++] = train;
            }
        }
        return Arrays.copyOf(found, count);
    }
}
