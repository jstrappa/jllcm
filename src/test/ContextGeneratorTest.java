package test;

import static org.junit.Assert.*;

import java.util.Collection;

import measure.BruteForce;

import org.junit.BeforeClass;
import org.junit.Test;

import util.FileUtil;
import feature.Feature;

public class ContextGeneratorTest {

	static Collection<Feature> model;
	static int N;
	static Collection<Feature> contexts;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		model = FileUtil.readFeatureSet("examples/mini.mn");
		N = 7; // should also work
		N = 3;
	}

	@Test
	public void test() {
		System.out.println("Set of features:");
		for (Feature f : model) {
			System.out.println(f.toString());

		}

		contexts = BruteForce.generateContextsFromFeatureSet(model, N);

		System.out.println("Set of contexts");
		for (Feature context : contexts) {
			System.out.println(context.toString());
		}

	}

}
