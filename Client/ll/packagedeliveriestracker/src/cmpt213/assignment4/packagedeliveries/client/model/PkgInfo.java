package cmpt213.assignment4.packagedeliveries.client.model;

import java.time.LocalDateTime;

/**
 * A class for holding package information
 *
 * @author Omansh
 */

public abstract class PkgInfo implements Comparable<PerishablePackage> {

    protected final String name;
    protected final String notes;
    protected final double price;
    protected final double weight;
    protected final LocalDateTime deliveryDate;
    protected boolean isDelivered;

    //Constructors

    /**
     * Constructor to create a package that takes date time as a single variable
     *
     * @param name
     * @param notes
     * @param price
     * @param weight
     * @param deliveryDate LocalDateTime variable
     */
    public PkgInfo(String name, String notes, double price, double weight, LocalDateTime deliveryDate) {
        this.name = name;
        this.notes = notes;
        this.price = price;
        this.weight = weight;
        this.isDelivered = false;
        this.deliveryDate = deliveryDate;
    }

//    public ArrayList<Package> addBook()

    //Public methods

    /**
     * Returns delivery date
     *
     * @return delivery date
     */
    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    /**
     * Returns delivery status
     *
     * @return delivery status
     */
    public boolean isDelivered() {
        return isDelivered;
    }

    /**
     * Sets delivered
     */
    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }

    /**
     * Returns package name
     *
     * @return package name
     */
    public String getName() {
        return name;
    }

    public String getNotes() {
        return notes;
    }

    public double getPrice() {
        return price;
    }

    public double getWeight() {
        return weight;
    }

    /**
     * Converts package to string
     *
     * @return toString
     */
    @Override
    public abstract String toString();

    public abstract String toString1();

    public abstract void setAuthor(String auth);

    public abstract void setFee(double fee);

    public abstract void setExpiryDate(LocalDateTime expiryDate);

    public abstract String getType();

    public void changeDelivered() {
        isDelivered = !isDelivered;
    }
}
