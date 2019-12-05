package test.llcm;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.HashSet;

import measure.LLCM;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import feature.BinaryFeature;
import feature.Feature;

/*
 * TODO verification could be automated with a method that 
 * generates all canonical contexts from a feature and compares 
 * the canonical contexts of sets H and P.
 */
public class PartitionTest {
	static Feature h1, h2, h3, h4;
	static Collection<Feature> H;
	static int N;

	@Before
	public void setUp() throws Exception {
		N = 3;
		LLCM.setN(N);
		h1 = new BinaryFeature(N);
		h2 = new BinaryFeature(N);
		h3 = new BinaryFeature(N);
		h4 = new BinaryFeature(N);

		H = new HashSet<Feature>();
	}

	@After
	public void tearDown() {
		h1 = h2 = h3 = h4 = null;
		H = null;
	}

	public void testTemplate(Collection<Feature> H, String expected) {

		//
		System.out
				.println("TEST ================================================");

		System.out.println("H: " + H.toString());

		Collection<Feature> result;
		result = LLCM.partition(H);
		System.out.println("P: " + result.toString());
		if (expected != null)
			assertEquals(result.toString(), expected);

		System.out
				.println("END =================================================");

		assert (true);
	}

	@Test
	public void test1() {
		h1.addVal(0, 0);
		h2.addVal(2, 0);

		H.add(h1);
		H.add(h2);

		testTemplate(H, "[+v0_1 +v2_0, +v0_0]");
	}

	@Test
	public void test2() {
		h1.addVal(0, 0);
		h1.addVal(1, 0);
		h2.addVal(0, 0);
		h2.addVal(2, 0);

		H.add(h1);
		H.add(h2);

		testTemplate(H, "[+v0_0 +v2_0, +v0_0 +v1_0 +v2_1]");
	}

}
