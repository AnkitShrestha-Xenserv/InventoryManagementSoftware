import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Ankit on 3/28/2021.
 */
public class DateCheck {
    public static void main(String[] args) {
        System.out.println(new Timestamp((new Date()).getTime()));
    }
}