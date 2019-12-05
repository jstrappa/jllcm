package measure;

import java.util.BitSet;
import java.util.Collection;
import java.util.HashSet;

import feature.BinaryFeature;
import feature.Feature;
import util.FileUtil;
import util.Util;

public class BruteForce {

	/**
	 * 
	 * Log-linear model structure comparison by brute force.
	 * 
	 * Only works for binary variables.
	 * 
	 * 
	 * @param model1
	 *            , or true model
	 * @param model2
	 *            , or learned model
	 * @param number
	 *            of binary variables in domain
	 */
	public static ConfusionMatrix computeConfusionMatrix(
			Collection<Feature> model1, Collection<Feature> model2, int N,
			boolean optimised) {
		// Initialise output values
		int TP, TN, FP, FN;
		TP = TN = FP = FN = 0;

		Collection<Feature> contexts = new HashSet<Feature>();

		if (!optimised) {
			// Generate all contexts in domain
			contexts = Util.generateAllContexts(N);
		} else {
			// Generate all contexts represented in the models
			contexts.addAll(generateContextsFromFeatureSet(model1, N));
			contexts.addAll(generateContextsFromFeatureSet(model2, N));
			//System.out.println("Contexts: " + contexts.size());

			// Initialise true negatives with the number of edges in contexts
			// not represented in the models
			int m = (int) Math.pow(2, N);
			TN = (m - contexts.size()) * N * (N - 1) / 2;
		}

		// Auxiliary variables
		boolean edges1, edges2 = false;
		Collection<Feature> matchingFeatures1, matchingFeatures2 = null;

		// theoretical number of comparisons
		int numberOfComparisons = TN;
		// actual comparisons being made when computing empty canonical graphs
		// as true negatives:
		int numberOfComparisonsOptimised = 0;

		// For each context
		for (Feature context : contexts) {
			// Retrieve features that match the context x2
			matchingFeatures1 = getMatchingFeatures(model1, context);
			matchingFeatures2 = getMatchingFeatures(model2, context);

			// Generate all pairs in domain
			for (int x = 0; x < N - 1; x++) {
				for (int y = x + 1; y < N; y++) {
					// System.out.println("edge: "+ x+","+y +
					// " context "+context);

					// Check if each model contains the edge
					edges1 = hasEdge(x, y, matchingFeatures1);
					edges2 = hasEdge(x, y, matchingFeatures2);

					if (edges1 && edges2)
						TP++;
					else if (edges1) {
						FN++;
						// System.out.println("FN: " + x + " " + y + " context "
						// + context);
					} else if (edges2) {
						FP++;
						// System.out.println("FP: " + x + " " + y + " context "
						// + context);
					} else
						TN++;

					// Reset for next iteration
					edges1 = edges2 = false;
					numberOfComparisons++;
					numberOfComparisonsOptimised++;
				}
			}

		}

		ConfusionMatrix cm = new ConfusionMatrix(TP, TN, FP, FN);
		cm.setNumberOfComparisons(numberOfComparisons); // For validation
														// purposes
		if (optimised)
			System.out
					.println("number of comparisons with optimised Brute Force: "
							+ numberOfComparisonsOptimised);

		return cm;

	}

	/*
	 * Generates the canonical assignments (contexts) represented by the
	 * features in the model, that is, this method returns all the contexts
	 * whose associated canonical graph in the model given contains at least one
	 * edge.
	 */
	public static Collection<Feature> generateContextsFromFeatureSet(
			Collection<Feature> model, int N) {
		Collection<Feature> result = new HashSet<Feature>();
		Feature g;
		int k, c, b_k;
		BitSet b, aux;

		for (Feature f : model) {
			aux = (BitSet) f.getVar().clone();
			aux.flip(0, N); // indicator of unassigned variables in f
			// System.out.println("aux: "+aux.toString());
			g = new BinaryFeature(N);
			k = N - f.getLength(); // number of unassigned variables
			c = (int) Math.pow(2, k); // number of contexts represented by f

			// Generate all features represented by f
			for (int i = 0; i < c; i++) {
				g = f.clone();
				b = Util.convert(i, k);
				// System.out.println("b : "+b.toString());
				b_k = 0;

				// Set values of each context
				for (int j = aux.nextSetBit(0); j >= 0; j = aux
						.nextSetBit(j + 1)) {
					g.addVal(j, b.get(b_k) ? 1 : 0);
					b_k++;
				}

				// System.out.println("adding: "+g.toString());
				result.add(g);

			}
		}

		return result;
	}

	/**
	 * Compute the set matchingFeatures of all features in 'model' that match a
	 * particular context.
	 */
	public static Collection<Feature> getMatchingFeatures(
			Collection<Feature> model, Feature context) {
		Collection<Feature> matchingFeatures = new HashSet<Feature>();
		for (Feature feature : model) {
			if (feature.isSatisfied(context))
				matchingFeatures.add(feature);
		}
		return matchingFeatures;
	}

	/**
	 * Checks if a set of features contains the edge (x,y) by verifying if there
	 * is a feature that has both x and y in its scope.
	 */
	public static boolean hasEdge(int x, int y, Collection<Feature> model) {
		boolean result = false;
		for (Feature f : model) {
			if (f.hasVar(x) && f.hasVar(y)) {
				result = true;
				break;
			}
		}
		return result;
	}

}
