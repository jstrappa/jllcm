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

public class FeatureMinusFeatureSetTest {
	static Feature f, g, h, i;
	static Collection<Feature> G;
	static int N;

	@Before
	public void setUp() throws Exception {
		N = 4;
		LLCM.setN(N);
		f = new BinaryFeature(N);
		g = new BinaryFeature(N);
		h = new BinaryFeature(N);
		i = new BinaryFeature(N);

		G = new HashSet<Feature>();
	}

	@After
	public void tearDown() {
		f = g = h = i = null;
		G = null;
	}

	public void testTemplate(Feature f, Collection<Feature> G, String expected) {

		//
		System.out
				.println("TEST ================================================");
		System.out.println("f: " + f.toString());
		System.out.println("G: " + G.toString() + "\n");

		Collection<Feature> result;
		result = LLCM.featureMinusFeatureSet(f, G);
		System.out.println("f \\ G: " + result.toString());
		if (expected != null)
		assertEquals(result.toString(), expected);

		System.out
				.println("END =================================================");

		assert (true);
	}

	@Test
	public void test1() {
		f.addVal(1, 0);
		g.addVal(2, 0);

		G.add(g);

		testTemplate(f, G,"[+v1_0 +v2_1]");
	}

	@Test
	public void test2() {
		f.addVal(1, 0);
		g.addVal(2, 0);
		h.addVal(3, 1);

		G.add(g);
		G.add(h);

		testTemplate(f, G,"[+v1_0 +v2_1 +v3_0]");
	}

	@Test
	public void test3() {
		f.addVal(1, 0);
		g.addVal(2, 0);
		h.addVal(3, 1);
		i.addVal(2, 1);

		G.add(g);
		G.add(h);
		G.add(i);

		testTemplate(f, G, "[]");
	}

	@Test
	public void test4() {
		f.addVal(1, 0);
		g.addVal(2, 0);
		g.addVal(1, 0);
		h.addVal(3, 1);

		G.add(g);
		G.add(h);

		testTemplate(f, G, "[+v1_0 +v2_1 +v3_0]");
	}
}
