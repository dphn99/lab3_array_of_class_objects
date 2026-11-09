/**
 * Виключення для значень, які мають правильний тип, але не підходять за змістом
 * Окремий клас дозволяє обробити помилку діапазону своїм повідомленням.
 */
public class InvalidInputException extends Exception {

    private static final long serialVersionUID = 1L;

    public InvalidInputException(String message) {
        super(message);
    }
}
