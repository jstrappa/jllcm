package test;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;

import feature.Feature;
import util.FileUtil;

public class FileUtilTest {

	private Collection<Feature> features;

	@Test
	public void test() {

		System.out
				.println("Test: parsing log linear without parameters and without leading space.");
		String filename = "examples/no_parameters.mn";
		features = FileUtil.readFeatureSet(filename);

		System.out
				.println("Test: parsing log linear without parameters and with leading spaces.");
		filename = "examples/no_parameters_leading_space.mn";
		features = FileUtil.readFeatureSet(filename);

		System.out.println("Test: parsing log linear with parameters.");
		filename = "examples/with_parameters1.mn";
		features = FileUtil.readFeatureSet(filename);
		filename = "examples/with_parameters.mn";
		features = FileUtil.readFeatureSet(filename);

		// For debugging
		// for (Feature f : features) {
		// System.out.println(f.toString());
		// }

		System.out
				.println("Testing number of variables and number of features.");
		assertEquals(50, features.size());
		assertEquals(6, features.iterator().next().getNumberOfDomainVariables());
		

		// If there are no exceptions assume the test was successful
		assert (true);
	}

}
