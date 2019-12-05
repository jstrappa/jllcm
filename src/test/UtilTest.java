package test;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;

import feature.Feature;
import util.Util;

public class UtilTest {

	@Test
	public void test() {
		int n = 6;

		Collection<Feature> set = Util.generateAllContexts(n);

		System.out
				.println("Testing generation of all canonical assignments for"
						+ n + "binary variables.");
		
		System.out.println("Contexts: "+set.size());
		
		assertEquals((int)Math.pow(2, n), set.size());

		for (Feature feature : set) {
			System.out.println(feature.toString());
		}

		// Manual test
		assert (true);

	}
}
