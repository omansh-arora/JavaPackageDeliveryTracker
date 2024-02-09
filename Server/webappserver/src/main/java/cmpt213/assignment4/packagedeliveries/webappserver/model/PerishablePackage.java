package cmpt213.assignment4.packagedeliveries.webappserver.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Subclass of Package to handle Electronic objects
 *
 * @author Omansh
 */
public class PerishablePackage extends PkgInfo {
    private final String type = "Perishable";
    private LocalDateTime expiryDate;

    /**
     * Constructor for super class
     *
     * @param name
     * @param notes
     * @param price
     * @param weight
     * @param deliveryDate
     */
    public PerishablePackage(String name, String notes, double price, double weight, LocalDateTime deliveryDate) {
        super(name, notes, price, weight, deliveryDate);
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

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDateTime1 = expiryDate.format(formatter);

        //Duration between delivery date and current date
        Duration duration = Duration.between(LocalDateTime.now(), deliveryDate);
        String durationInDays = duration.toDays() + " days";

        return "Package: " + name + "\n" + "Expiry date: " + formatDateTime1 + "\n" + "Notes: " + notes + "\n" +
                "Price: $" + String.format("%.2f", price) + "\n" + "Weight: " + String.format("%.2f", weight) + "kg\n" +
                "Has been delivered: " + delivered + "\n" + "Delivery date: "
                + formatDateTime + "\n" + "Days until delivery date: " + durationInDays + "\n";
    }

    @Override
    public void setAuthor(String auth) {

    }

    @Override
    public void setFee(double fee) {

    }

    /**
     * Compares expiry dates of Perishable packages
     *
     * @param o the object to be compared.
     * @return
     */
    public int compareTo(PerishablePackage o) {
        return getExpiryDate().compareTo(o.getExpiryDate());
    }

    /**
     * Gets expiry date
     *
     * @return
     */
    private LocalDateTime getExpiryDate() {

        return expiryDate;

    }

    /**
     * Sets expiry date of book object
     *
     * @param expiryDate
     */
    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString1() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDateTime = deliveryDate.format(formatter);

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDateTime1 = expiryDate.format(formatter);

        return name + "\n" + "Expiry date: " + formatDateTime1 + "\n" + "Notes: " + notes + "\n" +
                "Price: $" + String.format("%.2f", price) + "\n" + "Weight: " + String.format("%.2f", weight) + "kg\n" + "Delivery date: "
                + formatDateTime + "\n";

    }
}
