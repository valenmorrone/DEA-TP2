package aed;

import java.util.Comparator;

public class ReditoComparator implements Comparator<Traslado> { //O(1)

    @Override
    public int compare(Traslado t1, Traslado t2) {
        if (t1.gananciaNeta() == t2.gananciaNeta()) {
            return Integer.compare(t2.id(), t1.id());
        } else {
            return Integer.compare(t1.gananciaNeta(), t2.gananciaNeta());
        }
    }
}