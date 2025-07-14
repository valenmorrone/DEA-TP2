package aed;

import java.util.Comparator;

public class SuperavitComparator implements Comparator<Ciudad> { //O(1)

    @Override
    public int compare(Ciudad c1, Ciudad c2) {
        if (c1.ganancia() - c1.perdida() == c2.ganancia() - c2.perdida()) {
            return Integer.compare(c2.id(), c1.id());
        }
        return Integer.compare(c1.ganancia() - c1.perdida(), c2.ganancia() - c2.perdida());
    }
}