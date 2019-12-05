package measure;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

import util.FileUtil;
import feature.Feature;

public class Main {

	/*
	 * Log-linear models comparison measure
	 * 
	 * From command line: java -jar jllcm.jar model1 model2 n [method]
	 * 
	 * @param model1 , or .mn path of true model
	 * 
	 * @param model2 , or .mn path of learned model
	 * 
	 * @param number of binary variables in domain
	 * 
	 * @param method (optional) bf, llcm or both (default llcm)
	 */
	public static void main(String[] args) {
		if (args.length < 3) {
			System.out.println("Usage: ");
			System.out.println("java -jar jllcm.jar model1 model2 n [method]");
			System.out
					.println("Where method can be bf, both, or llcm (default: llcm).");
			System.out.println("Example: ");
			System.out
					.println("java -jar jllcm.jar examples/mini.mn examples/mini2.mn 3");
		}
		String filename1 = args[0];
		String filename2 = args[1];
		int N = Integer.parseInt(args[2]);

		String method = "llcm";
		String outputFile = null;
		boolean verbose = false;

		if (args.length >= 4) {
			method = args[3];
		}

		if (args.length >= 5) {
			outputFile = args[4];
		}

		if (args.length >= 6) {
			verbose = Boolean.parseBoolean(args[5]);
		}

		System.out.println("Model 1: " + filename1);
		System.out.println("Model 2: " + filename2);

		ConfusionMatrix cm;

		// Read models
		Collection<Feature> model1 = FileUtil.readFeatureSet(filename1);
		Collection<Feature> model2 = FileUtil.readFeatureSet(filename2);
//
//		try {
//			Thread.sleep(15000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		// Auxiliary variables for runtimes
		long startTime, endTime, totalTime;

		if (method.equalsIgnoreCase("llcm") || method.equalsIgnoreCase("both")
				|| method.equalsIgnoreCase("default")) {
			startTime = System.currentTimeMillis();
			cm = LLCM.computeConfusionMatrix(model1, model2, N);

			endTime = System.currentTimeMillis();
			totalTime = endTime - startTime;

			if(verbose){
			System.out.println("LLCM: " + cm.toString());
			System.out.println("Running time: " + totalTime);
			}
			
			FileUtil.writeCSV(outputFile, filename1, filename2, "default", cm, totalTime);
		}

		/*
		 * Perform Brute Force computing true negatives from empty canonical
		 * graphs efficiently.
		 */
		if (method.equalsIgnoreCase("both") || method.equalsIgnoreCase("bf")) {
			startTime = System.currentTimeMillis();
			cm = BruteForce.computeConfusionMatrix(model1, model2, N, true);

			endTime = System.currentTimeMillis();
			totalTime = endTime - startTime;

			if(verbose){
			System.out.println("BF:   " + cm.toString());

			// This is for validation purposes
			// Should be equal to ( 2^N * n choose 2 )			
			System.out.println("Number of comparisons: "
					+ cm.getNumberOfComparisons());
			
			System.out.println("Running time: " + totalTime);
			}
			
			FileUtil.writeCSV(outputFile, filename1, filename2, "bf", cm, totalTime);
		}

		/*
		 * Perform Brute Force without optimisation.
		 */
		if (method.equalsIgnoreCase("slowbf")) {
			startTime = System.currentTimeMillis();

			cm = BruteForce.computeConfusionMatrix(model1, model2, N, false);

			endTime = System.currentTimeMillis();
			totalTime = endTime - startTime;

			if(verbose){
			System.out.println("BF:   " + cm.toString());

			// This is for validation purposes
			// Should be equal to ( 2^N * n choose 2 )
			System.out.println("Number of comparisons: "
					+ cm.getNumberOfComparisons());

			System.out.println("Running time: " + totalTime);
			}
			
			FileUtil.writeCSV(outputFile, filename1, filename2, "slowbf", cm, totalTime);
		}
		}
	
}
