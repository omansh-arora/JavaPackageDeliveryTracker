package cmpt213.assignment4.packagedeliveries.webappserver.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Subclass of Package to handle Book objects
 *
 * @author Omansh
 */
public class BookPackage extends PkgInfo {

    private final String type = "Book";
    private String author;

    /**
     * Constructor for superclass
     *
     * @param name
     * @param notes
     * @param price
     * @param weight
     * @param deliveryDate
     */
    public BookPackage(String name, String notes, double price, double weight, LocalDateTime deliveryDate) {
        super(name, notes, price, weight, deliveryDate);
    }

    /**
     * Sets author of book object
     *
     * @param auth
     */
    public void setAuthor(String auth) {
        this.author = auth;
    }

    @Override
    public void setFee(double fee) {

    }

    @Override
    public void setExpiryDate(LocalDateTime expiryDate) {

    }

    public String getType() {
        return type;
    }

    /**
     * Converts package to string
     *
     * @return toString
     */
    @Override
    public String toString() {

        //Turning boolean into string value
        String delivered = "No";
        if (isDelivered) {
            delivered = "Yes";
        }

        //Formatting date time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDateTime = deliveryDate.format(formatter);

        //Duration between delivery date and current date
        Duration duration = Duration.between(LocalDateTime.now(), deliveryDate);
        String durationInDays = duration.toDays() + " days";

        return "Package: " + name + "\n" + "Author: " + author + "\n" + "Notes: " + notes + "\n" +
                "Price: $" + String.format("%.2f", price) + "\n" + "Weight: " + String.format("%.2f", weight) + "kg\n" +
                "Has been delivered: " + delivered + "\n" + "Delivery date: "
                + formatDateTime + "\n" + "Days until delivery date: " + durationInDays + "\n";
    }

    @Override
    public String toString1() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDateTime = deliveryDate.format(formatter);

        return name + "\n" + "Author: " + author + "\n" + "Notes: " + notes + "\n" +
                "Price: $" + String.format("%.2f", price) + "\n" + "Weight: " + String.format("%.2f", weight) + "kg\n" +
                "Delivery date: "
                + formatDateTime + "\n";

    }

    @Override
    public int compareTo(PerishablePackage o) {
        return 0;
    }
}
