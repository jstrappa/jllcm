package test.llcm;

import static org.junit.Assert.*;

import java.util.Collection;

import measure.ConfusionMatrix;
import measure.LLCM;

import org.junit.Test;

import util.FileUtil;
import feature.Feature;

public class LLCMTest {

	@Test
	public void test() {
		String filename1, filename2;
		int n;
		ConfusionMatrix expectedCM;

		// TEST
		expectedCM = new ConfusionMatrix(4, 15, 5, 0, 0);
		filename1 = "examples/mini.mn";
		filename2 = "examples/mini2.mn";
		n = 3;

		testTemplate(expectedCM, filename1, filename2, n);

		// TEST
		expectedCM = new ConfusionMatrix(4, 20, 0, 0, 0);
		filename1 = "examples/mini.mn";
		filename2 = "examples/mini.mn";
		n = 3;

		testTemplate(expectedCM, filename1, filename2, n);

		// TEST
		expectedCM = new ConfusionMatrix(9, 15, 0, 0, 0);
		filename1 = "examples/mini2.mn";
		filename2 = "examples/mini2.mn";
		n = 3;

		testTemplate(expectedCM, filename1, filename2, n);

	}

	/*
	 * Tests the brute force algorithm and compares the obtained confusion
	 * matrix with the one given.
	 */
	public void testTemplate(ConfusionMatrix expectedCM, String filename1,
			String filename2, int n) {

		// Read models
		Collection<Feature> model1 = FileUtil.readFeatureSet(filename1);
		Collection<Feature> model2 = FileUtil.readFeatureSet(filename2);

		ConfusionMatrix cm = LLCM.computeConfusionMatrix(model1, model2, n);

		System.out.println("Expected: " + expectedCM.toString()
				+ "\nObtained: " + cm.toString() + "\n");

		assertEquals(expectedCM.getTruePositives(), cm.getTruePositives());
		assertEquals(expectedCM.getTrueNegatives(), cm.getTrueNegatives());
		assertEquals(expectedCM.getFalsePositives(), cm.getFalsePositives());
		assertEquals(expectedCM.getFalseNegatives(), cm.getFalseNegatives());

		// Only test number of comparisons if it is specified
		if (expectedCM.getNumberOfComparisons() > 0)
			assertEquals(expectedCM.getNumberOfComparisons(),
					cm.getNumberOfComparisons());

	}

}
