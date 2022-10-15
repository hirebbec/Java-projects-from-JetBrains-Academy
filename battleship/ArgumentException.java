package battleship;

public class ArgumentException extends Exception {
    public ArgumentException() {
        super("Error! Wrong ship location! Try again:");
    }
}