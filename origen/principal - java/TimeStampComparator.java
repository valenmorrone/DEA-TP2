package aed;

import java.util.Comparator;

public class TimeStampComparator implements Comparator<Traslado>{ //O(1)
	@Override
	  public int compare(Traslado t1, Traslado t2){
		  return Integer.compare(t2.timestamp(),t1.timestamp());
	  }
}