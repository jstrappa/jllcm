package test.llcm2;

import static org.junit.Assert.*;

import java.util.BitSet;
import java.util.Collection;

import org.junit.Test;

import feature.BinaryFeature;
import feature.Feature;
import measure.LLCM2;

public class GenerateReducedContextsFromFeature {
	static int N;
	static Collection<Feature> contexts;

	@Test
	public void test() {

		Feature f = new BinaryFeature(6);
		Feature g = new BinaryFeature(6);

		f.addVal(0, 1);
		f.addVal(3, 1);

		g.addVal(0, 1);
		g.addVal(2, 1);
		g.addVal(3, 1);
		g.addVal(5, 1);

		System.out.println("f: " + f.toString());

		BitSet S = (BitSet) f.getVar().clone();
		S.or(g.getVar());
		System.out.println("S: " + S.toString());

		contexts = LLCM2.generateReducedContextsFromFeature(S, f, N);

		System.out.println("Set of contexts");
		for (Feature context : contexts) {
			System.out.println(context.toString());
		}
	}

}
