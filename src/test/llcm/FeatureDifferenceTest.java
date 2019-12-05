package test.llcm;

import static org.junit.Assert.*;

import java.util.Collection;

import measure.LLCM;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import feature.BinaryFeature;
import feature.Feature;

public class FeatureDifferenceTest {
	static Feature f;
	static Feature g;
	static int N;

	@Before
	public void setUp() throws Exception {
		N = 3;
		LLCM.setN(N);
		f = new BinaryFeature(N);
		g = new BinaryFeature(N);

	}
	
	@After
	public void tearDown(){
		f = g = null;
	}

	/*
	 * For each test, compute the partitions for difference, intersection
	 * and complement of two given features and compare the results
	 * to the strings given as arguments (d, i and c, respectively). 
	 */
	public void testTemplate(Feature f, Feature g, String d, String i, String c) {

		//
		System.out
				.println("TEST ================================================");
		System.out.println("f: " + f.toString());
		System.out.println("g: " + g.toString() + "\n");

		Collection<Feature> result;
		result = LLCM.featureDifference(f, g, "difference");
		System.out.println("D: " + result.toString());
		if (d != null)
			assertEquals(result.toString(), d);

		result = LLCM.featureDifference(f, g, "intersection");
		System.out.println("I: " + result.toString());
		if (i != null)
			assertEquals(result.toString(), i);

//		result = LLCM.featureDifference(f, g, "complement");
//		System.out.println("C: " + result.toString());
//		if (c != null)
//			assertEquals(result.toString(), c);

		System.out
				.println("END =================================================");

		assert (true);
	}

	@Test
	public void test1() {
		f.addVal(0, 0);
		f.addVal(1, 0);

		g.addVal(0, 0);
		g.addVal(1, 0);
		g.addVal(2, 0);

		testTemplate(f, g, "[+v0_0 +v1_0 +v2_1]", "[+v0_0 +v1_0 +v2_0]", "[]");
	}

	@Test
	public void test2() {
		f.addVal(0, 0);
		f.addVal(1, 0);
		f.addVal(2, 0);

		g.addVal(0, 0);
		g.addVal(1, 0);

		testTemplate(f, g, "[]", "[+v0_0 +v1_0 +v2_0]", "[+v0_0 +v1_0 +v2_1]");
	}

	@Test
	public void test3() {
		f.addVal(0, 1);
		f.addVal(1, 0);

		g.addVal(0, 0);
		g.addVal(1, 0);

		testTemplate(f, g, "[+v0_1 +v1_0]", "[]", "[+v0_0 +v1_0]");
	}

	@Test
	public void test4() {
		f.addVal(0, 0);
		f.addVal(1, 0);

		g.addVal(2, 0);

		testTemplate(f, g, "[+v0_0 +v1_0 +v2_1]", "[+v0_0 +v1_0 +v2_0]",
				"[+v0_0 +v1_1 +v2_0, +v0_1 +v2_0]");
	}

	@Test
	public void test5() {
		g.addVal(0, 0);
		g.addVal(1, 0);

		f.addVal(2, 0);

		testTemplate(f, g, "[+v0_0 +v1_1 +v2_0, +v0_1 +v2_0]",
				"[+v0_0 +v1_0 +v2_0]", "[+v0_0 +v1_0 +v2_1]");
	}

	@Test
	public void test6() {
		g.addVal(0, 0);

		f.addVal(2, 0);

		testTemplate(f, g, null, null, null);
	}
}
