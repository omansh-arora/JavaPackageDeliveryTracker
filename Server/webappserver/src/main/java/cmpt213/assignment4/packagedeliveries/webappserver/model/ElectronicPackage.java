package cmpt213.assignment4.packagedeliveries.webappserver.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Subclass of Package to handle Electronic objects
 *
 * @author Omansh
 */
public class ElectronicPackage extends PkgInfo {

    private final String type = "Electronic";
    private double fee;

    /**
     * Constructor for super class
     *
     * @param name
     * @param notes
     * @param price
     * @param weight
     * @param deliveryDate
     */
    public ElectronicPackage(String name, String notes, double price, double weight, LocalDateTime deliveryDate) {
        super(name, notes, price, weight, deliveryDate);
    }

    /**
     * Sets fee of book object
     *
     * @param fee
     */
    public void setFee(double fee) {

        this.fee = fee;

    }

    @Override
    public void setExpiryDate(LocalDateTime expiryDate) {

    }

    public String getType() {
        return type;
    }

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

        return "Package: " + name + "\n" + "Environmental handling fee: $" + String.format("%.2f", fee) + "\n" + "Notes: " + notes + "\n" +
                "Price: $" + String.format("%.2f", price) + "\n" + "Weight: " + String.format("%.2f", weight) + "kg\n" +
                "Has been delivered: " + delivered + "\n" + "Delivery date: "
                + formatDateTime + "\n" + "Days until delivery date: " + durationInDays + "\n";
    }

    @Override
    public String toString1() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDateTime = deliveryDate.format(formatter);

        return name + "\n" + "Environmental handling fee: $" + String.format("%.2f", fee) + "\n" + "Notes: " + notes + "\n" +
                "Price: $" + String.format("%.2f", price) + "\n" + "Weight: " + String.format("%.2f", weight) + "kg\n" +
                "Delivery date: "
                + formatDateTime + "\n";

    }

    @Override
    public void setAuthor(String auth) {

    }


    @Override
    public int compareTo(PerishablePackage o) {
        return 0;
    }
}
