package cmpt213.assignment4.packagedeliveries.client.model;

import java.time.LocalDateTime;

/**
 * Class resposible for returning instances of various package types
 *
 * @author Omansh
 */
public class PackageFactory {

    /**
     * Returns instances of various package objects
     *
     * @param pType
     * @param name
     * @param notes
     * @param price
     * @param weight
     * @param deliveryDate
     * @return
     */
    public static PkgInfo getPackage(int pType, String name, String notes, double price, double weight, LocalDateTime deliveryDate) {

        if (pType == 1) {

            return new BookPackage(name, notes, price, weight, deliveryDate);

        } else if (pType == 2) {

            return new PerishablePackage(name, notes, price, weight, deliveryDate);

        } else if (pType == 3) {

            return new ElectronicPackage(name, notes, price, weight, deliveryDate);

        } else {
            return null;
        }

    }
}
