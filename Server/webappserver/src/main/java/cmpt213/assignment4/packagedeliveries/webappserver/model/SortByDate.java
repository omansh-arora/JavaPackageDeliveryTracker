package cmpt213.assignment4.packagedeliveries.webappserver.model;

import java.util.Comparator;

public class SortByDate implements Comparator<PkgInfo> {

    public int compare(PkgInfo a, PkgInfo b) {
        return a.getDeliveryDate().compareTo(b.getDeliveryDate());
    }
}
