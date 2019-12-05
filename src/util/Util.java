package util;

import java.util.BitSet;
import java.util.Collection;
import java.util.HashSet;

import feature.BinaryFeature;
import feature.Feature;

public class Util {

	/*
	 * Generates all binary canonical assignments to the domain variables.
	 * 
	 * Number of variables limited by BitSet capacity.
	 * Number of contexts limited by long representation.
	 */
	public static Collection<Feature> generateAllContexts(int n) {
		Collection<Feature> contexts = new HashSet<Feature>((int) Math.pow(2, n)); 		
		Feature context;
		BitSet ones = new BitSet();
		ones.flip(0, n);
		//System.out.println("all ones: "+ones.toString()); //TODO remove
		
		for (long i = 0; i < Math.pow(2, n); i++) {
			context = new BinaryFeature(ones,convert(i,n)); 
			contexts.add(context);
		}		
		
		return contexts;
	}
	
	
	public static BitSet convert(long value, int numberOfVariables) {
		BitSet bits = new BitSet(numberOfVariables);
		int index = 0;
		while (value != 0L) {
			if (value % 2L != 0) {
				bits.set(index);
			}
			++index;
			value = value >>> 1;
		}
		return bits;
	}
	

}
