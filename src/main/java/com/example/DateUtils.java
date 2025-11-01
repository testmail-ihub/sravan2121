import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    /**
     * Formats a given Date object into a String with the format "yyyy-MM-dd".
     * @param date The Date object to format.
     * @return A String representing the formatted date.
     */
    public static String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    /**
     * Returns the current timestamp as a long value.
     * @return The current timestamp in milliseconds.
     */
    public static long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }
}