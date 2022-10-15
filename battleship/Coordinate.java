package battleship;

public class Coordinate {
    int x;
    int y;
    Coordinate(String str) throws ArgumentException {
        try {
            y = (str.charAt(0)) - 'A';
            x = Integer.parseInt(str.substring(1)) - 1;
            if (x < 0 || x > 9 || y < 0 || y > 9)
                throw (new ArgumentException());
        } catch (Exception e) {
            throw (new ArgumentException());
        }
    }
}