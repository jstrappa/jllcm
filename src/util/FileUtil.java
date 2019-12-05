package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

import feature.*;
import measure.ConfusionMatrix;

public class FileUtil {
	/**
	 * Reads a file containing a set of features according to the Libra Toolkit
	 * format. TODO link
	 * 
	 * 
	 * @param filename
	 * @return a Collection of features
	 */
	public static Collection<Feature> readFeatureSet(String filename) {
		Collection<Feature> features = new HashSet<Feature>();

		FileReader fileReader;
		try {
			fileReader = new FileReader(new File(filename));
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			String line = null;
			String[] values = null;
			Feature feature = null;
			// Infer number of variables from first line, which contains the
			// cardinalities of all variables
			int numberOfVariables = bufferedReader.readLine().trim().split(",").length;

			while ((line = bufferedReader.readLine()) != null) {
				if (line.contains("{") || line.contains("}"))
					continue; // ignore markers of beginning and end
				line = line.replaceAll("\\s", ""); // remove all spaces
				values = line.split("\\+v");
				feature = new BinaryFeature(numberOfVariables);
				for (int i = 1; i < values.length; i++) {
					int var = Character.getNumericValue(values[i].charAt(0));
					feature.addVar(var);
					feature.addVal(var, Character.getNumericValue(values[i].charAt(2)));
				}

				features.add(feature);

				values = null;
				feature = null;
			}
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("File was not found.\nExiting.");
			System.exit(1);
		} catch (IOException e) {
			System.out.println("I/O error while reading file:" + e.getMessage());
			System.out.println("Exiting.");
			System.exit(2);
		}

		return features;
	}

	public static void writeCSV(String outputFile, String filename1, String filename2, String method,
			ConfusionMatrix cm, long totalTime) {
		// Write output to a CSV file

		BufferedWriter bw = null;
		FileWriter fw = null;

		try {
			int hd = cm.getFalseNegatives() + cm.getFalsePositives();

			// Output content is
			// model1,model2,TP,FN,FP,TN,HD,method,runtime
			String results = filename1 + "," + filename2 + "," + cm.getTruePositives() + "," + cm.getFalseNegatives()
					+ "," + cm.getFalsePositives() + "," + cm.getTrueNegatives() + "," + hd + "," + method + ","
					+ totalTime + "\n";

			fw = new FileWriter(outputFile, true);
			bw = new BufferedWriter(fw);
			bw.append(results);

			System.out.println("Done");

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
		System.out.println("Output written successfully to " + outputFile);

	}

}
